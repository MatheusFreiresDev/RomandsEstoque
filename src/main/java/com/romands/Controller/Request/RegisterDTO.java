package com.romands.Controller.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO (@NotBlank(message = "O e-mail é obrigatório")
                           @Email(message = "E-mail inválido")String email, @NotBlank(message = "A Senha é obrigatória")@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
String password){
}
