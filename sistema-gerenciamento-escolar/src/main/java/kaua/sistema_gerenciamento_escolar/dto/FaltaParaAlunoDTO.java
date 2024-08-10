package kaua.sistema_gerenciamento_escolar.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FaltaParaAlunoDTO {
    
    private LocalDate data;
    private String situacao;
    private int totalFaltas;
    private MateriaDTO materia;
}
