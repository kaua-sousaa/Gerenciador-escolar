package kaua.sistema_gerenciamento_escolar.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
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
public class AdminService {

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private FaltasRepository faltasRepository;
     
    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Map<String, Long> contarEntidades(){
        Map<String, Long> conteudo = new HashMap<>();
        conteudo.put("alunosQ", alunoRepository.count());
        conteudo.put("professoresQ", professorRepository.count());
        conteudo.put("materiasQ", materiasRepository.count());

        return conteudo;
    }

    public Set<Integer> getAlunosMatriculados(Integer materia_id){
        Set<Integer> alunosMatriculados = alunoRepository.findAlunoIdsByMateriaId(materia_id);
        return alunosMatriculados;
    }

    public List<AlunoDTO> getAlunos(){
        return alunoRepository.findAll().stream()
        .map(this::toResumoAluno)
        .collect(Collectors.toList());
    }
    public AlunoDTO getAluno(Integer aluno_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        return toResumoAluno(aluno);
    }

    public List<MateriaDTO> getMaterias(){
        return materiasRepository.findAll().stream()
        .map(this::toResumoMaterias)
        .collect(Collectors.toList());
    }

    public List<ProfessorDTO> getProfessores(){
        return professorRepository.findAll().stream()
        .map(this::toResumoProfessor)
        .collect(Collectors.toList());
    }   

    public ProfessorDTO getProfessor(Integer professor_id){
        Professor professor = professorRepository.findById(professor_id)
        .orElseThrow(() -> new EntityNotFoundException("Professor nao encontrado"));

        return toResumoProfessor(professor);
    }   

    @Transactional
    public void criarAluno(AlunoDTO alunoResumo) {
        Aluno aluno = new Aluno();

        aluno.setNome(alunoResumo.getNome());
        aluno.setEmail(alunoResumo.getEmail());
        aluno.setMatricula(alunoResumo.getMatricula());
        String senha = gerarSenhaBaseadaEmData(alunoResumo.getDataNascimento());
        aluno.setSenha(senha);
        aluno.setTelefone(alunoResumo.getTelefone());
        aluno.setDataNascimento(alunoResumo.getDataNascimento());
        alunoRepository.save(aluno);
    }

    @Transactional
    public void criarMateria(MateriaDTO materiaResumo, Integer professor_id) {
        Materias materia = new Materias();
        // verificar se já existe essa matéria

        Optional<Materias> materiaExiste = materiasRepository.findByNome(materiaResumo.getNome());

        if (materiaExiste.isPresent()) {
            throw new IllegalArgumentException("Já existe uma matéria com esse nome e professor!");
        }

        materia.setNome(materiaResumo.getNome());
        materia.setDescricao(materiaResumo.getDescricao());
        materia.setCargaHoraria(materiaResumo.getCargaHoraria());

        if (professor_id!=null){
            Professor professor = professorRepository.findById(professor_id)
            .orElseThrow(() -> new EntityNotFoundException("Professor nao encontrado"));
            System.out.println("passou por aqui");
            materia.setProfessor(professor);
        }

        materiasRepository.save(materia);
       // return toResumoMaterias(materia);
    }

    @Transactional
    public void criarProfessor(ProfessorDTO professorResumo) {
        Professor professor = new Professor();

        professor.setNome(professorResumo.getNome());
        professor.setEmail(professorResumo.getEmail());
        String senha = gerarSenhaBaseadaEmData(professorResumo.getDataNascimento());
        professor.setSenha(senha);
        professor.setTelefone(professorResumo.getTelefone());
        professor.setDataNascimento(professorResumo.getDataNascimento());
        professorRepository.save(professor);
    }

    @Transactional
    public void adicionarAlunoMateria(Integer materia_id, Set<Integer> alunos_id) {
        Materias materia = materiasRepository.findById(materia_id)
                .orElseThrow(() -> new EntityNotFoundException("Materia nao encontrada"));
        Set<Aluno> alunos = new HashSet<>(alunoRepository.findAllById(alunos_id));
        if (alunos.size() != alunos.size()) {
            throw new EntityNotFoundException("alunos não encontrados");
        }
        materia.setAlunos(alunos);
        for (Aluno aluno : alunos) {
            aluno.getMateriasMatriculadas().add(materia);
        }
        materiasRepository.save(materia);
    }

    @Transactional
    public void excluirAluno(Integer aluno_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        for (Materias materia : aluno.getMateriasMatriculadas()) {
            materia.getAlunos().remove(aluno);
            materiasRepository.save(materia);
        }
        faltasRepository.deleteByAlunoId(aluno_id);
        notasRepository.deleteByAlunoId(aluno_id);
        alunoRepository.deleteById(aluno_id);
    }

    @Transactional
    public void excluirProfessor(Integer professor_id){
        Professor professor = professorRepository.findById(professor_id)
        .orElseThrow(() -> new EntityNotFoundException("Professor nao encontrado"));
        List<Materias> materias = materiasRepository.findByProfessorId(professor_id);
        
        for (Materias materia: materias){
            materia.setProfessor(null);
        }

        professorRepository.delete(professor);
    }

    public void excluirProfessorMateria(Integer materia_id){
        Materias materia = materiasRepository.findById(materia_id)
        .orElseThrow(() -> new EntityNotFoundException("materia não encontrada"));
        materia.setProfessor(null);
        materiasRepository.save(materia);
    }

    public void excluirMateria(Integer materia_id){
        //ao apagar a materia, todos os registros relacionados a ela são apagados (arrumar isso depois)
        Materias materia = materiasRepository.findById(materia_id)
        .orElseThrow(() -> new EntityNotFoundException("materia não encontrada"));
        List<Faltas> faltas = faltasRepository.findByMateriaId(materia_id);
        List<Notas> notas = notasRepository.findByMateriaId(materia_id);
        faltasRepository.deleteAll(faltas); // deletando todas as faltas relacionadas a essa materia
        notasRepository.deleteAll(notas); // deletando todas as notas relacionadas a essa materia
        materiasRepository.delete(materia);
    }
    
    @Transactional
    public void editarAluno(Integer aluno_id, AlunoDTO alunoResumo){
        Aluno aluno = alunoRepository.findById(aluno_id)
        .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        aluno.setNome(alunoResumo.getNome());
        aluno.setEmail(alunoResumo.getEmail());
        aluno.setMatricula(alunoResumo.getMatricula());
        aluno.setDataNascimento(alunoResumo.getDataNascimento());
        aluno.setTelefone(alunoResumo.getTelefone());

        alunoRepository.save(aluno);
    }

     @Transactional
    public void editarMateria(Integer materia_id, MateriaDTO materiasResumo){
        Materias materia = materiasRepository.findById(materia_id)
        .orElseThrow(() -> new EntityNotFoundException("Materia não encontrada"));
        Professor professor = professorRepository.findById(materiasResumo.getProfessor().getId()).orElseThrow(()-> new EntityNotFoundException("Professor nao encontrado"));
        materia.setNome(materiasResumo.getNome());
        materia.setDescricao(materiasResumo.getDescricao());
        materia.setCargaHoraria(materiasResumo.getCargaHoraria());
        materia.setProfessor(professor);
        materiasRepository.save(materia);
    }
 
    @Transactional
    public void editarProfessor(Integer professor_id, ProfessorDTO professorResumo, Integer materia_id){
        Professor professor = professorRepository.findById(professor_id)
        .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));
        professor.setNome(professorResumo.getNome());
        professor.setEmail(professorResumo.getEmail());
        professor.setTelefone(professorResumo.getTelefone());
        professor.setDataNascimento(professorResumo.getDataNascimento());
        professorRepository.save(professor);
        if(materia_id!=null){
            Materias materia = materiasRepository.findById(materia_id)
            .orElseThrow(() -> new EntityNotFoundException("Materia nao encontrada"));
            materia.setProfessor(professor);
            materiasRepository.save(materia);
        }   
    }


    private MateriaDTO toResumoMaterias(Materias materias) {
        return modelMapper.map(materias, MateriaDTO.class);
    }

    private AlunoDTO toResumoAluno(Aluno aluno) {
        return modelMapper.map(aluno, AlunoDTO.class);
    }

    private ProfessorDTO toResumoProfessor(Professor professor) {
        return modelMapper.map(professor, ProfessorDTO.class);
    }
    private String gerarSenhaBaseadaEmData(LocalDate dataNascimento) {
    return dataNascimento.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
}
}
