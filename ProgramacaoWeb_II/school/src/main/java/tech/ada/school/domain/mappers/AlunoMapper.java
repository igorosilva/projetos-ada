package tech.ada.school.domain.mappers;

import tech.ada.school.domain.dto.v1.AlunoDto;
import tech.ada.school.domain.dto.v1.NotaDto;
import tech.ada.school.domain.entities.Aluno;
import tech.ada.school.domain.entities.Nota;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoMapper {
    public static Aluno toEntity(AlunoDto dto) {
        Aluno aluno = Aluno.builder()
                .nome(dto.getNome())
                .turma(dto.getTurma())
                .email(dto.getEmail())
                .mediaNotas(dto.calcularMediaNotas())
                .semestreAtual(dto.getSemestreAtual())
                .build();

        List<Nota> notas = dto.getNotas().stream()
                .map(notaDto -> {
                    Nota nota = new Nota();
                    nota.setAluno(aluno);
                    nota.setNota(notaDto.getNota());
                    return nota;
                })
                .collect(Collectors.toList());

        aluno.setNotas(notas);

        return aluno;
    }

    public static AlunoDto toDto(Aluno entity, String activity) {
        List<NotaDto> notas = entity.getNotas().stream()
                .map(nota -> new NotaDto(nota.getId(), nota.getNota()))
                .collect(Collectors.toList());

        AlunoDto dto = new AlunoDto(
                entity.getId(),
                entity.getNome(),
                entity.getTurma(),
                entity.getEmail(),
                notas,
                entity.getMediaNotas(),
                entity.getSemestreAtual(),
                activity
        );

        dto.setMediaNotas(dto.calcularMediaNotas());

        return dto;
    }
}
