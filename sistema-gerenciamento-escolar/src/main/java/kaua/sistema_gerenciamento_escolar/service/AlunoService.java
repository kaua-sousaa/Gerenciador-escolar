package kaua.sistema_gerenciamento_escolar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
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
