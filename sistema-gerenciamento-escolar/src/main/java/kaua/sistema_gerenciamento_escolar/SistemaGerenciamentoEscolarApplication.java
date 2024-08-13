	package kaua.sistema_gerenciamento_escolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SistemaGerenciamentoEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGerenciamentoEscolarApplication.class, args);
	}
}
