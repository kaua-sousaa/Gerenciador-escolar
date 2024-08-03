package kaua.sistema_gerenciamento_escolar.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
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
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ProfessorResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Materias;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.MateriasRepository;
import kaua.sistema_gerenciamento_escolar.repository.NotasRepository;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;

@Service
public class AdminService {

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Set<Integer> getAlunosMatriculados(Integer materia_id){
        Set<Integer> alunosMatriculados = alunoRepository.findAlunoIdsByMateriaId(materia_id);
        return alunosMatriculados;
    }

    public List<AlunoResumo> getAlunos() {
        return alunoRepository.findAll().stream()
        .map(this::toResumoAluno)
        .collect(Collectors.toList());
    }

    public List<MateriasResumo> getMaterias(){
        return materiasRepository.findAll().stream()
        .map(this::toResumoMaterias)
        .collect(Collectors.toList());
    }

    public List<MateriaDTO> getMateriasDTO(){
        return materiasRepository.findAll().stream()
        .map(this::toResumoMateriasDTO)
        .collect(Collectors.toList());
    }

    public List<ProfessorResumo> getProfessor(){
        return professorRepository.findAll().stream()
        .map(this::toResumoProfessor)
        .collect(Collectors.toList());
    }

    public List<AlunoResumo> getAluno(){
        return alunoRepository.findAll().stream()
        .map(this::toResumoAluno)
        .collect(Collectors.toList());
    }

    @Transactional
    public AlunoResumo criarAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(alunoDTO.getNome());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setMatricula(alunoDTO.getMatricula());
        String senha = gerarSenhaBaseadaEmData(alunoDTO.getDataNascimento());
        aluno.setSenha(senha);
        aluno.setTelefone(alunoDTO.getTelefone());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());

        if (alunoDTO.getMatricula_id() != null) {
            Set<Materias> materias = new HashSet<>(materiasRepository.findAllById(alunoDTO.getMatricula_id()));
            aluno.setMateriasMatriculadas(materias);

            for (Materias materia : materias) {
                materia.getAlunos().add(aluno);
            }
        }

        alunoRepository.save(aluno);

        return toResumoAluno(aluno);
    }

    @Transactional
    public MateriasResumo criarMateria(MateriaDTO materiaDTO) {
        Materias materia = new Materias();

        // verificar se já existe essa matéria
        Optional<Materias> materiaExiste = materiasRepository.findByNomeAndProfessorId(materiaDTO.getNome(),
                materiaDTO.getProfessor_id());

        if (materiaExiste.isPresent()) {
            throw new IllegalArgumentException("Já existe uma matéria com esse nome e professor!");
        }

        materia.setNome(materiaDTO.getNome());
        materia.setDescricao(materiaDTO.getDescricao());
        materia.setCargaHoraria(materiaDTO.getCargaHoraria());

        Professor professor = professorRepository.findById(materiaDTO.getProfessor_id())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        materia.setProfessor(professor);

        materiasRepository.save(materia);

        return toResumoMaterias(materia);
    }

    @Transactional
    public ProfessorResumo criarProfessor(ProfessorDTO professorDTO) {
        Professor professor = new Professor();

        professor.setNome(professorDTO.getNome());
        professor.setEmail(professorDTO.getEmail());
        String senha = gerarSenhaBaseadaEmData(professorDTO.getDataNascimento());
        professor.setSenha(senha);
        professor.setTelefone(professorDTO.getTelefone());
        professor.setDataNascimento(professorDTO.getDataNascimento());
        professorRepository.save(professor);

        return toResumoProfessor(professor);
    }

    @Transactional
    public AlunoResumo adicionarMateriaAluno(Integer aluno_id, Set<Integer> materia_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontradooo"));
        Set<Materias> materias = new HashSet<>(materiasRepository.findAllById(materia_id));
        if (materias.size() != materia_id.size()) {
            throw new EntityNotFoundException("Materias não encontradas");
        }
        aluno.setMateriasMatriculadas(materias);
        for (Materias materia : materias) {
            materia.getAlunos().add(aluno);
        }
        alunoRepository.save(aluno);
        return toResumoAluno(aluno);
    }

    @Transactional
    public MateriasResumo adicionarAlunoMateria(Integer materia_id, Set<Integer> alunos_id) {
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
        return toResumoMaterias(materia);
    }

    @Transactional
    public void excluirAluno(Integer aluno_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

        for (Materias materia : aluno.getMateriasMatriculadas()) {
            materia.getAlunos().remove(aluno);
            materiasRepository.save(materia);
        }

        notasRepository.deleteByAlunoId(aluno_id);

        alunoRepository.deleteById(aluno_id);
    }

    @Transactional
    public void excluirProfessor(Integer professor_id){
        Professor professor = professorRepository.findById(professor_id)
        .orElseThrow(() -> new EntityNotFoundException("Professor nao encontrado"));

        professorRepository.delete(professor);
    }

    private MateriasResumo toResumoMaterias(Materias materias) {
        return modelMapper.map(materias, MateriasResumo.class);
    }

    private MateriaDTO toResumoMateriasDTO(Materias materias) {
        //modificao feita pq em MateriaDTO a variavel é "professor_id", mas na entidade Materias é "professor"
        MateriaDTO materiaDTO = modelMapper.map(materias, MateriaDTO.class);
        if (materias != null){
            materiaDTO.setProfessor_id(materias.getProfessor().getId());
        }

        return materiaDTO;
    }

    private AlunoResumo toResumoAluno(Aluno aluno) {
        return modelMapper.map(aluno, AlunoResumo.class);
    }

    private ProfessorResumo toResumoProfessor(Professor professor) {
        return modelMapper.map(professor, ProfessorResumo.class);
    }

    private String gerarSenhaBaseadaEmData(LocalDate dataNascimento) {
    return dataNascimento.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
}
}
