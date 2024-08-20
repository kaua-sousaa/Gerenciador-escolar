# Sistema de Gerenciamento Escolar/Universitário

## Visão Geral
O sistema de gerenciamento escolar/universitário é uma aplicação desenvolvida em **Spring Boot** no back-end e **HTML/Bootstrap** no front-end. O objetivo do sistema é facilitar o gerenciamento de alunos, professores, matérias, notas e faltas em uma instituição de ensino. O sistema foi projetado com foco na automação de processos administrativos e na disponibilização de informações acadêmicas para alunos e professores.

## Funcionalidades

### **Administração**
- **Adicionar Alunos, Professores e Matérias**: O administrador pode criar novos alunos, professores e matérias no sistema.
- **Gerenciar Matérias de Alunos**: O administrador pode matricular alunos em matérias, vincular matérias a professores e editar essas associações.
- **Deletar**: O administrador pode excluir qualquer entidade do sistema.
- **Dashboard**: Visão geral dos alunos matriculados, matérias disponíveis e outras informações úteis.

### **Professores**
- **Alterar e Salvar Notas**: Professores podem acessar suas turmas e editar as notas dos alunos, bem como salvar essas alterações.
- **Gerenciar Faltas**: Professores podem registrar faltas para cada aluno em suas matérias.
- **Dashboard**: Interface com visão geral de suas turmas, matérias e funcionalidades relacionadas à edição de notas e faltas.

### **Alunos**
- **Acessar Informações Pessoais**: Alunos podem acessar seus dados cadastrais, notas, faltas e matérias em que estão matriculados.
- **Alterar Senha**: A senha padrão é gerada a partir da data de nascimento e pode ser alterada no primeiro acesso.

## Estrutura do Projeto

### **Backend**
O back-end foi desenvolvido usando **Spring Boot**, **JPA/Hibernate** e **PostgreSQL** como banco de dados. O projeto segue uma arquitetura em camadas, com as seguintes divisões:

- **Controllers**: Responsáveis por lidar com as requisições HTTP e interagir com os serviços.
- **Services**: Contêm a lógica de negócio.
- **Repositories**: Interface para comunicação com o banco de dados via JPA.
- **DTOs**: Usados para transferência de dados entre camadas e para controlar os dados expostos nas respostas da API.

### **Frontend**
O front-end foi desenvolvido utilizando **HTML5**, **Bootstrap** e **Thymeleaf** para renderização dinâmica de páginas. A interface é simples, mas funcional, com modais para exibir detalhes como matérias, notas e faltas.

## Tecnologias Utilizadas

### **Backend**
- **Java 22**
- **Spring Boot**
- **JPA/Hibernate**
- **Spring Web**
- **Spring DevTools**
- **Spring Security**
- **PostgreSQL**
- **Lombok** para redução de boilerplate
- **ModelMapper** para conversão entre entidades e DTOs
- **JWT** para autenticação de usuários

### **Frontend**
- **HTML5**
- **Bootstrap 5**
- **Thymeleaf**

## Organização do Código

- **dto**: Contém as classes `DTO` usados para salvar os dados no banco.
- **controllers**: Lida com as rotas da API REST e as páginas Thymeleaf.
- **services**: Camada que contém a lógica do sistema e interage com os repositórios.
- **repositories**: Contém as interfaces que comunicam com o banco de dados através de JPA.

## Funcionalidades Finais
- **CRUD** completo para Alunos, Professores e Matérias.
- **Autenticação com Spring Security** para controle de acesso ao sistema.
- **Interface Simples e Funcional** com suporte para **modais**, facilitando a visualização de informações sem a necessidade de múltiplos redirecionamentos.

## Futuras Melhorias
- Implementação de relatórios para acompanhar o desempenho dos alunos e professores.
- Melhorias na interface com um dashboard mais interativo.
