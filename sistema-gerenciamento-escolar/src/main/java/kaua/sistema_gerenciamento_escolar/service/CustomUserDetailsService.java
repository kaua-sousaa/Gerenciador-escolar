package kaua.sistema_gerenciamento_escolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kaua.sistema_gerenciamento_escolar.model.Administrador;
import kaua.sistema_gerenciamento_escolar.model.Aluno;
import kaua.sistema_gerenciamento_escolar.model.Professor;
import kaua.sistema_gerenciamento_escolar.repository.AdministradorRepository;
import kaua.sistema_gerenciamento_escolar.repository.AlunoRepository;
import kaua.sistema_gerenciamento_escolar.repository.ProfessorRepository;
import kaua.sistema_gerenciamento_escolar.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Aluno aluno = alunoRepository.findByEmail(email);

        if (aluno != null){
            return new CustomUserDetails(aluno.getId(), aluno.getEmail(), aluno.getSenha(), aluno.getRole_nome());
        }

        Professor professor = professorRepository.findByEmail(email);

        if (professor != null){
            return new CustomUserDetails(professor.getId(), professor.getEmail(), professor.getSenha(), professor.getRole_nome());
        }

        Administrador adm = administradorRepository.findByEmail(email);
        if (adm!=null){
            return new CustomUserDetails(adm.getId(), adm.getEmail(), adm.getSenha(), adm.getRole_nome());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    
}
