package tech.ada.school.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_completo")
    private String nome;

    private String turma;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Nota> notas = new ArrayList<>();

    @Column(name = "media")
    private double mediaNotas;

    @Column(name = "semestre_atual")
    private int semestreAtual;

    public void atualizarMediaNotas(double mediaNotas) {
        this.mediaNotas = mediaNotas;
    }
}
