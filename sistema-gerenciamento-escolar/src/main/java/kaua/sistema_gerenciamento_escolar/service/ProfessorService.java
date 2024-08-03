package kaua.sistema_gerenciamento_escolar.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kaua.sistema_gerenciamento_escolar.dto.FaltasDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotasDTO;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.FaltaResumo;
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
public class ProfessorService {

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private FaltasRepository faltasRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MateriasRepository materiasRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public NotaResumo aplicarNotas(NotasDTO notasDTO) {
        Notas notas = new Notas();

        if (notasDTO.getNota1() < 0 || notasDTO.getNota1() > 10 ||
                notasDTO.getNota2() < 0 || notasDTO.getNota2() > 10) {
            throw new IllegalArgumentException("A nota não pode ser menor que 0");
        }
        notas.setNota1(notasDTO.getNota1());
        notas.setNota2(notasDTO.getNota2());

        Aluno aluno = alunoRepository.findById(notasDTO.getAluno_id())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        notas.setAlunos(aluno);

        Materias materias = materiasRepository.findById(notasDTO.getMateria_id())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));

        for (Notas valor : aluno.getHistoricoNotas()) {
            // Se o valor(nome da materia), dentro de historicoNotas, já existir, a nota já
            // foi inserida uma vez.

            if (valor.getMateria().getNome().equals(materias.getNome())) {
                throw new IllegalArgumentException("Materia duplicada");
            }
        }

        notas.setMateria(materias);

        notasRepository.save(notas);

        return toResumoNota(notas);
    }

    @Transactional
    public NotaResumo alterarNotas(Integer nota_id, List<Double> notas) {
        // Anotações: nota 1 sempre haverá nota
        // 1º mudar nota "av1", se nota 1 tiver alteração e nota 2 não vir nada.
        // 2º adicionar nota "av2", se vir duas notas e nota 1 for igual.
        // se nota 1 e nota 2 tiverem alteração, alterar ambas;

        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("A lista de notas não pode estar vazia");
        }

        Notas nota = notasRepository.findById(nota_id)
                .orElseThrow(() -> new EntityNotFoundException());

        Double nota1 = null;
        Double nota2 = null;
        if (notas.size() > 0) {
            nota1 = notas.get(0);
        }
        if (notas.size() > 1) {
            nota2 = notas.get(1);
        }

        if ((nota1 != null && (nota1 < 0 || nota1 > 10)) ||
            (nota2 != null && (nota2 < 0 || nota2 > 10))) {
            throw new IllegalArgumentException("A nota não pode ser menor que 0 ou maior que 10");
        }

        if ((nota1 != null && nota1.equals(nota.getNota1()) && nota2 == null) ||
            (nota1 != null && nota1.equals(nota.getNota1()) && nota2 != null && nota2.equals(nota.getNota2()))) { //Nenhuma modificação feita
            throw new IllegalArgumentException("Nenhuma modificação foi feita");
        }else if (nota1 != null && nota2 == null && !nota1.equals(nota.getNota1())) { // ocasião 1º
            nota.setNota1(nota1);
        } else if (nota1 != null && nota1.equals(nota.getNota1()) && nota2 != null) { // 2º ocasião
            nota.setNota2(nota2);
        } else if ((nota1 != null && nota2 != null) && !nota1.equals(nota.getNota1()) // 3º ocasião
                && !nota2.equals(nota.getNota2())) { 
            nota.setNota1(nota1);
            nota.setNota2(nota2);
        } else {
            throw new IllegalArgumentException("parametros invalidos");
        }

        notasRepository.save(nota);

        return toResumoNota(nota);
    }

    @Transactional
    public FaltaResumo aplicarFaltas(FaltasDTO faltasDTO){
        Faltas falta = new Faltas();

        if (faltasDTO.getQuantidade() <= 0){
            throw new IllegalArgumentException("Falta não pode ser menor ou igual 0");
        }

        falta.setAluno(alunoRepository.findById(faltasDTO.getAluno_id()).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado")));
        falta.setMateria(materiasRepository.findById(faltasDTO.getMateria_id()).orElseThrow(() -> new EntityNotFoundException("Materia nao encontado")));
        falta.setData(LocalDate.now());
        System.out.println("Quantidade faltas"+ faltasDTO.getQuantidade());
        falta.setQuantidade(faltasDTO.getQuantidade());
 
        List<Faltas> faltas = faltasRepository.findByAlunoIdAndMateriaId(faltasDTO.getAluno_id(), faltasDTO.getMateria_id());
        
        int totalFaltas = 0;
        for (Faltas valor: faltas){
            totalFaltas += valor.getQuantidade();  //total=0 depois total=4//  total =4 depois total = 8//
        }
        totalFaltas +=faltasDTO.getQuantidade();   


        falta.setTotalFaltas(totalFaltas);

        if (falta.getTotalFaltas() < 20){
            falta.setSituacao("Dentro do limite");
        }else{
            falta.setSituacao("Reprovado por falta");
        }
        
        faltasRepository.save(falta);

        return toResumoFalta(falta);
    } 
    
    private FaltaResumo toResumoFalta(Faltas falta) {
        return modelMapper.map(falta, FaltaResumo.class);
    }

    private NotaResumo toResumoNota(Notas nota) {
        return modelMapper.map(nota, NotaResumo.class);
    }
}

