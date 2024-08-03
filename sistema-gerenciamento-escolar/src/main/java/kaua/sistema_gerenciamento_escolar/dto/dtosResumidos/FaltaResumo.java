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
    private int id;
    private LocalDate data;
    private int quantidade;
    private String situacao;
    private int totalFaltas;
    private MateriasResumo materia;
    private AlunoResumoParaFalta aluno;
}
