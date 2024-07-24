package kaua.sistema_gerenciamento_escolar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name="materiaId" ,nullable = false)
    private Materias materia;

    @ManyToOne
    @JoinColumn(name = "alunoId", nullable = false)
    private Aluno aluno;
}
