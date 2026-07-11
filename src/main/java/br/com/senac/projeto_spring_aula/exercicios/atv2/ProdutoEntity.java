package br.com.senac.projeto_spring_aula.exercicios.atv2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produtoatv")
@Setter
@Getter
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantiddeEstoque;

    @Enumerated(EnumType.STRING)
    private ProdutoStatus status;

    @PrePersist
    public void prePersist(){
        if (status == null){
            status = ProdutoStatus.DISPONIVEL;
        }
    }
}
