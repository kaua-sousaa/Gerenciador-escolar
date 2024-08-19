package kaua.sistema_gerenciamento_escolar.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import kaua.sistema_gerenciamento_escolar.dto.AlunoDTO;
import kaua.sistema_gerenciamento_escolar.dto.FaltaDTO;
import kaua.sistema_gerenciamento_escolar.dto.MateriaDTO;
import kaua.sistema_gerenciamento_escolar.dto.NotaDTO;
import kaua.sistema_gerenciamento_escolar.service.AlunoService;
import kaua.sistema_gerenciamento_escolar.service.AuthService;

@Controller
public class AlunoController {
    
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AuthService authService;

    @GetMapping("/aluno")
    public String indexAluno(Model model){
        Integer id = authService.getIdAutenticado();

        AlunoDTO alunoResumo = alunoService.alunoInformacoes(id);
        List<FaltaDTO> faltaResumo = alunoService.faltasHistorico(id);
        List<NotaDTO> notaResumo = alunoService.notaHistorico(id);
        List<MateriaDTO> materiasResumos = alunoService.materiasAluno(id);
        model.addAttribute("aluno", alunoResumo); //info aluno
        model.addAttribute("faltas", faltaResumo); //info faltas
        model.addAttribute("notas", notaResumo); // info notas
        model.addAttribute("materias", materiasResumos); //info de materias
        return "aluno";
    }
}
