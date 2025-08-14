package com.romands.Controller;

import com.romands.Controller.Request.LoginDTO;
import com.romands.Controller.Request.RegisterDTO;
import com.romands.Controller.Response.LoginResponse;
import com.romands.Controller.Response.RegisterResponse;
import com.romands.Entity.Transferencia;
import com.romands.Entity.User;
import com.romands.Entity.UserRoles;
import com.romands.Execeptions.EmailRegisteredException;
import com.romands.Execeptions.UsernameNotFoundException;
import com.romands.Repository.UserRepository;
import com.romands.Service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @Operation(summary = "Registra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Email já registrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no cadastro")
    })
    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody RegisterDTO registerDTO) {
        String password = new BCryptPasswordEncoder().encode(registerDTO.password());
        List<Transferencia> transferencias = new ArrayList<>();
        User user = new User(registerDTO.email(), password, UserRoles.USER, transferencias);
        if (userRepository.findByEmail(user.getUsername()) != null) {
            throw new EmailRegisteredException();
        }
        userRepository.save(user);
        RegisterResponse response = new RegisterResponse(
                user.getUsername() + " foi registrado!",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Faz login com email e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no login")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        if(userRepository.findByEmail(loginDTO.email()) == null ){
            throw new UsernameNotFoundException();
        }
        var user = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(user);
        String token = tokenService.GenerateToken((User) userRepository.findByEmail(loginDTO.email()));
        LoginResponse response = new LoginResponse(
                "Logado com sucesso.",
                token,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );
        return ResponseEntity.ok(response);
    }
}
