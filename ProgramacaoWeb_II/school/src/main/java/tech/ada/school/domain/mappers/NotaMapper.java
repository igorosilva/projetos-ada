package tech.ada.school.domain.mappers;

import tech.ada.school.domain.dto.v1.NotaDto;
import tech.ada.school.domain.entities.Nota;

public class NotaMapper {
    public static Nota toEntity(NotaDto dto) {
        return Nota
                .builder()
                .nota(dto.getNota())
                .build();
    }

    public static NotaDto toDto(Nota entity) {
        return new NotaDto(
                entity.getId(),
                entity.getNota()
        );
    }
}
