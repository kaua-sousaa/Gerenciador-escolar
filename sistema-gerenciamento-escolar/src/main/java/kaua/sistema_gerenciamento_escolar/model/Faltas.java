package kaua.sistema_gerenciamento_escolar.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
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

@Table(name = "tb_faltas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Faltas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materias materia;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private String situacao;

    @Column(nullable = false)
    private int totalFaltas;
}
