# CapyCourse - Plataforma de Cursos Online

## Introdução

O CapyCourse é um sistema web desenvolvido para gerenciar cursos online. Construído utilizando [Java, JPA e MySql ou PostgreSQL], o projeto tem como objetivo facilitar a gestão de cursos e proporcionar uma experiência de aprendizado online completa para alunos e professores.

## Funcionalidades

* **Cadastro:**
    * Cursos: Criação e edição de cursos, incluindo informações como nome, descrição e carga horária.
    * Alunos: Cadastro de alunos com informações pessoais e histórico acadêmico.
    * Professores: Cadastro de professores com suas respectivas áreas de atuação.
* **Gerenciamento de Cursos:**
    * Matrícula de alunos em cursos.
    * Criação de avaliações e atividades.
    * Publicação de materiais de estudo.
* **Fórum de Discussão:**
    * Criação de fóruns e tópicos para discussão entre alunos e professores.
* **Relatórios:**
    * Geração de relatórios sobre o desempenho dos alunos e o progresso dos cursos.

## O Scrum como Método de Processo

Adotamos o Scrum, um framework ágil, para gerenciar o desenvolvimento do CapyCourse. Essa metodologia nos permitiu entregar valor de forma incremental e iterativa, adaptando-nos às mudanças e garantindo a qualidade do produto final.

### Padrões de Projeto

A escolha de padrões de projeto sólidos foi crucial para garantir a manutenibilidade, extensibilidade e escalabilidade do CapyCourse.

* **MVC (Model-View-Controller):**
  *  Separamos as responsabilidades do sistema em três camadas distintas:
      * **Modelo:** Representa os dados do sistema (cursos, alunos, professores).
      * **Views:** Responsável pela interface do usuário, exibindo os dados do modelo.
      * **Controlador:** Recebe as entradas do usuário, atualiza o modelo e escolhe qual vista será exibida.

* **Singleton:**
  * Garantimos que certas classes, como a conexão com o banco de dados, tenham apenas uma instância em todo o sistema, evitando problemas de concorrência e otimizando o uso de recursos.
* **DAO (Data Access Object):**
  *  Abstraímos o acesso aos dados, centralizando a lógica de persistência e facilitando a troca de bancos de dados.

## Instalação e Uso

1. **Clone o repositório:**
   ```bash
   git clone [URL inválido removido]
