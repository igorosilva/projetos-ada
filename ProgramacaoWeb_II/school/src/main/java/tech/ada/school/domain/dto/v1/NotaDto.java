package tech.ada.school.domain.dto.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class NotaDto {
    @Positive
    @NotNull
    @NotEmpty
    @NotBlank
    private int id;

    @NotNull
    @NotEmpty
    @NotBlank
    @PositiveOrZero
    @Min(0)
    @Max(10)
    private double nota;
}
