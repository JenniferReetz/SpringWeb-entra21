package br.com.senac.projeto_spring_aula.todolist;
import br.com.senac.projeto_spring_aula.todolist.model.ListaEntity;
import br.com.senac.projeto_spring_aula.todolist.model.ListaPostDTO;
import br.com.senac.projeto_spring_aula.todolist.model.ListaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lista")
@RequiredArgsConstructor
public class ToDoListController {
    private final ListaRepository listaRepository;

    @PostMapping
    public ResponseEntity<ListaEntity> createTask(@RequestBody ListaPostDTO dto){
       ListaEntity listaEntity = new ListaEntity();
       if (dto.descricao() == null || dto.descricao().trim().equals("")){
           throw new IllegalArgumentException("A descrição é obrigatória!");
       }

       listaEntity.setDescricao(dto.descricao());
       listaEntity.setStatus(ListaStatus.TO_DO);

        ListaEntity saved =  listaRepository.save(listaEntity);
        return ResponseEntity
                .status(201)
                .body(saved);
    }
}
