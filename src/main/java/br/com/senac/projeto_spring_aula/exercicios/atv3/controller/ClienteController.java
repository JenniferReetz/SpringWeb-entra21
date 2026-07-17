package br.com.senac.projeto_spring_aula.exercicios.atv3.controller;

import br.com.senac.projeto_spring_aula.exercicios.atv3.model.ClienteEntity;
import br.com.senac.projeto_spring_aula.exercicios.atv3.model.ClientePostDTO;
import br.com.senac.projeto_spring_aula.exercicios.atv3.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<ClienteEntity> criar(@Valid @RequestBody ClientePostDTO dto) {
        ClienteEntity cliente = new ClienteEntity();
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("Já existe um cliente com esse email!");
        }

        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setCpf(dto.cpf());

        ClienteEntity save = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listar() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

}
