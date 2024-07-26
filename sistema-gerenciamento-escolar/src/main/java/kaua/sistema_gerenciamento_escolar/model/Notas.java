package kaua.sistema_gerenciamento_escolar.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_notas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Notas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double nota1;

    private double nota2;

    @ManyToOne
    @JoinColumn(name="materia_id", nullable = false)
    private Materias materia;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @JsonBackReference
    private Aluno alunos;

}
