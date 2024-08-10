package kaua.sistema_gerenciamento_escolar.dto.dtosResumidos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfessorResumo {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private MateriasResumo materiaResumo;
}
