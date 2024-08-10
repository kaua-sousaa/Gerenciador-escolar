package kaua.sistema_gerenciamento_escolar.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AlunoDTO {
    private int id;
    private String nome;
    private String email;
    private long matricula;
    private String senha;
    private String telefone;
    private LocalDate dataNascimento;
    private Set<Integer> materia_id;
    private Set<Integer> historico_notas_id;
    private Set<Integer> faltas_id;
}
