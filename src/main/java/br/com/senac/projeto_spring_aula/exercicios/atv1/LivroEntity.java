package br.com.senac.projeto_spring_aula.exercicios.atv1;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String autor;
    @Column(name = "ano_publicacao")
    private int anoPublicacao;
    // Boolean - this class provides many methods
    // for converting a boolean to a String
    // and a String to a boolean
    private Boolean disponivel;
    // ↓ antes de persistir no banco
    @PrePersist
    public void prePersist(){
        if (disponivel == null){
            disponivel = true;
        }
    }
}
