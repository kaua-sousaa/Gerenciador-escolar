package kaua.sistema_gerenciamento_escolar.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ProfessorResumo;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.service.AdminService;

@Controller
public class AdministradorController {
    

    @Autowired
    private AdminService adminService;

    @GetMapping("/gerenciarMateriasAdicionar/{materia_id}")
    public String adicionarMateria(@PathVariable Integer materia_id, Model model){
        List<AlunoResumo> alunos = adminService.getAluno();
        Set<Integer> alunosMatriculados = adminService.getAlunosMatriculados(materia_id);
        model.addAttribute("alunosMatriculados", alunosMatriculados);
        model.addAttribute("alunos", alunos);
        return "adicionarMateriaAluno";
    }


    @GetMapping("/gerenciarMaterias")
    public String listarMaterias(Model model){
        List<MateriaDTO> materias = adminService.getMateriasDTO();
        List<ProfessorResumo> professor = adminService.getProfessor();
        List<AlunoResumo> aluno = adminService.getAluno();
        model.addAttribute("professores", professor);
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        model.addAttribute("materiaDTO", new MateriaDTO());
        return "gerenciarMaterias";
    }

    @GetMapping("/gerenciarAluno")
    public String listarAlunos(Model model){
        List<AlunoResumo> aluno = adminService.getAlunos();
        List<MateriasResumo> materias = adminService.getMaterias();
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        model.addAttribute("aluno", new AlunoResumo());
        return "gerenciarAluno";
    }

    @GetMapping("/gerenciarProfessor")
    public String listaGerenciarProfessores (Model model){
        List<ProfessorResumo> professor = adminService.getProfessor();
        model.addAttribute("professores", professor);
        return "gerenciarProfessor";
    }

    @GetMapping("/")
    public String listaProfessoresIndex (Model model){
        List<ProfessorResumo> professor = adminService.getProfessor();
        model.addAttribute("professores", professor);
        model.addAttribute("materiaDTO", new MateriaDTO());  
        return "index";
    }

    @PostMapping("/salvarAluno")
    public String criarAluno(@ModelAttribute AlunoDTO alunoDTO){
        adminService.criarAluno(alunoDTO);
        return "redirect:/";
    }

    @PostMapping("/salvarMateria")
    public String criarMateria(@ModelAttribute MateriaDTO materiaDTO){
       adminService.criarMateria(materiaDTO);
       return "redirect:/";  
    }  

    @PostMapping("/salvarProfessor")
    public String criarProfessor(@ModelAttribute ProfessorDTO professorDTO){
        adminService.criarProfessor(professorDTO);
        return "redirect:/";
    }

  /*   @PutMapping("/adicionarMateriaAluno/{aluno_id}")
    public String adicionarMateriaAluno(@PathVariable Integer aluno_id, @RequestBody Set<Integer> materias_id){
        adminService.adicionarMateriaAluno(aluno_id, materias_id);

        return "redirect:/gerenciarAluno";
    } */

    @PostMapping("/adicionarAlunoMateria/{materia_id}")
    public String adicionarAlunoMateria(@PathVariable Integer materia_id, @RequestParam("aluno_id") Set<String> alunosId){
        Set<Integer> alunos_id = alunosId.stream()
            .map(Integer::parseInt)
            .collect(Collectors.toSet());

        adminService.adicionarAlunoMateria(materia_id, alunos_id);

        return "redirect:/gerenciarMaterias";
    }

    @PostMapping("/deletarAluno/{aluno_id}")
    public String deletarAluno(@PathVariable Integer aluno_id){
        adminService.excluirAluno(aluno_id);
        return "redirect:/gerenciarAluno";
    }

    @PostMapping("/deletarProfessor/{professor_id}")
    public String deletarProfessor(@PathVariable Integer professor_id){
        adminService.excluirProfessor(professor_id);
        return "redirect:/gerenciarProfessor";
    }
}
