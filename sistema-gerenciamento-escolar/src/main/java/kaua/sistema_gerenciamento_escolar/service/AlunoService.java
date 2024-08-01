package kaua.sistema_gerenciamento_escolar.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;

@Service
public class AlunoService {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AlunoResumo alunoInformacoes(Integer aluno_id){
        Aluno aluno = alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        return toAlunoResumo(aluno);   
    }

    private AlunoResumo toAlunoResumo(Aluno aluno) {
        return modelMapper.map(aluno, AlunoResumo.class);
    }
}
