package tech.ada.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.school.domain.entities.Nota;

import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    Optional<Nota> findById(int id);
}
