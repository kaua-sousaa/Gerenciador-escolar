package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumoMaterias {
    private String nome;
    private ResumoProfessor professor;
}
