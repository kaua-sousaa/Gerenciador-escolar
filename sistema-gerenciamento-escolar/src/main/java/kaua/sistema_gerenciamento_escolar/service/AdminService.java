package kaua.sistema_gerenciamento_escolar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ResumoMaterias;
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

    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    @Transactional
    public AlunoResumo criarAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        aluno.setMatricula(alunoDTO.matricula());
        aluno.setSenha(alunoDTO.senha());

        if (alunoDTO.matricula_id() != null) {
            Set<Materias> materias = new HashSet<>(materiasRepository.findAllById(alunoDTO.matricula_id()));
            aluno.setMateriasMatriculadas(materias);

            for (Materias materia : materias) {
                materia.getAlunos().add(aluno);
            }
        }

        alunoRepository.save(aluno);

        return toResumoAluno(aluno);
    }

    @Transactional
    public ResumoMaterias criarMateria(MateriaDTO materiaDTO) {
        Materias materia = new Materias();

        // verificar se já existe essa matéria
        Optional<Materias> materiaExiste = materiasRepository.findByNomeAndProfessorId(materiaDTO.nome(),
                materiaDTO.professor_id());

        if (materiaExiste.isPresent()) {
            throw new IllegalArgumentException("Já existe uma matéria com esse nome e professor!");
        }

        materia.setNome(materiaDTO.nome());
        materia.setDescricao(materiaDTO.descricao());
        materia.setCargaHoraria(materiaDTO.cargaHoraria());

        Professor professor = professorRepository.findById(materiaDTO.professor_id())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        materia.setProfessor(professor);

        materiasRepository.save(materia);

        return toResumoMaterias(materia);
    }

    @Transactional
    public ProfessorResumo criarProfessor(ProfessorDTO professorDTO) {
        Professor professor = new Professor();

        professor.setNome(professorDTO.nome());
        professor.setEmail(professorDTO.email());
        professor.setSenha(professorDTO.senha());
        professor.setTelefone(professorDTO.telefone());

        professorRepository.save(professor);

        return toResumoProfessor(professor);
    }

    @Transactional
    public AlunoResumo adicionarMateriaAluno(Integer aluno_id, Set<Integer> materia_id) {
        Aluno aluno = alunoRepository.findById(aluno_id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno nao encontrado"));

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

    private ResumoMaterias toResumoMaterias(Materias materias) {
        return modelMapper.map(materias, ResumoMaterias.class);
    }

    private AlunoResumo toResumoAluno(Aluno aluno) {
        return modelMapper.map(aluno, AlunoResumo.class);
    }

    private ProfessorResumo toResumoProfessor(Professor professor) {
        return modelMapper.map(professor, ProfessorResumo.class);
    }
}
