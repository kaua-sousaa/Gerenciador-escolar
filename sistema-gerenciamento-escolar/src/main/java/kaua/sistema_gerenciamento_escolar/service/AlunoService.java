package kaua.sistema_gerenciamento_escolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.FaltaResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.NotaResumo;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Faltas;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.model.Notas;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.FaltasRepository;
import kaua.sistema_gerenciamento_escolar.repository.MateriasRepository;
import kaua.sistema_gerenciamento_escolar.repository.NotasRepository;

@Service
public class AlunoService {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private FaltasRepository faltasRepository;

    public AlunoResumo alunoInformacoes(Integer aluno_id){
        Aluno aluno = alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        return toAlunoResumo(aluno);   
    }

    public List<NotaResumo> notaHistorico(Integer aluno_id){
        List <Notas> notas = notasRepository.findByAlunosId(aluno_id);
        List<NotaResumo> notasResumo = new ArrayList<>();

        for (Notas nota : notas){
            notasResumo.add(toNotasResumo(nota));
        }

        return notasResumo;
    }

    public List<FaltaResumo> faltasHistorico(Integer aluno_id){
        List <Faltas> faltas = faltasRepository.findByAlunoId(aluno_id);
        List<FaltaResumo> faltasResumo = new ArrayList<>();

        for (Faltas nota : faltas){
            faltasResumo.add(toFaltaResumo(nota));
        }

        return faltasResumo;
    }

    public List<MateriasResumo> materiasAluno(Integer aluno_id){
        List<Materias> materias = materiasRepository.findMateriaByAlunoId(aluno_id);
        List<MateriasResumo> materiasResumos = new ArrayList<>();
        for (Materias materia : materias){
            materiasResumos.add(toMateriaResumo(materia));
        }
        return materiasResumos;
    }

    private FaltaResumo toFaltaResumo(Faltas faltas) {
        return modelMapper.map(faltas, FaltaResumo.class);
    }

    private NotaResumo toNotasResumo(Notas notas) {
        return modelMapper.map(notas, NotaResumo.class);
    }

    private AlunoResumo toAlunoResumo(Aluno aluno) {
        return modelMapper.map(aluno, AlunoResumo.class);
    }

    private MateriasResumo toMateriaResumo(Materias materia){
        return modelMapper.map(materia, MateriasResumo.class);
    }
}
