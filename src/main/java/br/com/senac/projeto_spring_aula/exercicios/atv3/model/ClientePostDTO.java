package br.com.senac.projeto_spring_aula.exercicios.atv3.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientePostDTO(
        @NotBlank(message = "O nome deve ser preenchido!")
        String nome,
        @NotBlank(message = "O email deve ser preenchido!")
        @Email(message = "Email inválido!")
        String email,
        @NotBlank(message = "O cpf deve ser preenchido!")
        @Size(min = 11, max = 11, message = "O cpf deve ter 11 caracteres!")
        String cpf
) {
}
