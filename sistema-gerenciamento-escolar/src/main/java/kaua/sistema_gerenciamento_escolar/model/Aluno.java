package kaua.sistema_gerenciamento_escolar.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_alunos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private long matricula;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Materias> materiasMatriculadas = new HashSet<>();

    @OneToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Notas> historicoNotas = new HashSet<>();

    @OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Faltas> faltas;
}

