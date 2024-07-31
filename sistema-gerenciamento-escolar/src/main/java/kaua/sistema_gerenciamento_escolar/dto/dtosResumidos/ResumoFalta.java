package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumoFalta {

    private LocalDate data;
    private int faltas;
    private String situacao;
    private int totalFaltas;
    private ResumoMaterias materia;
    private ResumoAluno aluno;
}

/*
 * @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materias materia;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private String situacao;

    @Column(nullable = false)
    private int totalFaltas;
 */