package kaua.sistema_gerenciamento_escolar.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FaltaDTO {
    private Integer id;
    private LocalDate data;
    private int quantidade;
    private String situacao;
    private int totalFaltas;
    private MateriaDTO materia;
    private AlunoParaFaltaDTO aluno;
}
