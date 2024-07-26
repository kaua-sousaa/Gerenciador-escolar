package kaua.sistema_gerenciamento_escolar.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import kaua.sistema_gerenciamento_escolar.dto.NotasDTO;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.model.Notas;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.MateriasRepository;
import kaua.sistema_gerenciamento_escolar.repository.NotasRepository;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;

@Service
public class ProfessorService {
    
    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MateriasRepository materiasRepository;

    public Notas aplicarNotas(NotasDTO notasDTO){
        Notas notas = new Notas();
     
        notas.setNota1(notasDTO.nota1());
        notas.setNota2(notasDTO.nota2());

        Aluno aluno = alunoRepository.findById(notasDTO.aluno_id())
        .orElseThrow(()-> new EntityNotFoundException("Aluno não encontrado"));

        notas.setAlunos(aluno);

        Materias materias = materiasRepository.findById(notasDTO.materia_id())
        .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));

        for (Notas valor: aluno.getHistoricoNotas()){
            //Se o valor(nome da materia), dentro de historicoNotas, já existir, a nota já foi inserida uma vez.
            if (valor.getMateria().getNome().equals(materias.getNome())){
                throw new RuntimeException("Materia duplicada");
            }
        }
        

        notas.setMateria(materias);

        return notasRepository.save(notas);
    }
}
