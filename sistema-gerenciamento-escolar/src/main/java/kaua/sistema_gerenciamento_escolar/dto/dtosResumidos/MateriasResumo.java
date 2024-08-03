package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MateriasResumo {
    private int id;
    private String nome;
    private ProfessorResumo professor;
}
