package kaua.sistema_gerenciamento_escolar.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FaltasDTO {
    private int id;
    private Integer aluno_id;
    private Integer materia_id;
    private Integer quantidade;
}

