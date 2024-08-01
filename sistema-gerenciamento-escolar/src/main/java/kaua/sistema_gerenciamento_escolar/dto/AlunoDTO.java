package kaua.sistema_gerenciamento_escolar.dto;

import java.util.Set;

public record AlunoDTO(String nome, String email, long matricula, String senha, Set<Integer> matricula_id, Set<Integer> historico_notas_id, Set<Integer> faltas_id) {
    
}
