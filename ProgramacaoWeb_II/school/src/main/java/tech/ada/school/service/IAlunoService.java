package tech.ada.school.service;

import tech.ada.school.domain.dto.exception.NotFoundException;
import tech.ada.school.domain.dto.v1.AlunoDto;
import tech.ada.school.domain.dto.v1.NotaDto;

import java.util.List;

public interface IAlunoService {
    AlunoDto criarAluno(AlunoDto pedido);

    List<AlunoDto> listarAlunos();

    List<AlunoDto> buscarTurma(String turma);

    AlunoDto buscarAlunoPorId(int id) throws NotFoundException;

    AlunoDto buscarAlunoPorEmail(String email) throws NotFoundException;

    AlunoDto atualizarNomeAluno(int id, String nome) throws NotFoundException;

    AlunoDto atualizarTurmaAluno(int id, String turma) throws NotFoundException;

    AlunoDto atualizarEmailAluno(int id, String email) throws NotFoundException;

    NotaDto atualizarNotasAluno(int id, Double notas) throws NotFoundException;

    AlunoDto atualizarSemestreAluno(int id, int semestre) throws NotFoundException;

    void deletarAluno(int id) throws NotFoundException;
}
