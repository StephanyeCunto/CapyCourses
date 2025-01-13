# 🦫 CapyCourses - A Jornada do Saber Começa Aqui! 📚

> Transformando o aprendizado em uma experiência extraordinária com tecnologia e inovação.

[![CI Status](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml)
[![Java](https://img.shields.io/badge/Java-17%2B-brightgreen)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-19%2B-blue)](https://openjfx.io/)
[![MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![Wakatime](https://wakatime.com/badge/github/StephanyeCunto/CapyCourses.svg)](https://wakatime.com/badge/github/StephanyeCunto/CapyCourses)

---

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Começando](#-começando)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Metodologia](#-metodologia)
- [Interface](#-interface)
- [Contribuição](#-contribuição)
- [Roadmap](#-roadmap)
- [Licença](#-licença)
- [FAQ](#-faq)
- [Contribuições Futuras](#-contribuições-futuras)
- [Segurança](#-segurança)
- [Comunicação](#-comunicação)
- [Feedback](#-feedback)
- [Roadmap Detalhado](#-roadmap-detalhado)
- [Licenciamento](#-licenciamento)
- [Agradecimentos](#-agradecimentos)
---

## 🎯 Sobre o Projeto

O **CapyCourses** é uma plataforma de ensino online desenvolvida em JavaFX, projetada para oferecer uma experiência de aprendizado moderna, intuitiva e envolvente. Nossa missão é democratizar o conhecimento através de uma interface amigável e recursos inovadores.

### 🦫 Por que CapyCourses?

| Característica          | Descrição                                                                 |
|-------------------------|---------------------------------------------------------------------------|
| 🎨 *Design Moderno*      | Interface elegante com suporte a temas claro/escuro.                      |
| 📱 *Responsividade*      | Adaptável a diferentes dispositivos e resoluções.                         |
| 🔒 *Segurança*           | Sistema robusto de autenticação e permissões.                             |
| 🚀 *Performance*         | Otimizado para máxima eficiência.                                         |
| 🌟 *Experiência*         | Foco na usabilidade e satisfação do usuário.                              |
| 🦫 *Comunidade*          | Ambiente colaborativo com fóruns e interação entre alunos e professores.  |

---

## ✨ Funcionalidades

### 🎓 Gerenciamento de Cursos
- Criação e edição de cursos.
- Organização por categorias e níveis de dificuldade.
- Sistema de avaliação e feedback.
- Acompanhamento de progresso do aluno.
- Emissão de certificados personalizados.

### 👥 Perfis de Usuário
- **Aluno**: Acesso a cursos, materiais e fóruns.
- **Professor**: Criação e gerenciamento de cursos.
- **Administrador**: Controle total da plataforma.

### 📚 Recursos de Aprendizado
- Biblioteca digital com materiais complementares.
- Fórum de discussão para interação entre alunos e professores.
- Vídeos, quizzes e materiais interativos.
- Certificados personalizados ao concluir cursos.

### ⚙️ Personalização
- Modo escuro/claro.
- Configurações de perfil personalizadas.
- Preferências salvas.
- Notificações personalizáveis.

### 🛠️ Ferramentas Avançadas
- Dashboard de desempenho com métricas detalhadas.
- Relatórios de progresso e desempenho.

---

## 🛠️ Tecnologias 

### Core

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white) | 17+ | Backend e lógica principal |
| ![JavaFX](https://img.shields.io/badge/JavaFX-4B4B77?style=for-the-badge&logo=java&logoColor=white) | 19+ | Interface gráfica |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white) | 3.6+ | Gestão de dependências |

### Dependências

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![Lombok](https://img.shields.io/badge/Lombok-BC4520?style=for-the-badge&logo=lombok&logoColor=white) | 1.18+ | Redução de boilerplate |
| ![ValidadorFX](https://img.shields.io/badge/ValidadorFX-4B9C3D?style=for-the-badge&logo=java&logoColor=white) | 2.0+ | Validação de formulários |
| ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white) | 6.0+ | Persistência de dados |
| ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white) | 5.0+ | Testes unitários |

---

### Ferramentas de Desenvolvimento
- **VS Code**: IDE principal.
- **Git**: Controle de versão.
- **Scene Builder**: Design de interfaces JavaFX.

---

## 🚀 Começando

### Pré-requisitos
Certifique-se de ter instalado:
- Java 17+
- JavaFX 19+
- Maven 3.6+

```bash
# Verifique as versões instaladas
java -version
mvn -version
```

## Instalação

```bash
# Clone o repositório
git clone https://github.com/StephanyeCunto/CapyCourses.git

# Entre no diretório
cd CapyCourses

# Instale as dependências
mvn clean install

# Execute o projeto
mvn javafx:run
```
---
## 🔧 Integração Contínua (CI)

O projeto utiliza **GitHub Actions** para garantir a qualidade do código. A cada push ou pull request, o pipeline de CI é executado para:

- Compilar o projeto.
- Executar testes automatizados.
- Verificar a qualidade do código.

[![CI Status](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml)

### Como Funciona?
1. **Compilação**: O projeto é compilado usando o Maven (`mvn clean package`).
2. **Testes**: Os testes automatizados são executados (`mvn test`).
3. Análise Estática: O código é verificado com o PMD (`mvn pmd:check`).
4. Verificação de Dependências: As dependências são analisadas com OWASP Dependency-Check (`mvn org.owasp:dependency-check-maven:check`).
5. **Status**: O badge acima mostra o status atual do CI (✅ passando ou ❌ falhando).

### Como Verificar?
- Clique no badge **CI Status** para ver os detalhes da execução do pipeline na aba **Actions** do GitHub.

---

## 🗂️ Estrutura do Projeto
```bash
src/
├── com/
│   ├── login_cadastro/     # Autenticação
│   ├── paginaInicial/      # Páginas principais
│   ├── model/              # Entidades
│   └── controller/         # Controladores
├── resources/
│   ├── views/             # Arquivos FXML
│   ├── css/               # Estilos
│   └── images/            # Recursos visuais
```
## 📈 Metodologia

Utilizamos **Scrum** como framework ágil, com:

- 🔄 Sprints de 2 semanas
- 📊 Planejamento iterativo e priorização de tarefas.
- 👥 Daily meetings para acompanhamento do progresso.
- 📝 Retrospectivas para melhoria contínua.

### Padrões de Projeto

- 🏗️ **MVC (Model-View-Controller)**: Separação de responsabilidades entre modelos, visualizações e controladores.
- 🔒 **Singleton**: Garantia de que apenas uma instância de uma classe seja criada.
- 🗃️ **DAO (Data Access Object)**: Abstração de acesso a dados para persistência.
- 🧩 **Factory**: Criação de objetos de forma flexível e desacoplada.

### Ferramentas de Gestão

- **Trello**: Para gerenciamento de tarefas e acompanhamento de sprints.
- **GitHub Projects**: Para organização de issues e pull requests.
- **Discord**: Para comunicação diária e reuniões da equipe.

---

## 🖼️ Interface

### Tela de Login (Modo Escuro)
![Login Dark](https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaLoginDark.png)

### Tela Inicial Aluno (Modo Claro)
![Cadastro Light](https://raw.githubusercontent.com/StephanyeCunto/CapyCourses/main/img/telaInicialLigth.png)

*Mais screenshots disponíveis na [galeria completa](https://github.com/StephanyeCunto/CapyCourses/tree/main/img)*

---

## 👥 Contribuição

1. Fork o projeto.
2. Crie sua branch (`git checkout -b feature/AmazingFeature`).
3. Commit suas mudanças (`git commit -m 'Add: nova funcionalidade'`).
4. Push para a branch (`git push origin feature/AmazingFeature`).
5. Abra um Pull Request.

### Diretrizes de Contribuição

- 📝 **Documentação**: Mantenha a documentação atualizada.
- 🧪 **Testes**: Adicione testes unitários e de integração para novas funcionalidades.
- 🧹 **Clean Code**: Siga as boas práticas de codificação.
- 🔄 **Revisão**: Submeta seu código para revisão antes de merge.

### Boas Práticas

- Use nomes descritivos para branches, commits e variáveis.
- Documente novas funcionalidades no README ou em arquivos específicos.
- Respeite o padrão de código definido no projeto.

---

## 🗺 Roadmap

### Em Andamento
- [x] Interface responsiva.
- [ ] Integração com banco de dados.
- [ ] Sistema de permissões avançado.

### Próximas Etapas
- [ ] Módulo de análise de desempenho.
- [ ] API REST para integração com outras plataformas.
- [ ] Suporte a múltiplos idiomas.

### Futuro
- [ ] Integração com serviços de pagamento.
- [ ] Aplicativo móvel (Android/iOS).
- [ ] Inteligência Artificial para recomendações personalizadas.

---

## 📄 Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

---

## ❓ FAQ

### Perguntas Frequentes

#### 1. Como reportar um bug?
- Abra uma **issue** no GitHub com a descrição do problema.
- Inclua:
  - Passos para reproduzir o bug.
  - Capturas de tela ou vídeos, se aplicável.
  - Informações sobre o ambiente (SO, versão do Java, etc.).

#### 2. Como sugerir uma nova funcionalidade?
- Crie uma **issue** com a label `enhancement`.
- Descreva sua ideia de forma clara e detalhada.
- Explique como a funcionalidade beneficiaria os usuários.

#### 3. Como configurar o ambiente de desenvolvimento?
- Siga o guia na seção [Configuração do Ambiente](#-configuração-do-ambiente).
- Se encontrar problemas, consulte a [documentação oficial do JavaFX](https://openjfx.io/).

#### 4. Onde posso encontrar mais screenshots da interface?
- Acesse a [Galeria de Screenshots](#-galeria-de-screenshots) no README.
- Ou visite o diretório `img/` no repositório.

#### 5. Como contribuir para o projeto?
- Siga as diretrizes na seção [Contribuição](#-contribuição).
- Leia o [Código de Conduta](CODE_OF_CONDUCT.md) antes de começar.

#### 6. Onde posso obter suporte?
- Entre em contato conosco via:
  - **Discord**: [Link do servidor](#)
  - **E-mail**: support@capycourses.com

---

## 🔮 Contribuições Futuras

### Lista de Desejos
- [ ] Suporte a cursos offline.
- [ ] Integração com Google Classroom.
- [ ] Gamificação (badges e rankings).
- [ ] Suporte a múltiplos idiomas.

### Como Contribuir para Novas Funcionalidades
1. Verifique o [Roadmap](#-roadmap) para ver se a funcionalidade já está planejada.
2. Crie uma issue com a label `enhancement` para discutir a ideia.
3. Após aprovação, siga as diretrizes de contribuição.

---

## 🔒 Segurança

### Política de Segurança
- **Reporte Vulnerabilidades**: Envie um e-mail para security@capycourses.com.
- **Boas Práticas**:
  - Nunca compartilhe credenciais de acesso.
  - Use senhas fortes e autenticação de dois fatores (2FA).
  - Mantenha suas dependências atualizadas.

### Auditoria de Código
- Realizamos auditorias periódicas para garantir a segurança do código.
- Utilizamos ferramentas como [PMD](https://pmd.github.io) para análise estática.

---

## 🔍 Análise Estática com PMD

O **PMD** é uma ferramenta de análise estática de código que ajuda a identificar problemas comuns, más práticas e potenciais vulnerabilidades no código-fonte. Ele é amplamente utilizado para garantir que o código siga boas práticas de desenvolvimento e mantenha um alto padrão de qualidade.

### Por que Usamos o PMD?
- **Identificação de Code Smells**: Detecta padrões de código que podem indicar más práticas ou complexidade desnecessária, como métodos muito longos, classes excessivamente grandes ou duplicação de código.
- **Prevenção de Bugs**: Identifica problemas antes que eles se tornem bugs em produção.
- **Padronização**: Garante que o código siga boas práticas e padrões consistentes.
- **Gratuito e Open-Source**: Totalmente gratuito e com suporte da comunidade.

### Como o PMD é Integrado ao CapyCourses?
O PMD é executado automaticamente em cada push ou pull request através do **GitHub Actions**. Ele verifica o código em busca de problemas e gera relatórios que são usados para corrigir e melhorar o código.

#### Configuração do PMD no Projeto
O PMD está configurado no arquivo `pom.xml` do projeto, utilizando o plugin Maven:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.20.0</version>
    <configuration>
        <rulesets>
            <ruleset>category/java/bestpractices.xml</ruleset>
            <ruleset>category/java/errorprone.xml</ruleset>
            <ruleset>category/java/design.xml</ruleset>
        </rulesets>
        <failOnViolation>false</failOnViolation>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
#### Regras Utilizadas
- **Best Practices**: Verifica boas práticas de codificação.
- **Error Prone**: Detecta erros comuns que podem levar a bugs.
- **Design**: Identifica problemas de design, como classes com muitas responsabilidades.

#### Como Executar o PMD Localmente
Para executar o PMD localmente e verificar o código, use o seguinte comando Maven:

```bash
mvn pmd:check
```
Os relatórios serão gerados em `target/pmd.xml` e `target/pmd.html`.


### Benefícios do PMD para o CapyCourses
- **Melhoria da Qualidade do Código**: Identifica e corrige problemas antes que se tornem bugs.
- **Padronização**: Garante que o código siga boas práticas e padrões consistentes.
- **Prevenção de Vulnerabilidades**: Detecta potenciais problemas de segurança.

---

## 📢 Comunicação

### Canais de Comunicação
- **Discord**: [Link do servidor](#)
  - Canais disponíveis:
    - `#geral`: Discussões gerais.
    - `#suporte`: Ajuda com problemas técnicos.
    - `#sugestões`: Ideias para novas funcionalidades.
- **Fórum**: [Link do fórum](#)
  - Discussões técnicas e compartilhamento de conhecimento.

### Reuniões da Comunidade
- **Quando**: Toda quarta-feira às 20h (GMT-3).
- **Onde**: Canal `#reuniões` no Discord.
- **Agenda**:
  - Apresentação de novas funcionalidades.
  - Discussão de issues abertas.
  - Planejamento de sprints.

---

## 📝 Feedback

### Formulário de Feedback
- [Link do formulário](#)
  - Nos ajude a melhorar! Deixe seu feedback sobre:
    - Usabilidade.
    - Funcionalidades.
    - Problemas encontrados.

### Agradecimentos
- Seu feedback é valioso para nós! Contribuidores que forneceram feedback significativo serão reconhecidos na seção [Agradecimentos](#-agradecimentos).

---

## 🗺 Roadmap Detalhado

### Cronograma
| Etapa                  | Status       | Prazo Estimado |
|------------------------|--------------|----------------|
| Interface responsiva   | Concluído    | Out/2023       |
| Integração com banco   | Em andamento | Dez/2023       |
| Sistema de permissões  | Planejado    | Jan/2024       |
| API REST               | Planejado    | Mar/2024       |

### Prioridades
1. Finalizar integração com banco de dados.
2. Implementar sistema de permissões avançado.
3. Desenvolver módulo de análise de desempenho.

---

## 📜 Licenciamento

### Licenças de Dependências
| Dependência       | Licença       |
|-------------------|---------------|
| JavaFX            | GPL v2        |
| Lombok            | MIT           |
| Hibernate         | LGPL          |
| JUnit 5           | EPL 2.0       |

### Política de Uso
- O projeto é distribuído sob a licença MIT.
- Você pode usar, modificar e distribuir o código, desde que inclua a licença original.

---

## 🙏 Agradecimentos

### Contribuidores
- [Stephanye Cunto](https://github.com/StephanyeCunto)
- [Julio Bossigia](https://github.com/juliobossigia)

### Reconhecimentos Especiais
- Agradecemos a todos que contribuíram com feedback, código e ideias para o projeto.

---

<p align="center">
  <strong>CapyCourses</strong> - Desenvolvido com 🧡 pela equipe CapyCourses<br>
  <sub>Transformando a educação, uma capivara de cada vez 🦫</sub>
</p>
