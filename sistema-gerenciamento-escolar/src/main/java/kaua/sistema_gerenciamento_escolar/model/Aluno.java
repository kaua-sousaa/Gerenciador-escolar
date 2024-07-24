package kaua.sistema_gerenciamento_escolar.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long matricula;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Materias> materiasMatriculadas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Notas> historicoNotas;
}
