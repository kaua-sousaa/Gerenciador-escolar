package kaua.sistema_gerenciamento_escolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
            return User.withUsername(aluno.getEmail())
            .password(aluno.getSenha())
            .roles("ALUNO")
            .build();
        }

        Professor professor = professorRepository.findByEmail(email);

        if (professor != null){
            return User.withUsername(professor.getEmail())
            .password(professor.getSenha())
            .roles("PROFESSOR")
            .build();
        }

        Administrador adm = administradorRepository.findByEmail(email);
        if (adm!=null){
            return User.withUsername(adm.getEmail())
            .password(adm.getSenha())
            .roles("ADMIN")
            .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    
}
