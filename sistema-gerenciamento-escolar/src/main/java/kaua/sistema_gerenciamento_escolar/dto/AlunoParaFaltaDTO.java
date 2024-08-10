package kaua.sistema_gerenciamento_escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlunoParaFaltaDTO {
    
    private String nome;
    private long matricula;
}
