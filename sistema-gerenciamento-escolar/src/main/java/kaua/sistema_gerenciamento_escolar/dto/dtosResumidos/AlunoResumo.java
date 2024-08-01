package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import java.util.Set;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlunoResumo {
    
    private String nome;
    private String email;
    private long matricula;
    private Set<ResumoMaterias> materias;
    private Set<NotaResumo> historico;
    private List<FaltaResumoParaAluno> faltas;
}
