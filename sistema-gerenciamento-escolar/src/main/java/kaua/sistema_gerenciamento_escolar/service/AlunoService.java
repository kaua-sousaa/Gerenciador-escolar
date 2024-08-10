package kaua.sistema_gerenciamento_escolar.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.FaltaDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotaDTO;
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

    public AlunoDTO alunoInformacoes(Integer aluno_id){
        Aluno aluno = alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        return toAlunoResumo(aluno);   
    }

    public List<NotaDTO> notaHistorico(Integer aluno_id){
        List <Notas> notas = notasRepository.findByAlunosId(aluno_id);
        List<NotaDTO> notasResumo = new ArrayList<>();

        for (Notas nota : notas){
            notasResumo.add(toNotasResumo(nota));
        }

        return notasResumo;
    }

    public List<FaltaDTO> faltasHistorico(Integer aluno_id){
        List <Faltas> faltas = faltasRepository.findByAlunoId(aluno_id);
        List<FaltaDTO> faltasResumo = new ArrayList<>();

        for (Faltas nota : faltas){
            faltasResumo.add(toFaltaResumo(nota));
        }

        return faltasResumo;
    }

    public List<MateriaDTO> materiasAluno(Integer aluno_id){
        List<Materias> materias = materiasRepository.findMateriaByAlunoId(aluno_id);
        List<MateriaDTO> materiasResumos = new ArrayList<>();
        for (Materias materia : materias){
            materiasResumos.add(toMateriaResumo(materia));
        }
        return materiasResumos;
    }

    private FaltaDTO toFaltaResumo(Faltas faltas) {
        return modelMapper.map(faltas, FaltaDTO.class);
    }

    private NotaDTO toNotasResumo(Notas notas) {
        return modelMapper.map(notas, NotaDTO.class);
    }

    private AlunoDTO toAlunoResumo(Aluno aluno) {
        return modelMapper.map(aluno, AlunoDTO.class);
    }

    private MateriaDTO toMateriaResumo(Materias materia){
        return modelMapper.map(materia, MateriaDTO.class);
    }
}
