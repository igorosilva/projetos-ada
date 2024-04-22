package tech.ada.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tech.ada.school.domain.dto.exception.NotFoundException;
import tech.ada.school.domain.dto.v1.AlunoDto;
import tech.ada.school.domain.dto.v1.NotaDto;
import tech.ada.school.domain.entities.Aluno;
import tech.ada.school.domain.entities.Nota;
import tech.ada.school.domain.mappers.AlunoMapper;
import tech.ada.school.domain.mappers.NotaMapper;
import tech.ada.school.external.FeignBoredApi;
import tech.ada.school.repositories.AlunoRepository;
import tech.ada.school.repositories.NotaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Primary
public class AlunoServicoBD implements IAlunoService {
    private final AlunoRepository repositorio;
    private final NotaRepository notaRepositorio;
    private final FeignBoredApi boredApi;

    @Override
    public AlunoDto criarAluno(AlunoDto pedido) {
        Aluno a = AlunoMapper.toEntity(pedido);
        double media = calcularMedia(
                        a.getNotas()
                        .stream()
                        .map(Nota::getNota)
                        .collect(Collectors.toList())
        );
        a.atualizarMediaNotas(media);
        return AlunoMapper.toDto(repositorio.save(a), boredApi.getActivity().activity());
    }

    private double calcularMedia (List<Double> notas) {
        if (notas.isEmpty()) {
            return 0.0;
        }
        return notas
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .getAsDouble();
    }

    @Override
    public List<AlunoDto> listarAlunos() {
        return repositorio
                .findAll()
                .stream()
                .map(ent -> AlunoMapper.toDto(ent, boredApi.getActivity().activity()))
                .toList();
    }

    @Override
    public List<AlunoDto> buscarTurma(String turma) {
        Stream<AlunoDto> listaAlunos = repositorio
                .findAll()
                .stream()
                .map(ent -> AlunoMapper.toDto(ent, boredApi.getActivity().activity()))
                .filter(it -> it.getTurma().equals(turma));
        if(!listarAlunos().isEmpty()) {
            return listaAlunos.collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public Aluno buscarAluno(int id) throws NotFoundException {
        return repositorio
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Aluno.class, String.valueOf(id)));
    }

    public Nota buscarNota(int id) throws NotFoundException {
        return notaRepositorio
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Nota.class, String.valueOf(id)));
    }

    @Override
    public AlunoDto buscarAlunoPorEmail(String email) throws NotFoundException {
        return repositorio
                .findByEmail(email)
                .map(ent -> AlunoMapper.toDto(ent, boredApi.getActivity().activity()))
                .orElseThrow(() -> new NotFoundException(Aluno.class, email));
    }

    @Override
    public AlunoDto buscarAlunoPorId(int id) throws NotFoundException {
        return repositorio
                .findById(id)
                .map(ent -> AlunoMapper.toDto(ent, boredApi.getActivity().activity()))
                .orElseThrow(() -> new NotFoundException(Aluno.class, String.valueOf(id)));
    }

    @Override
    public AlunoDto atualizarNomeAluno(int id, String nome) throws NotFoundException {
        final Aluno a = buscarAluno(id);
        a.setNome(nome);
        repositorio.save(a);
        return AlunoMapper.toDto(a, boredApi.getActivity().activity());
    }

    @Override
    public AlunoDto atualizarTurmaAluno(int id, String turma) throws NotFoundException {
        final Aluno a = buscarAluno(id);
        a.setTurma(turma);
        repositorio.save(a);
        return AlunoMapper.toDto(a, boredApi.getActivity().activity());
    }

    @Override
    public AlunoDto atualizarEmailAluno(int id, String email) throws NotFoundException {
        final Aluno a = buscarAluno(id);
        a.setEmail(email);
        repositorio.save(a);
        return AlunoMapper.toDto(a, boredApi.getActivity().activity());
    }

    @Override
    public NotaDto atualizarNotasAluno(int id, Double nota) throws NotFoundException {
        final Nota n = buscarNota(id);
        Double notaAntiga = n.getNota();
        n.setNota(nota);
        notaRepositorio.save(n);

        final Aluno a = buscarAluno(n.getAluno().getId());
        List<Nota> notas = a.getNotas();
        notas.forEach(notaAtual -> {
            if (notaAtual.getId() == id) {
                notaAtual.setNota(nota);
            }
        });
        double media = calcularMedia(
                notas.stream()
                        .map(Nota::getNota)
                        .collect(Collectors.toList()));
        a.atualizarMediaNotas(media);
        repositorio.save(a);

        return NotaMapper.toDto(n);
    }

    @Override
    public AlunoDto atualizarSemestreAluno(int id, int semestre) throws NotFoundException {
        final Aluno a = buscarAluno(id);
        a.setSemestreAtual(semestre);
        repositorio.save(a);
        return AlunoMapper.toDto(a, boredApi.getActivity().activity());
    }

    @Override
    public void deletarAluno(int id) throws NotFoundException {
        final Aluno a = buscarAluno(id);
        repositorio.delete(a);
    }
}
