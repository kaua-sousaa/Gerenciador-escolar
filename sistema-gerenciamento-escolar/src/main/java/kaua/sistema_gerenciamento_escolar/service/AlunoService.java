package kaua.sistema_gerenciamento_escolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;

@Service
public class AlunoService {
    
    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno alunoInformacoes(Integer aluno_id){
        return alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException());
    }
}
