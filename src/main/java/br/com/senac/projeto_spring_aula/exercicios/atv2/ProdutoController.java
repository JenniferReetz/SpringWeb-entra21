package br.com.senac.projeto_spring_aula.exercicios.atv2;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoEntity> criarProduto (@Valid @RequestBody ProdutoPostDTO dto){
        ProdutoEntity produto = new ProdutoEntity();

        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidade());

        ProdutoEntity save = repository.save(produto);
        return ResponseEntity.status(201).body(save);
    }
    // Criem GET /produtos (200) e GET /produtos/{id} (200 se achar, 404 se não achar — usem
    //Optional, igual ao getById do ToDoList).
    //Criem DELETE /produtos/{id} retornando 204 NO_CONTENT
    @GetMapping
    public ResponseEntity<List<ProdutoEntity>> listarProdutos(){
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEntity> buscarProduto(@PathVariable Integer id){
        Optional<ProdutoEntity> optionalProdutoEntity = repository.findById(id);

        if (optionalProdutoEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.status(200).body(optionalProdutoEntity.get());
        }
    }
    // Param é diferente de body que é diferente de pathVariable
    // Param é ?quantidade=X
    // Criem PATCH /produtos/{id}/reabastecer?quantidade=X que soma X à quantidadeEstoque atual.
    //    //Se o produto estava ESGOTADO e a nova quantidade for maior que zero, mudem o status para DISPONIVEL
    //    //automaticamente.
    //    //Devolvam 404 se o produto não existir.
    @PatchMapping("/{id}/reabastecer")
    @Transactional
    public ResponseEntity<ProdutoEntity> reabastecerProduto (@PathVariable int id, @RequestParam Integer quantidade){
        // buscar
        Optional<ProdutoEntity> optionalProduto = repository.findById(id);

        // checar
        if (optionalProduto.isEmpty()){
            return ResponseEntity.status(404).body(null);}
        ProdutoEntity produto = optionalProduto.get();
        if ((produto.getQuantidadeEstoque() + quantidade) < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // alterar
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        if (produto.getStatus().equals(ProdutoStatus.ESGOTADO)){
            produto.setStatus(ProdutoStatus.DISPONIVEL);
        } else {
            produto.setStatus(ProdutoStatus.ESGOTADO);
        }

        // devolver
        ProdutoEntity save = repository.save(produto);
        return ResponseEntity.ok(save);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarProduto(@PathVariable int id){
        if (!repository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
