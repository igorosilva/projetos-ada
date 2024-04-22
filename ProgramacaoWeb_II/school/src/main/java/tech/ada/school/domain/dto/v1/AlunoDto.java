package tech.ada.school.domain.dto.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class AlunoDto {
    @Positive
    @NotNull
    @NotEmpty
    @NotBlank
    private int id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    private String turma;

    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    private List<NotaDto> notas = new ArrayList<>();

    @NotNull
    @NotEmpty
    @NotBlank
    @PositiveOrZero
    @Min(0)
    @Max(10)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double mediaNotas = calcularMediaNotas();

    @NotNull
    @NotEmpty
    @NotBlank
    @Positive
    @Min(1)
    private int semestreAtual;

    @Null
    private String activity;

    public double calcularMediaNotas() {
        if (notas == null || notas.isEmpty()) {
            return 0;
        }

        double somaNotas = notas.stream().mapToDouble(NotaDto::getNota).sum();
        return somaNotas / notas.size();
    }
}
