package kaua.sistema_gerenciamento_escolar.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import kaua.sistema_gerenciamento_escolar.security.CustomUserDetails;

@Service
public class AuthService {
    
    public Integer getIdAutenticado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
