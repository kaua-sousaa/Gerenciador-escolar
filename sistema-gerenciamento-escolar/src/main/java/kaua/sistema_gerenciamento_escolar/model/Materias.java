package kaua.sistema_gerenciamento_escolar.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_materias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Materias {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private double cargaHoraria;

    @ManyToMany
    @JoinTable(
        name = "tb_aluno_materia",
        joinColumns = @JoinColumn(name = "materia_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    @JsonBackReference
    private Set<Aluno> alunos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;


}
