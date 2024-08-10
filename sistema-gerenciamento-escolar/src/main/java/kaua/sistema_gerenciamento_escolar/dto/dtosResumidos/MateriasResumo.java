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
    private Integer id;
    private String nome;
    private String descricao;
    private double cargaHoraria;
    private ProfessorResumo professor;
}
