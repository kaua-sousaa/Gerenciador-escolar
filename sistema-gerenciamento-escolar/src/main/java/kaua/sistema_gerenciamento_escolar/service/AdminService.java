package kaua.sistema_gerenciamento_escolar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.MateriasRepository;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;

@Service
public class AdminService {
    
    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Aluno> getAlunos(){
        return alunoRepository.findAll();
    }

    @Transactional
    public Aluno criarAluno(AlunoDTO alunoDTO){
        Aluno aluno = new Aluno();

        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        aluno.setMatricula(alunoDTO.matricula());
        aluno.setSenha(alunoDTO.senha());    

        Set<Materias> materias = new HashSet<>(materiasRepository.findAllById(alunoDTO.matricula_id()));
        System.out.println("Materias encontradas: "+ materias);
        aluno.setMateriasMatriculadas(materias);

        for (Materias materia: materias){
            materia.getAlunos().add(aluno);
        }   
        
        System.out.println("Aluno salvo:" +aluno);
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Materias criarMateria(MateriaDTO materiaDTO){
        Materias materia = new Materias();

        materia.setNome(materiaDTO.nome());
        materia.setDescricao(materiaDTO.descricao());
        materia.setCargaHoraria(materiaDTO.cargaHoraria());

        Professor professor = professorRepository.findById(materiaDTO.professor_id())
        .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        materia.setProfessor(professor);
        
        return materiasRepository.save(materia);
    }

    @Transactional
    public Professor criarProfessor(ProfessorDTO professorDTO){
        Professor professor = new Professor();

        professor.setNome(professorDTO.nome());
        professor.setEmail(professorDTO.email());
        professor.setSenha(professorDTO.senha());
        professor.setTelefone(professorDTO.telefone());

        return professorRepository.save(professor);
    }

    
}
