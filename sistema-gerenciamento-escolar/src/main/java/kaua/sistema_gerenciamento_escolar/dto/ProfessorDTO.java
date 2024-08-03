package kaua.sistema_gerenciamento_escolar.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProfessorDTO {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private LocalDate dataNascimento;
}
