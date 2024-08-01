package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotaResumo {
    
    private double nota1;
    private double nota2;
    private ResumoMaterias materia;
}
