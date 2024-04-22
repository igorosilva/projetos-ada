package tech.ada.school.view;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.school.domain.dto.exception.NotFoundException;
import tech.ada.school.domain.dto.v1.AlunoDto;
import tech.ada.school.domain.dto.v1.NotaDto;
import tech.ada.school.service.IAlunoService;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    private final IAlunoService servico;

    @Autowired
    public AlunoController(IAlunoService servico) {
        this.servico = servico;
    }

    @PostMapping
    public ResponseEntity<AlunoDto> criarAluno(
            @RequestBody @Valid AlunoDto pedido
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servico.criarAluno(pedido));
    }

    @GetMapping
    public ResponseEntity<List<AlunoDto>> listarAlunos(
            @RequestParam(required = false) @Valid String turma
    ) throws NotFoundException {
        List<AlunoDto> alunos;
        if(turma == null) {
            alunos = servico.listarAlunos();
        } else {
            alunos = servico.buscarTurma(turma);
        }
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscarAlunoPorId(
            @PathVariable("id") @Valid int id
    ) throws NotFoundException {
        final AlunoDto a = servico.buscarAlunoPorId(id);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(servico.buscarAlunoPorId(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AlunoDto> buscarAlunoPorEmail(
            @PathVariable("email") @Valid String email
    ) throws NotFoundException {
        final AlunoDto a = servico.buscarAlunoPorEmail(email);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(servico.buscarAlunoPorEmail(email));
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<AlunoDto> atualizarNomeAluno(
            @PathVariable("id") @Valid int id,
            @RequestBody(required = true) String nome
    ) throws NotFoundException {
        final AlunoDto a = servico.atualizarNomeAluno(id, nome);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(a);
    }

    @PutMapping("/{id}/turma")
    public ResponseEntity<AlunoDto> atualizarTurmaAluno(
            @PathVariable("id") @Valid int id,
            @RequestBody(required = true) @Valid String turma
    ) throws NotFoundException {
        final AlunoDto a = servico.atualizarTurmaAluno(id, turma);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(a);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<AlunoDto> atualizarEmailAluno(
            @PathVariable("id") @Valid int id,
            @RequestBody(required = true) @Valid String email
    ) throws NotFoundException {
        final AlunoDto a = servico.atualizarEmailAluno(id, email);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(a);
    }

    @PutMapping("/{id}/notas")
    public ResponseEntity<NotaDto> atualizarNotasAluno(
            @PathVariable("id") @Valid int id,
            @RequestBody(required = true) @Valid Double nota
    ) throws NotFoundException {
            final NotaDto n = servico.atualizarNotasAluno(id, nota);

            if(n == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(n);
    }

    @PutMapping("/{id}/semestre")
    public ResponseEntity<AlunoDto> atualizarSemestreAluno(
            @PathVariable("id") @Valid int id,
            @RequestBody(required = true) @Valid int semestre
    ) throws NotFoundException {
        final AlunoDto a = servico.atualizarSemestreAluno(id, semestre);

        if(a == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(
            @PathVariable("id") @Valid int id
    ) throws NotFoundException {
         servico.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}