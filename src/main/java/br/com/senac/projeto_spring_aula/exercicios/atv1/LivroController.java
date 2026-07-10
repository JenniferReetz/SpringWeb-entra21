package br.com.senac.projeto_spring_aula.exercicios.atv1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController {
    private final LivroRepository livroRepository;

    @PostMapping                    // sempre receber um DTO nos parâmetros ↓
    public ResponseEntity<LivroEntity> criarLivro(@Valid @RequestBody LivroPostDTO livroDTO){
        LivroEntity livro = new LivroEntity();

        livro.setAutor(livroDTO.autor());
        livro.setTitulo(livroDTO.titulo());
        livro.setAnoPublicacao(livroDTO.anoPublicacao());

        LivroEntity save = livroRepository.save(livro);
        return ResponseEntity
                .status(201)
                .body(save);
    }

    @GetMapping
    public ResponseEntity<List<LivroEntity>> listarLivros(){
        return ResponseEntity
                .status(200)
                .body(livroRepository.findAll());
    }
}
