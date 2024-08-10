package kaua.sistema_gerenciamento_escolar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.FaltaDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Faltas;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.model.Notas;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.FaltasRepository;
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
    private FaltasRepository faltasRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProfessorDTO professorInformacoes(Integer professor_id){
        Professor professor = professorRepository.findById(professor_id)
        .orElseThrow(() -> new EntityNotFoundException("Professor nao encontrado"));   
        return toProfessorResumo(professor);
    }

    public List<MateriaDTO> professorMaterias(Integer professor_id){
        List <Materias> materias = materiasRepository.findByProfessorId(professor_id);
        List<MateriaDTO> materiasResumo = new ArrayList<>(); 
        
        for (Materias materia : materias){
            materiasResumo.add(toMateriasResumo(materia));
        }
        return materiasResumo;
    }

    public List<NotaDTO> professorNotas(Integer professor_id){
        List<Materias> materia = materiasRepository.findByProfessorId(professor_id);
        List<Notas> notas = notasRepository.findByMateriaIn(materia);
        List<NotaDTO> notaResumos = new ArrayList<>();
        for (Notas nota : notas){
            notaResumos.add(toResumoNota(nota));
        }
        return notaResumos;
    }

    public Set<FaltaDTO> professorFaltas(Integer professor_id){
        //Diferente, pois envia um resumo para cada aluno e não todos os resumos do banco
        List<Materias> materia = materiasRepository.findByProfessorId(professor_id);

        Set<Faltas> faltas = faltasRepository.findByMateriaIn(materia);
        Map<String, FaltaDTO> faltaResumos = new HashMap<>();
        
        for (Faltas falta : faltas){
            int alunoId = falta.getAluno().getId();
            int materiaId = falta.getMateria().getId();

            String chave = alunoId + "_" + materiaId;
            faltaResumos.put(chave, toResumoFalta(falta));
        }
        
        return new HashSet<>(faltaResumos.values());
    }

    public List<AlunoDTO> alunosProfessor(Integer professor_id){
        Materias materia = materiasRepository.findByProfessorId(professor_id).get(0);
        List<Aluno> alunos = alunoRepository.findAlunosByMateriasMatriculadas(materia);
        
        List<AlunoDTO> alunosResumo = new ArrayList<>();
        for (Aluno aluno : alunos){
            alunosResumo.add(toAlunoResumo(aluno));
        }
        return alunosResumo;
    }

    @Transactional
    public void aplicarNotas(Integer materia_id, List<Integer> alunosId, List<Double> nota1, List<Double> nota2) {
        boolean nota2Existe = true;
        for (int i=0; i<nota2.size() && nota2Existe;i++){
            if (nota2.get(i) == null){
                nota2Existe = false;
            }
        }
        for (int i=0;i<alunosId.size();i++){
            if ((nota1.get(i) == null || nota1.get(i) < 0) || nota1.get(i) >10){
                throw new IllegalArgumentException("A nota não pode ser menor que 0 ou maior que 10");
            }
            if (nota2Existe && (nota2.get(i) < 0 || nota2.get(i) > 10)) {
                throw new IllegalArgumentException("A nota não pode ser menor que 0 ou maior que 10");
            } //garante que todas as notas são diferentes de null e >=0 e <=10

            Aluno aluno = alunoRepository.findById(alunosId.get(i))
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
            Materias materia = materiasRepository.findById(materia_id)
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));
       
            boolean notaExistente = false;
            for (Notas valor : aluno.getHistoricoNotas()) {
                if (valor.getMateria().getNome().equals(materia.getNome())) {
                    valor.setNota1(nota1.get(i));   
                    if(nota2Existe){
                        valor.setNota2(nota2.get(i));
                    }
                    notasRepository.save(valor);
                    notaExistente = true;
                    break;
                }
            }

            if (!notaExistente){
                Notas notas = new Notas();
                notas.setNota1(nota1.get(i));
                if (nota2Existe){
                    notas.setNota2(nota2.get(i)); // Se a nota 2 existir(primeiro for garante isso), preenche ela. Caso nao exista, preencha com null;
                }else{
                    notas.setNota2(null);
                }
                notas.setAlunos(aluno);
                notas.setMateria(materia);
                notasRepository.save(notas);
            }       
        }   
    }


    @Transactional
    public void aplicarFaltas(Integer materia_id, List<Integer> alunosId, List<Integer> quantidades){
        for (int i=0; i< alunosId.size(); i++){
            Integer alunoId = alunosId.get(i);
            Integer quantidade = quantidades.get(i);

            Faltas falta = new Faltas();
            falta.setAluno(alunoRepository.findById(alunoId).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado")));
            falta.setMateria(materiasRepository.findById(materia_id).orElseThrow(() -> new EntityNotFoundException("Materia nao encontado")));
            falta.setQuantidade(quantidade);
            falta.setData(LocalDate.now());

            List<Faltas> faltasTotal = faltasRepository.findByAlunoIdAndMateriaId(alunoId, materia_id);

            int totalFaltas = 0;
            for (Faltas valorTotal: faltasTotal){
                totalFaltas += valorTotal.getQuantidade();  //total=0 depois total=4//  total =4 depois total = 8//
            }
            totalFaltas +=quantidade; 
            falta.setTotalFaltas(totalFaltas);

            if (falta.getTotalFaltas() < 20){
                falta.setSituacao("Dentro do limite");
            }else{
                falta.setSituacao("Reprovado por falta");
            } 

            faltasRepository.save(falta);
        }      
        
    } 
    
    private MateriaDTO toMateriasResumo(Materias materia){
        return modelMapper.map(materia, MateriaDTO.class);
    }

    private FaltaDTO toResumoFalta(Faltas falta) {
        return modelMapper.map(falta, FaltaDTO.class);
    }

    private NotaDTO toResumoNota(Notas nota) {
        return modelMapper.map(nota, NotaDTO.class);
    }

    private ProfessorDTO toProfessorResumo(Professor professor){
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    private AlunoDTO toAlunoResumo(Aluno aluno){
        return modelMapper.map(aluno, AlunoDTO.class);
    }
}

