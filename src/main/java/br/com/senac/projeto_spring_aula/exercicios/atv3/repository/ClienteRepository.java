package br.com.senac.projeto_spring_aula.exercicios.atv3.repository;

import br.com.senac.projeto_spring_aula.exercicios.atv3.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    // JPQL usa o nome das classes/entidades e nao a tabela
    // ? e numero do parametro, ?1 primeiro parametro ou :email
    @Query("SELECT c FROM ClienteEntity c WHERE c.email = :email")
    Optional<ClienteEntity> findByEmail (String email);

    // findByNomeCointaining StartingWith, EndingWith Query Methods
    boolean existsByEmail(String email);

}
