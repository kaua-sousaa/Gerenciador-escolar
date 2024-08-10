package kaua.sistema_gerenciamento_escolar.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.AlunoResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.MateriasResumo;
import kaua.sistema_gerenciamento_escolar.dto.dtosResumidos.ProfessorResumo;
import kaua.sistema_gerenciamento_escolar.service.AdminService;

@Controller
public class AdministradorController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping("/gerenciarMateriasAdicionar/{materia_id}")
    public String adicionarMateriaEmAluno(@PathVariable Integer materia_id, Model model){
        List<AlunoResumo> alunos = adminService.getAlunos();
        Set<Integer> alunosMatriculados = adminService.getAlunosMatriculados(materia_id);
        model.addAttribute("alunosMatriculados", alunosMatriculados);
        model.addAttribute("alunos", alunos);
        return "adicionarMateriaAluno";
    }

    @GetMapping("/editarProfessorGet/{professor_id}")
    public String editarProfessor(@PathVariable Integer professor_id, Model model){
        ProfessorResumo professor = adminService.getProfessor(professor_id);
        List<MateriasResumo> materias = adminService.getMaterias();
        model.addAttribute("professor", professor);
        model.addAttribute("materias", materias);
        return "editarProfessor";
    }

    @GetMapping("/editarAlunoGet/{aluno_id}")
    public String editarAluno(@PathVariable Integer aluno_id, Model model){
        AlunoResumo aluno = adminService.getAluno(aluno_id); 
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }

    @GetMapping("/gerenciarMaterias")
    public String listaGerenciarMaterias(Model model){
        List<MateriasResumo> materias = adminService.getMaterias();
        List<ProfessorResumo> professor = adminService.getProfessores();
        List<AlunoResumo> aluno = adminService.getAlunos();
        model.addAttribute("professores", professor);
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        return "gerenciarMaterias";
    }

    @GetMapping("/gerenciarAluno")
    public String listaGerenciarAlunos(Model model){
        List<AlunoResumo> aluno = adminService.getAlunos();
        List<MateriasResumo> materias = adminService.getMaterias();
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        model.addAttribute("aluno", new AlunoResumo());
        return "gerenciarAluno";
    }

    @GetMapping("/gerenciarProfessor")
    public String listaGerenciarProfessores (Model model){
        List<ProfessorResumo> professor = adminService.getProfessores();
        model.addAttribute("professores", professor);
        return "gerenciarProfessor";
    }

    @GetMapping("/")
    public String indexConteudo (Model model){
        List<ProfessorResumo> professor = adminService.getProfessores();  //Q = quantidade
        Map<String, Long> conteudo = adminService.contarEntidades();
        model.addAttribute("alunQ", conteudo.get("alunosQ"));
        model.addAttribute("profQ", conteudo.get("professoresQ"));
        model.addAttribute("mateQ", conteudo.get("materiasQ"));
        model.addAttribute("professores", professor);
        model.addAttribute("materiaDTO", new MateriaDTO());  
        return "index";
    }

    @PostMapping("/salvarAluno")
    public String criarAluno(@ModelAttribute AlunoResumo alunoResumo){
        adminService.criarAluno(alunoResumo);
        return "redirect:/";
    }

    @PostMapping("/salvarMateria")
    public String criarMateria(@RequestParam("professor_id") Integer professor_id, @ModelAttribute MateriasResumo materiasResumo){

       adminService.criarMateria(materiasResumo, professor_id);
       return "redirect:/";  
    }  

    @PostMapping("/salvarProfessor")
    public String criarProfessor(@ModelAttribute ProfessorDTO professorDTO){
        adminService.criarProfessor(professorDTO);
        return "redirect:/";
    }

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

    @PostMapping("/editarAluno/{aluno_id}")
    public String editarAluno(@PathVariable Integer aluno_id, @ModelAttribute AlunoResumo alunoResumo){
        adminService.editarAluno(aluno_id, alunoResumo);
        return "redirect:/gerenciarAluno";
    }

    @PostMapping("/editarProfessor/{professor_id}")
    public String editarProfessor(@RequestParam("materia_id") Integer materia_id, @PathVariable Integer professor_id, @ModelAttribute ProfessorResumo professorResumo){
        adminService.editarProfessor(professor_id, professorResumo, materia_id);
        return "redirect:/gerenciarProfessor";
    }
}
