package kaua.sistema_gerenciamento_escolar.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MateriaDTO {
    private int id;
    private String nome;
    private String descricao;
    private double cargaHoraria;
    private Integer professor_id;
}


