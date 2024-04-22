package tech.ada.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.school.domain.entities.Aluno;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    Optional<Aluno> findById(int id);

    Optional<Aluno> findByEmail(String email);

    List<Aluno> findAll();
}
