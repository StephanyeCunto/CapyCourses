# 🦫 CapyCourses - A Jornada do Saber Começa Aqui! 📚


Bem-vindo ao **CapyCourses**, o seu novo melhor amigo no universo dos cursos online! 🚀 Criado com o coração pulsando **em JavaFX**, nosso sistema tem uma interface descolada, dinâmica e super responsiva, perfeita para você aprender com estilo e facilidade. Vamos nessa jornada de aprendizado, mas sem perder a vibe?

![Java](https://img.shields.io/badge/Java-17%2B-brightgreen)
![JavaFX](https://img.shields.io/badge/JavaFX-19%2B-blue)
![MIT](https://img.shields.io/badge/License-MIT-green)
<a href="https://wakatime.com/badge/github/StephanyeCunto/CapyCourses"><img src="https://wakatime.com/badge/github/StephanyeCunto/CapyCourses.svg" alt="wakatime"></a>
---

## 🌟 Principais Funcionalidades

✨ **CapyCourses** oferece uma variedade de funcionalidades para tornar sua experiência de aprendizado incrível:

- 🎓 **Gerenciamento de Cursos**: Crie, edite e organize cursos como um expert!
- 🌗 **Modo Escuro/Claro**: Porque seu mood merece ser personalizado!
- 📱 **Interface Responsiva**: Aprenda onde quiser, em qualquer dispositivo, sem limites!
- 🔒 **Cadastro de Usuários**: Proteja seus dados com a simplicidade que você merece.
- 💾 **Salvar Configurações**: Suas preferências, salvas e prontas para o próximo login!
- ⚙️ **Sistema de Permissões** (Vem por aí!): Controle total sobre quem vê o quê – perfeito para Administradores, Alunos e Professores.

---

## 🖼️ Screenshots do Sistema

### Tela de Login: Entre com estilo!
<p align="center">
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaLoginDark.png" alt="Tela de Login - Modo Escuro" width="48%" />
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaLoginLigth.png" alt="Tela de Login - Modo Claro" width="48%" />
</p>

### Tela de Cadastro de Curso
<p align="center">
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaCadastroCursoDark.png" alt="Tela de Cadastro Curso - Modo Escuro" width="48%" />
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaCadastroCursoLigth.png" alt="Tela de Cadastro Curso - Modo Claro" width="48%" />
</p>

### Tela Inicial Estudante
<p align="center">
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaInicialDark.png" alt="Tela Inicial Estudante - Modo Escuro" width="48%" />
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaInicialLigth.png" alt="Tela Inicial Estudante - Modo Claro" width="48%" />
</p>

### Tela Inicial Estudante - ver Detalhes
<p align="center">
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/modalVerDetalhesDark.png" alt="Tela Inicial Estudante Ver Detalhes - Modo Escuro" width="48%" />
  <img src="https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/modalVerDetalhesLigth.png" alt="Tela Inicial Estudante Ver Detalhes - Modo Claro" width="48%" />
</p>
---

## 🛠️ Tecnologias Utilizadas

Aqui no CapyCourses, a gente usa o que há de melhor no mercado para garantir uma experiência única:

- [**Java**](https://www.oracle.com/java/): Nosso combustível principal.
- [**JavaFX**](https://openjfx.io/): O que faz a mágica acontecer na tela.
- [**Lombok**](https://projectlombok.org/): Menos código repetido, mais produtividade!
- [**ValidadorFX**](https://validadorfx.com/): Valide seus dados com estilo.

---

## 🚀 Como Rodar o Projeto

Fique tranquilo! Configurar o **CapyCourses** é moleza!
Nada de complicação, aqui é tudo simplificado:

### Pré-requisitos
- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [JavaFX 19+](https://openjfx.io/)
- [Maven](https://maven.apache.org/) ou [Gradle](https://gradle.org/) (opcional)
- Seu editor favorito (como [VS Code](https://code.visualstudio.com/) ou [IntelliJ IDEA](https://www.jetbrains.com/idea/))
  
1. Clone o repositório:
   ```bash
   git clone https://github.com/StephanyeCunto/CapyCourses.git
   cd CapyCourses
   ```

3. Se estiver utilizando **Maven**:
   ```bash
   mvn clean install
   mvn javafx:run
   ```

4. Se estiver utilizando **Gradle**:
   ```bash
   gradle build
   gradle run
   ```

5. Quer rodar sem Maven/Gradle? Sem stress:
   ```bash
   javac -d out src/**/*.java
   ```

6. Execute a aplicação:
   ```bash
   java -cp out Main
   ```
   
---
## 🎉 Próximos Passos?

Estamos só no começo e já temos várias novidades a caminho!

- [x] **Interface Responsiva**  
- [ ] **Banco de Dados Relacional** (MySQL, PostgreSQL ou SQLite)
- [ ] **Sistema de Permissões**  
  - Perfis: Administrador, Aluno e Professor  
  - Controle de acesso mágico
- [ ] **Fórum de Discussão**  
  - O lugar perfeito para perguntas e respostas!
- [ ] **Relatórios de Progresso**  
  - Gráficos para monitorar seu sucesso!
- [ ] **Geração de Certificados**  
  - Certificados com seu nome brilhando no PDF!
- [ ] **Biblioteca de Materiais Didáticos**  
  - Onde os materiais de apoio estarão prontos para você!
    
---

## 🗂️ Estrutura do Projeto

Aqui está o que você vai encontrar dentro da mágica do **CapyCourses**:

### **src/ - O coração do projeto, com a lógica do sistema!**

```text
src/
├── com/
│   ├── login_cadastro/
│   │   ├── PaginaLogin.java        #  A mágica de login e cadastro
│   ├── paginaInicial/
│   │   ├── Main.java               # Onde tudo começa
│   ├── model/
│   │   ├── Curso.java              # Definindo como nossos cursos brilham
│   ├── controller/
│   │   ├── LoginController.java    # Controle para sua jornada de login
│   └── ...
```
### **resources/ - Os arquivos visuais que tornam tudo bonito!**

```text
resources/
├── views/                          # Aqui estão os arquivos FXML das telas
│   ├── login.fxml                  # Tela de login
│   ├── cadastroCurso.fxml          # Tela para adicionar seus cursos
├── css/                            # Onde os estilos acontecem
│   ├── dark/                       # O lado escuro da força
│      └── styleDark.css            # Para quando o modo noturno te chamar
│   ├── light/                      # O lado brilhante
│      └── styleLight.css           # Para brilhar no modo claro
├── images/                         # Porque até a tecnologia merece uma boa imagem
│   ├── logo.png                    # O logo de CapyCourses
└── icons/                          # Ícones fofos para representar o tema
    ├── sun-icon.png                # Para os dias de sol (modo claro)
    └── moon-icon.png               # Para as noites estreladas (modo escuro)
```

### **documents/ - Documentação complementar que descreve o funcionamento do sistema.**

```text
documents/
├── caso de uso Curso Online.mdj   # Descrição dos casos de uso do sistema, com foco nos fluxos do curso online
└── diagrama de classe.mdj         # Diagrama de classe detalhando a arquitetura do sistema 
```

---

## 📖 O Scrum como Método de Processo

Adotamos o **Scrum**, um framework ágil, para gerenciar o desenvolvimento do **CapyCourse**.  
Essa metodologia nos permitiu entregar valor de forma incremental e iterativa, adaptando-nos às mudanças e garantindo a qualidade do produto final.

---

## 📐 Padrões de Projeto
Com um design arrojado e flexível, garantimos a manutenibilidade, extensibilidade e escalabilidade do **CapyCourse**.

### **MVC (Model-View-Controller):**
Separamos as responsabilidades do sistema em três camadas distintas:
- **Model**: Representa os dados do sistema (cursos, alunos, professores).
- **Views**: Responsável pela interface do usuário, exibindo os dados do modelo.
- **Controller**: Recebe as entradas do usuário, atualiza o modelo e escolhe qual vista será exibida.

### **Singleton:**
Garantimos que certas classes, como a conexão com o banco de dados, tenham apenas uma instância em todo o sistema, evitando problemas de concorrência e otimizando o uso de recursos.

### **DAO (Data Access Object):**
Abstraímos o acesso aos dados, centralizando a lógica de persistência e facilitando a troca de bancos de dados.

---
## 🎯 Boas Práticas

Para manter a qualidade do código e tornar nossa capivara feliz, seguimos algumas boas práticas divertidas e eficientes:

### 🎨 Convenções de Código
- Utilizamos o padrão de nomenclatura camelCase (como uma capivara pulando!)
- Classes começam com letra maiúscula 
 - Exemplo: `LoginController`, `PaginaInicial`, `CapyCourse`
- Métodos e variáveis começam com letra minúscula
 - Exemplo: `validarLogin()`, `nomeUsuario`, `cursosDisponiveis`
- Usamos nomes significativos e em português
 - Prefira `cadastrarAluno()` em vez de `cadAluno()`

### 📝 Commits
- Commits devem ser concisos e descritivos (como uma capivara organizada!)
- Use o prefixo: `Sprint 00 - `
- O texto deve descrever claramente o que foi feito
- Exemplos:
 - `Sprint 00 - adiciona sistema de notificações`
 - `Sprint 00 - corrige bug no login dark mode`
 - `Sprint 00 - atualiza documentação do CapyCourses`

### 📋 Padrões de Código
- Seguimos o padrão MVC para organização das classes
- Utilizamos o JavaFX Scene Builder para as interfaces
- Mantemos um código limpo e bem documentado
- Aplicamos os princípios SOLID

> 💡 **Dica da Capivara**: Mantenha seus commits pequenos e focados em uma única alteração. Isso facilita o review e o controle de versão! 🦫

---

## 👩‍💻 Contribuindo

🎉 **Sua ajuda é bem-vinda!** Se você é apaixonado por tecnologia e quer fazer parte do **CapyCourses**, estamos prontos para te receber!

1.  🍴Faça um fork do repositório.
   
2. 🌿Crie um branch para a funcionalidade:

    ```bash
   git checkout -b minha-funcionalidade
3. ✅Commit suas alterações:  
   ```bash
   git commit -m 'Adiciona nova funcionalidade'
   
4. 🔝Faça o push:
   
   ```bash
   git push origin minha-funcionalidade

5. 📤Crie um Pull Request:  
   - Acesse o repositório original no GitHub.  
   - Clique em **Pull Requests**.  
   - Clique em **New Pull Request**.  
   - Selecione o branch do seu fork e compare com o branch principal do repositório original.  
   - Adicione um título e uma descrição explicando suas alterações.  
   - Clique em **Create Pull Request** para enviar sua contribuição.

---

## 📜 Licença

Este projeto está sob a licença MIT, então sinta-se à vontade para usar, modificar e fazer o que quiser. Só não se esqueça de nos dar os créditos! Confira o arquivo LICENSE para mais detalhes.

---

<p align="center">
  <i>Feito com 🧡 pela galera do CapyCourses!</i><br>
  <small>Transformando o aprendizado em uma experiência visualmente extraordinária! 🦫✨</small>
</p>
