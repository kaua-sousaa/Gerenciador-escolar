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
public class FaltaResumo {

    private LocalDate data;
    private int faltas;
    private String situacao;
    private int totalFaltas;
    private ResumoMaterias materia;
    private AlunoResumoParaFalta aluno;
}
