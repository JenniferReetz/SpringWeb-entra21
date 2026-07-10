package br.com.senac.projeto_spring_aula.todolist;
import br.com.senac.projeto_spring_aula.todolist.model.ListaEntity;
import br.com.senac.projeto_spring_aula.todolist.model.ListaPostDTO;
import br.com.senac.projeto_spring_aula.todolist.model.ListaStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lista")
@RequiredArgsConstructor
public class ToDoListController {
    private final ListaRepository listaRepository;

    @PostMapping
    @Transactional
    // O RequestBody serve pra pegar o corpo JSON            ↓
    public ResponseEntity<ListaEntity> createTask(@Valid @RequestBody ListaPostDTO dto){
       ListaEntity listaEntity = new ListaEntity();
       listaEntity.setDescricao(dto.descricao());
       listaEntity.setStatus(ListaStatus.TO_DO);

        ListaEntity saved =  listaRepository.save(listaEntity);
        return ResponseEntity
                .status(201)
                .body(saved);
    }
    @GetMapping
    @Transactional
    public ResponseEntity<List<ListaEntity>> getAll(){
        List<ListaEntity> todas = listaRepository.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todas);
    }
    @GetMapping("/{id}")
    @Transactional
    // PathVariable é pra pegar a variável do caminho ↓
    public ResponseEntity<ListaEntity> getById(@PathVariable int id){
        Optional<ListaEntity> optionalListaEntity = listaRepository.findById(id);

        if (optionalListaEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalListaEntity.get());
        }
    }
    // PATCH ATUALIZA UM DADO, PUT TODOS OS DADOS
    // o patch atualiza de forma predefenida, tipo o put seria tipo um post,
    // este só faz um post interno, já predefenido
    // então nao tem corpo na requisição NESTE CASO
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ListaEntity> endTask(@PathVariable int id){
        Optional<ListaEntity> optionalLista = listaRepository.findById(id);

        if (optionalLista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ListaEntity listaEntity = optionalLista.get();
        if (!listaEntity.getStatus().equals(ListaStatus.TO_DO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        listaEntity.setStatus(ListaStatus.DONE);
        listaEntity.setDataConclusao(LocalDateTime.now());

        ListaEntity saved = listaRepository.save(listaEntity);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @PatchMapping("/{id}/cancelar")
    @Transactional
    public ResponseEntity<ListaEntity> cancelTask(@PathVariable int id){
        // metodo principal de buscar por id
        Optional<ListaEntity> optionalLista = listaRepository.findById(id);
        // validação
        if (optionalLista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ListaEntity listaEntity = optionalLista.get();
        // lógica principal do metodo
        if (!listaEntity.getStatus().equals(ListaStatus.TO_DO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}
        listaEntity.setStatus(ListaStatus.CANCELLED);
        listaEntity.setDataConclusao(LocalDateTime.now());

        // salvar e 200 OK
        ListaEntity saved = listaRepository.save(listaEntity);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        listaRepository.deleteById(id);
        // o build() serve tipo como um body, só que sem body
        // (Tipo quando o tipo do ResponseEntity<Void> é Void) ↓
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
