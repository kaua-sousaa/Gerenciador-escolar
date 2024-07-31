package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumoAluno {
    
    private String nome;
    private long matricula;
}
