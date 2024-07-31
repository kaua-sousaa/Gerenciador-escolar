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
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ResumoFalta;
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
    public Notas aplicarNotas(NotasDTO notasDTO) {
        Notas notas = new Notas();

        if (notasDTO.nota1() < 0 || notasDTO.nota1() > 10 ||
                notasDTO.nota2() < 0 || notasDTO.nota2() > 10) {
            throw new IllegalArgumentException("A nota não pode ser menor que 0");
        }
        notas.setNota1(notasDTO.nota1());
        notas.setNota2(notasDTO.nota2());

        Aluno aluno = alunoRepository.findById(notasDTO.aluno_id())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        notas.setAlunos(aluno);

        Materias materias = materiasRepository.findById(notasDTO.materia_id())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada"));

        for (Notas valor : aluno.getHistoricoNotas()) {
            // Se o valor(nome da materia), dentro de historicoNotas, já existir, a nota já
            // foi inserida uma vez.

            if (valor.getMateria().getNome().equals(materias.getNome())) {
                throw new IllegalArgumentException("Materia duplicada");
            }
        }

        notas.setMateria(materias);

        return notasRepository.save(notas);
    }

    @Transactional
    public Notas alterarNotas(Integer nota_id, List<Double> notas) {
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

        return notasRepository.save(nota);
    }

    @Transactional
    public ResumoFalta aplicarFaltas(FaltasDTO faltasDTO){
        Faltas falta = new Faltas();

        if (faltasDTO.quantidade() <= 0){
            throw new IllegalArgumentException("Falta não pode ser menor ou igual 0");
        }

        falta.setAluno(alunoRepository.findById(faltasDTO.aluno_id()).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado")));
        falta.setMateria(materiasRepository.findById(faltasDTO.materia_id()).orElseThrow(() -> new EntityNotFoundException("Materia nao encontado")));
        falta.setData(LocalDate.now());
        falta.setQuantidade(faltasDTO.quantidade());
 
        List<Faltas> faltas = faltasRepository.findByAlunoId(faltasDTO.aluno_id());
        
        int totalFaltas = 0;
        for (Faltas valor: faltas){
            totalFaltas += valor.getQuantidade();  //total=0 depois total=4//  total =4 depois total = 8//
        }
        totalFaltas +=faltasDTO.quantidade();   


        falta.setTotalFaltas(totalFaltas);

        if (falta.getTotalFaltas() < 20){
            falta.setSituacao("Dentro do limite");
        }else{
            falta.setSituacao("Reprovado por falta");
        }
        
        faltasRepository.save(falta);

        return toFaltasResumidoDTO(falta);
    } 
    
    private ResumoFalta toFaltasResumidoDTO(Faltas falta) {
        return modelMapper.map(falta, ResumoFalta.class);
    }
}

