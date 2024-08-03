package kaua.sistema_gerenciamento_escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotasDTO {
    private int id;
    private double nota1;
    private double nota2;
    private Integer materia_id;
    private Integer aluno_id;
}
