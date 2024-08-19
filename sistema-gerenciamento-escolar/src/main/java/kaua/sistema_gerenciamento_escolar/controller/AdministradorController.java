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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.ProfessorDTO;
import kaua.sistema_gerenciamento_escolar.service.AdminService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String redirecionarIndex(){
        return "redirect:/administrador";
    }

    @GetMapping("/gerenciarMateriasAdicionar/{materia_id}")
    public String adicionarMateriaEmAluno(@PathVariable Integer materia_id, Model model){
        List<AlunoDTO> alunos = adminService.getAlunos();
        Set<Integer> alunosMatriculados = adminService.getAlunosMatriculados(materia_id);
        model.addAttribute("alunosMatriculados", alunosMatriculados);
        model.addAttribute("alunos", alunos);
        return "adicionarMateriaAluno";
    }

    @GetMapping("/editarProfessorGet/{professor_id}")
    public String editarProfessor(@PathVariable Integer professor_id, Model model){
        ProfessorDTO professor = adminService.getProfessor(professor_id);
        List<MateriaDTO> materias = adminService.getMaterias();
        model.addAttribute("professor", professor);
        model.addAttribute("materias", materias);
        return "editarProfessor";
    }

    @GetMapping("/editarAlunoGet/{aluno_id}")
    public String editarAluno(@PathVariable Integer aluno_id, Model model){
        AlunoDTO aluno = adminService.getAluno(aluno_id); 
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }

    @GetMapping("/gerenciarMaterias")
    public String listaGerenciarMaterias(Model model){
        List<MateriaDTO> materias = adminService.getMaterias();
        List<ProfessorDTO> professor = adminService.getProfessores();
        List<AlunoDTO> aluno = adminService.getAlunos();
        model.addAttribute("professores", professor);
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        return "gerenciarMaterias";
    }

    @GetMapping("/gerenciarAluno")
    public String listaGerenciarAlunos(Model model){
        List<AlunoDTO> aluno = adminService.getAlunos();
        List<MateriaDTO> materias = adminService.getMaterias();
        model.addAttribute("alunos", aluno);
        model.addAttribute("materias", materias);
        return "gerenciarAluno";
    }

    @GetMapping("/gerenciarProfessor")
    public String listaGerenciarProfessores (Model model){
        List<ProfessorDTO> professor = adminService.getProfessores();
        model.addAttribute("professores", professor);
        return "gerenciarProfessor";
    }

    @GetMapping("")
    public String indexConteudo (Model model){
        List<ProfessorDTO> professor = adminService.getProfessores();  //Q = quantidade
        Map<String, Long> conteudo = adminService.contarEntidades();
        model.addAttribute("alunQ", conteudo.get("alunosQ"));
        model.addAttribute("profQ", conteudo.get("professoresQ"));
        model.addAttribute("mateQ", conteudo.get("materiasQ"));
        model.addAttribute("professores", professor); 
        return "index";
    }

    @PostMapping("/salvarAluno")
    public String criarAluno(@ModelAttribute AlunoDTO alunoResumo){
        adminService.criarAluno(alunoResumo);
        return "redirect:/administrador";
    }

    @PostMapping("/salvarMateria")
    public String criarMateria(@RequestParam(value="professor_id", required = false) Integer professor_id, @ModelAttribute MateriaDTO materiasResumo){
       adminService.criarMateria(materiasResumo, professor_id);
       return "redirect:/administrador";  
    }  

    @PostMapping("/salvarProfessor")
    public String criarProfessor(@ModelAttribute ProfessorDTO professorResumo){
        adminService.criarProfessor(professorResumo);
        return "redirect:/administrador";
    }

    @PostMapping("/adicionarAlunoMateria/{materia_id}")
    public String adicionarAlunoMateria(@PathVariable Integer materia_id, @RequestParam(value = "aluno_id", required= false) Set<String> alunosId){
        Set<Integer> alunos_id = null;
        if (alunosId !=null && !alunosId.isEmpty()){
            alunos_id = alunosId.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        }
        adminService.adicionarAlunoMateria(materia_id, alunos_id);
        return "redirect:/administrador/gerenciarMaterias";
    }

    @PostMapping("/deletarAluno/{aluno_id}")
    public String deletarAluno(@PathVariable Integer aluno_id){
        adminService.excluirAluno(aluno_id);
        return "redirect:/administrador/gerenciarAluno";
    }

    @PostMapping("/deletarProfessor/{professor_id}")
    public String deletarProfessor(@PathVariable Integer professor_id){
        adminService.excluirProfessor(professor_id);
        return "redirect:/administrador/gerenciarProfessor";
    }

    @GetMapping("/deletarProfessorMateria/{materia_id}")
    public String deletarProfessorMateria(@PathVariable Integer materia_id){
        adminService.excluirProfessorMateria(materia_id);
        return "redirect:/administrador/gerenciarMaterias";
    }

    @GetMapping("/deletarMateria/{materia_id}")
    public String deletarMateria(@PathVariable Integer materia_id){
        adminService.excluirMateria(materia_id);
        return "redirect:/administrador/gerenciarMaterias";
    }

    @PostMapping("/editarAluno/{aluno_id}")
    public String editarAluno(@PathVariable Integer aluno_id, @ModelAttribute AlunoDTO alunoResumo){
        adminService.editarAluno(aluno_id, alunoResumo);
        return "redirect:/administrador/gerenciarAluno";
    }

    @PostMapping("/editarProfessor/{professor_id}")
    public String editarProfessor(@RequestParam(value="materia_id", required = false) Integer materia_id, @PathVariable Integer professor_id, @ModelAttribute ProfessorDTO professorResumo){
        adminService.editarProfessor(professor_id, professorResumo, materia_id);
        return "redirect:/administrador/gerenciarProfessor";
    }
}
