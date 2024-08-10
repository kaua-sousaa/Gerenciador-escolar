package kaua.sistema_gerenciamento_escolar.dto;

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
public class AlunoDTO {
    
    private Integer id;
    private String nome;
    private String email;
    private long matricula;
    private String telefone;
    private LocalDate dataNascimento;
    private Set<MateriaDTO> materias;
    private Set<NotaDTO> historico;
    private List<FaltaParaAlunoDTO> faltas;

}

