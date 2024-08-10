package kaua.sistema_gerenciamento_escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotaDTO {
    private Integer id;
    private Double nota1;
    private Double nota2;
    private MateriaDTO materia;
    private AlunoDTO alunos;
}
