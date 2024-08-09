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
    private int id;
    private Double nota1;
    private Double nota2;
    private MateriasResumo materia;
}
