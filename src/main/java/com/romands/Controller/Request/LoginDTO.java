package com.romands.Controller.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank(message = "O e-mail é obrigatório")
                         @Email(message = "E-mail inválido")String email, @NotBlank(message = "A Senha é obrigatória") String password) {
}
