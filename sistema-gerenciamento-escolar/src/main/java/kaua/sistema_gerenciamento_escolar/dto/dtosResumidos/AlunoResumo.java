package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import java.util.Set;
import java.time.LocalDate;
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
    
    private int id;
    private String nome;
    private String email;
    private long matricula;
    private String telefone;
    private LocalDate dataNascimento;
    private Set<MateriasResumo> materias;
    private Set<NotaResumo> historico;
    private List<FaltaResumoParaAluno> faltas;
}
