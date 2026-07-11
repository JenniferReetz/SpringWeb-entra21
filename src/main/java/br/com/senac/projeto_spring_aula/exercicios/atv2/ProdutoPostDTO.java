package br.com.senac.projeto_spring_aula.exercicios.atv2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoPostDTO(
        @NotBlank(message = "O nome do produto não pode ser vazio!")
        String nome,
        @NotNull(message = "O preço do produto não pode ser vazio!")
        @Positive(message = "O preço do produto não pode ser negativo!")
        BigDecimal preco,
        @NotNull(message = "A quantidade do produto não pode ser vazia!")
        @PositiveOrZero(message = "A quantidade do produto não pode ser negativa ou zero!")
        Integer quantidade
) {
}
