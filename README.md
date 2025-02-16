# 🦫 CapyCourses - A Jornada do Saber Começa Aqui! 📚

> Transformando o aprendizado em uma experiência extraordinária com tecnologia e inovação.

[![CI Status](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/StephanyeCunto/CapyCourses/actions/workflows/ci-cd.yml)
[![Java](https://img.shields.io/badge/Java-17%2B-brightgreen)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-19%2B-blue)](https://openjfx.io/)
[![MIT](https://img.shields.io/badge/License-MIT-green)](LICENSE)
[![wakatime](https://wakatime.com/badge/user/5a343522-23db-45ae-b20b-54655c392390/project/777997fa-1d4e-4bf3-acd5-5b2f3fbbf946.svg)](https://wakatime.com/badge/user/5a343522-23db-45ae-b20b-54655c392390/project/777997fa-1d4e-4bf3-acd5-5b2f3fbbf946)
[![wakatime](https://wakatime.com/badge/user/cfd3175d-aa54-44f0-b0c6-befacccd6793/project/b49cdaa5-fd26-4250-b17e-2c68c5875e52.svg)](https://wakatime.com/badge/user/cfd3175d-aa54-44f0-b0c6-befacccd6793/project/b49cdaa5-fd26-4250-b17e-2c68c5875e52)

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
- [JavaFX](#-estilo-e-design-com-javafx)
- [PMD](#-análise-estática-com-pmd)
- [JavaDoc](#-documentação-com-javadoc)
- [SQLite](#-sqlite)
- [Diagrama Entidade Relacionamento](#-diagrama-entidade-relacionamento-(er))
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
| ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white) | 17+ | Linguagem principal do projeto, escolhida por sua portabilidade e robustez. |
| ![JavaFX](https://img.shields.io/badge/JavaFX-4B4B77?style=for-the-badge&logo=java&logoColor=white) | 23+ | Framework para interfaces gráficas, selecionado por sua integração nativa com Java e suporte a temas modernos. |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white) | 3.6+ | Ferramenta de automação de build e gerenciamento de dependências para projetos Java. |

### Dependências Principais

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![Lombok](https://img.shields.io/badge/Lombok-BC4520?style=for-the-badge&logo=lombok&logoColor=white) | 1.18.24 | Reduz o boilerplate de código com anotações para geração automática de getters, setters, construtores, entre outros. |
| ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white) | 5.6.15| Framework de persistência de dados, permitindo o mapeamento objeto-relacional entre o Java e o banco de dados. |
| ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white) | 5.0+ | Framework de testes unitários, utilizado para garantir a qualidade do código por meio de testes automatizados. |
| ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white) | 3.36.0.3 | Banco de dados leve e eficiente, ideal para persistência local em aplicações desktop como o CapyCourses. |

### Dependências Adicionais

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![ValidadorFX](https://img.shields.io/badge/ValidadorFX-4B9C3D?style=for-the-badge&logo=java&logoColor=white) | 2.0+ | Biblioteca para validação de formulários, simplificando a implementação de regras de validação no JavaFX. |
| ![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white) | 20230618 | Biblioteca para manipulação de dados no formato JSON, usada para comunicação com APIs ou arquivos de configuração. |
| ![BCrypt](https://img.shields.io/badge/BCrypt-03A87C?style=for-the-badge&logo=java&logoColor=white) | 0.10.2 | Algoritmo de hashing de senhas, garantindo segurança na armazenagem de credenciais. |
| ![JavaMail](https://img.shields.io/badge/JavaMail-FF5A00?style=for-the-badge&logo=java&logoColor=white) | 1.4.7 | Biblioteca para envio de e-mails a partir de uma aplicação Java. |
| ![Apache Commons](https://img.shields.io/badge/Apache%20Commons-FCC624?style=for-the-badge&logo=apache&logoColor=black) | 1.5 | Conjunto de bibliotecas utilitárias para diversas tarefas comuns em Java, como manipulação de strings e arquivos. |

### Dependências para Geração e Manipulação de PDF

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![iText](https://img.shields.io/badge/iText-000000?style=for-the-badge&logo=pdf&logoColor=white) | 5.5.13.3 | Biblioteca para criação e manipulação de arquivos PDF, utilizada para gerar documentos dinâmicos. |
| ![PDFBox](https://img.shields.io/badge/PDFBox-00A9E0?style=for-the-badge&logo=apache&logoColor=white) | 2.0.29 | Biblioteca para ler e escrever arquivos PDF, facilitando a extração e manipulação de conteúdo. |

### Integração e Outros

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![JavaFX Swing](https://img.shields.io/badge/JavaFX%20Swing-FF0000?style=for-the-badge&logo=java&logoColor=white) | 23+ | Integração entre JavaFX e Swing, permitindo que componentes Swing sejam usados em uma aplicação JavaFX. |
| ![ControlsFX](https://img.shields.io/badge/ControlsFX-8E8E8E?style=for-the-badge&logo=java&logoColor=white) | 11.2.0 | Biblioteca de componentes adicionais para JavaFX, oferecendo controles e funcionalidades extras. |
| ![JavaFX Web](https://img.shields.io/badge/JavaFX%20Web-00B5E2?style=for-the-badge&logo=java&logoColor=white) | 23+ | Suporte para exibir conteúdo web diretamente em aplicativos JavaFX. |
| ![Java Activation Framework](https://img.shields.io/badge/Java%20Activation-0066CC?style=for-the-badge&logo=java&logoColor=white) | 1.1.1 | API para ativar e desativar recursos relacionados ao tipo de dados em aplicações Java. |

### Plugins de Build

| Tecnologia | Versão | Descrição |
|:----------:|:------:|:---------:|
| ![PMD](https://img.shields.io/badge/PMD-FFD300?style=for-the-badge&logo=pmd&logoColor=black) | 3.20.0 | Ferramenta de análise estática de código, usada para identificar padrões de código indesejáveis. |
| ![Maven Javadoc](https://img.shields.io/badge/Maven%20Javadoc-FF9800?style=for-the-badge&logo=java&logoColor=white) | 3.5.0 | Plugin do Maven para gerar automaticamente a documentação Javadoc do projeto. |
| ![Maven Compiler](https://img.shields.io/badge/Maven%20Compiler-9E1B32?style=for-the-badge&logo=apache&logoColor=white) | 3.11.0 | Plugin do Maven para compilar o código-fonte Java de acordo com a versão do JDK especificada. |

---

### Ferramentas de Desenvolvimento
- **VS Code**: IDE principal.
- **Git**: Controle de versão.
- **Scene Builder**: Design de interfaces JavaFX.

---

## 🚀 Começando

### 💻 Requisitos do Sistema

Para garantir uma experiência fluida no **CapyCourses**, recomendamos os seguintes requisitos mínimos e ideais:  

#### 📌 Requisitos Mínimos:
- **💾 Processador:** Dual-core 2GHz ou superior  
- **🛑 Memória RAM:** 4GB  
- **📂 Armazenamento:** 500MB de espaço livre  
- **🎨 Gráficos:** Placa integrada compatível com OpenGL 2.0  
- **🌐 Conectividade:** Internet estável (mínimo 2Mbps)  
- **🖥️ Resolução de Tela:** 1280x720 (HD)  
- **🔌 Dependências:**  
  - **Java:** JDK 17 ou superior  
  - **JavaFX:** 19 ou superior  
  - **Maven:** 3.6 ou superior  

#### ⚡ Requisitos Recomendados:
- **🚀 Processador:** Quad-core 2.5GHz ou superior  
- **🔥 Memória RAM:** 8GB ou mais  
- **📁 Armazenamento:** SSD com pelo menos 1GB de espaço livre  
- **🎨 Gráficos:** Placa dedicada compatível com OpenGL 3.0+  
- **⚡ Conectividade:** Internet de alta velocidade (mínimo 10Mbps)  
- **🖥️ Resolução de Tela:** 1920x1080 (Full HD) ou superior  
- **🔌 Dependências:**  
  - **Java:** JDK 21+ recomendado  
  - **JavaFX:** 21+ recomendado  
  - **Maven:** 3.8 ou superior  

#### 🛠️ Plataformas Suportadas:
- **Windows** (10 ou superior)  
- **macOS** (Big Sur 11+ ou superior)  
- **Linux** (Distribuições baseadas em Debian e Arch recomendadas) 

### 🚀 Instalação

### Passo 1: Configuração do Ambiente

```bash
# Verifique a instalação do Java
java -version

# Verifique a instalação do Maven
mvn -version

# Clone o repositório
git clone https://github.com/StephanyeCunto/CapyCourses.git
cd CapyCourses
```

### Passo 2: Variáveis de Ambiente

```bash
# Linux/macOS
export JAVA_HOME=/path/to/java
export PATH=$JAVA_HOME/bin:$PATH

# Windows (PowerShell)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path += ";$env:JAVA_HOME\bin"
```
### Passo 3: Execução

```bash
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
## 🎨 Estilo e Design com JavaFX

O **JavaFX** é um framework para desenvolvimento de interfaces gráficas de usuário (GUIs) em Java, que permite criar interfaces visuais modernas e responsivas. Ele oferece uma ampla gama de controles, gráficos e animações para criar experiências ricas para os usuários.

### Por que Usamos o JavaFX?

- **Interface Rápida e Responsiva**: Com suporte para layouts dinâmicos e animações, o JavaFX permite criar interfaces altamente interativas e com ótima performance.
- **Controles Rich**: JavaFX oferece controles como tabelas, gráficos, botões, sliders e muito mais para melhorar a experiência do usuário.
- **Integração com CSS**: A possibilidade de estilizar a interface com CSS proporciona flexibilidade para criar designs modernos e consistentes.
- **Suporte a Animações**: Permite a criação de animações suaves, como transições de tela, mudanças de opacidade e efeitos visuais para tornar a interface mais envolvente.
- **Plataforma Multiplataforma**: Funciona em diversas plataformas, incluindo Windows, macOS e Linux.

### Como o JavaFX é Integrado ao CapyCourses?

O JavaFX é a base para a interface gráfica do CapyCourses, garantindo que a aplicação seja visualmente atraente, intuitiva e fácil de usar. A interface foi projetada para ser responsiva, adaptando-se bem a diferentes tamanhos de tela e dispositivos, como desktops e tablets.

#### Estrutura do Layout com FXML

A interface gráfica do CapyCourses é estruturada com o uso de **FXML** para definir a aparência da interface e **Controllers** para definir as interações do usuário.

- **FXML**: Define o layout da interface de forma declarativa, permitindo fácil modificação.
- **Controller**: Gerencia os eventos de interação com os elementos da interface, como cliques de botão e entradas de texto.

#### Exemplo de um arquivo FXML:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Button?>

<VBox alignment="CENTER" spacing="10.0">
    <Button text="Iniciar Curso" onAction="#startCourse"/>
</VBox>
```
### Animações com JavaFX

O **JavaFX** permite criar animações fáceis de implementar, que ajudam a melhorar a experiência do usuário. Um exemplo de animação de fade-in para um elemento:

```java
FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), myButton);
fadeIn.setFromValue(0.0);
fadeIn.setToValue(1.0);
fadeIn.play();
```

### Estilização com CSS

O **JavaFX** suporta a estilização de componentes utilizando CSS, permitindo que a interface tenha um design moderno e consistente.

Exemplo de arquivo CSS:

```css
.button {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 10px 20px;
}

.button:hover {
    -fx-background-color: #45a049;
}
```
### Benefícios do JavaFX para o CapyCourses

1. **Para os Usuários**:
   - Interface intuitiva e agradável
   - Facilidade de navegação em dispositivos móveis e desktops
   - Design moderno e responsivo

2. **Para os Desenvolvedores**:
   - Facilidade de criação e manutenção de interfaces gráficas
   - Capacidade de integrar animações e efeitos visuais
   - Suporte a uma ampla gama de controles e layouts

3. **Para o Projeto**:
   - Experiência de usuário rica e atraente
   - Plataforma multiplataforma
   - Design adaptável para diferentes dispositivos


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

## 📚 Documentação com JavaDoc

O **JavaDoc** é uma ferramenta padrão para documentação de código Java que gera documentação HTML a partir de comentários no código-fonte. Esta ferramenta é fundamental para manter uma documentação clara, consistente e acessível do projeto.

### Por que Usamos o JavaDoc?
- **Documentação Padronizada**: Gera documentação em formato HTML consistente e profissional.
- **Integração com IDEs**: Suporte nativo em todas as principais IDEs Java.
- **Documentação Atualizada**: Mantém a documentação junto ao código, facilitando atualizações.
- **Ferramenta Oficial**: Parte do JDK, sendo o padrão da indústria para documentação Java.

### Como o JavaDoc é Integrado ao CapyCourses?
O JavaDoc é executado durante o processo de build através do Maven, gerando documentação completa e atualizada do projeto. A documentação é gerada automaticamente em cada release.

#### Configuração do JavaDoc no Projeto
O JavaDoc está configurado no arquivo `pom.xml` do projeto através do plugin Maven:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <goals>
                <goal>javadoc</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

#### Padrões de Documentação
1. **Documentação de Classes**:
```java
/**
 * Gerencia as operações relacionadas aos cursos na plataforma.
 * 
 * @author Equipe CapyCourses
 * @version 1.0
 * @since 1.0
 */
public class CourseManager {
    // implementação
}
```

2. **Documentação de Métodos**:
```java
/**
 * Cria um novo curso na plataforma.
 * 
 * @param course objeto contendo as informações do curso
 * @return o ID do curso criado
 * @throws IllegalArgumentException se os dados do curso forem inválidos
 * @throws DatabaseException se houver erro ao salvar no banco de dados
 */
public Long createCourse(Course course) {
    // implementação
}
```

#### Como Gerar Documentação
Para gerar a documentação localmente, use o seguinte comando Maven:

```bash
mvn javadoc:javadoc
```

A documentação será gerada em `target/site/apidocs/`.

### Benefícios do JavaDoc para o CapyCourses

1. **Para Desenvolvedores**:
   - Compreensão rápida do código
   - Documentação sempre atualizada
   - Suporte da IDE para visualização
   - Facilitação do processo de onboarding

2. **Para o Projeto**:
   - Documentação profissional
   - Manutenção simplificada
   - Redução de dúvidas técnicas
   - Melhoria da qualidade do código

3. **Para a Comunidade**:
   - Facilidade de contribuição
   - Documentação acessível
   - Curva de aprendizado reduzida

### Diretrizes de Documentação

1. **Elementos Obrigatórios**:
   - Descrição da classe/método
   - Parâmetros (@param)
   - Retorno (@return)
   - Exceções (@throws)
   - Autor (@author)
   - Versão (@version)

2. **Boas Práticas**:
   - Manter documentação atualizada
   - Usar linguagem clara e concisa
   - Incluir exemplos quando necessário
   - Documentar todos os métodos públicos
   - Mencionar comportamentos especiais

3. **Elementos Adicionais**:
   - @see para referências
   - @since para versionamento
   - @deprecated para métodos obsoletos
   - {@code} para trechos de código
   - {@link} para referências a outras classes

### Visualização da Documentação

A documentação gerada pode ser acessada de várias formas:
1. Localmente após geração (`target/site/apidocs/index.html`)
2. Através da IDE (hover sobre classes/métodos)

---

## 🗄️ SQLite

O **SQLite** é um sistema de gerenciamento de banco de dados relacional (RDBMS) que implementa um banco de dados SQL transacional, sem servidor e autocontido. Foi escolhido como a solução de banco de dados para o CapyCourses por suas características únicas e benefícios específicos para nossa aplicação.

### Por que Escolhemos SQLite?

#### Vantagens Principais
- **Sem Servidor**: Opera sem necessidade de um processo de servidor separado
- **Zero Configuração**: Não requer instalação ou configuração
- **Portabilidade**: Todo o banco de dados fica em um único arquivo
- **Confiabilidade**: Transações ACID compliant
- **Performance**: Excelente desempenho para aplicações desktop
- **Lightweight**: Biblioteca compacta (<600KB)

### Como o SQLite é Integrado ao CapyCourses?

#### Configuração no Projeto
O SQLite está configurado através das seguintes dependências no `pom.xml`:

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.36.0.3</version>
</dependency>
<dependency>
    <groupId>com.github.gwenn</groupId>
    <artifactId>sqlite-dialect</artifactId>
    <version>0.1.2</version>
</dependency>
```

#### Configuração do Hibernate
```xml
<property name="hibernate.dialect">org.hibernate.dialect.SQLiteDialect</property>
<property name="hibernate.connection.driver_class">org.sqlite.JDBC</property>
<property name="hibernate.connection.url">jdbc:sqlite:capycourses.db</property>
```

### Características Técnicas

#### 1. Tipos de Dados Suportados
- TEXT
- INTEGER
- REAL
- BLOB
- NULL

#### 2. Limitações e Considerações
- Sem suporte nativo a BOOLEAN (usa INTEGER 0/1)
- Sem tipos DATE/TIME nativos (armazenados como TEXT/INTEGER)
- Bloqueio a nível de arquivo durante escritas
- Limite de tamanho prático: 128TB

### Uso no CapyCourses

#### 1. Estrutura de Dados
```sql
-- Exemplo de tabela de cursos
CREATE TABLE courses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT DEFAULT CURRENT_TIMESTAMP
);
```

#### 2. Boas Práticas Implementadas

1. **Backups Automáticos**:
```java
public void backupDatabase() {
    String dbPath = "capycourses.db";
    String backupPath = "backup_" + System.currentTimeMillis() + ".db";
    Files.copy(Paths.get(dbPath), Paths.get(backupPath));
}
```

2. **Otimização de Performance**:
```java
// Uso de transações para operações em lote
public void batchInsert(List<Course> courses) {
    try (Connection conn = getConnection()) {
        conn.setAutoCommit(false);
        // ... operações em lote
        conn.commit();
    }
}
```

### Benefícios para o CapyCourses

1. **Para Usuários**:
   - Instalação simplificada
   - Operação offline
   - Rápida inicialização
   - Backups simples

2. **Para Desenvolvedores**:
   - Ambiente de desenvolvimento simples
   - Debugging facilitado
   - Testes unitários simplificados
   - Controle de versão do banco

3. **Para o Projeto**:
   - Distribuição simplificada
   - Menor complexidade de infraestrutura
   - Custos reduzidos
   - Manutenção simplificada

### Práticas de Segurança

1. **Proteção do Arquivo**:
```java
private void secureDatabase() {
    File dbFile = new File("capycourses.db");
    // Define permissões restritas
    dbFile.setReadable(true, true);
    dbFile.setWritable(true, true);
}
```

2. **Prevenção de SQL Injection**:
```java
// Uso de PreparedStatement
public Course getCourse(int id) {
    String sql = "SELECT * FROM courses WHERE id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        // ... execução segura
    }
}
```

### Ferramentas de Desenvolvimento

1. **DB Browser for SQLite**:
   - Visualização e edição de dados
   - Execução de queries
   - Análise de estrutura

2. **SQLite Expert**:
   - Administração avançada
   - Otimização de performance
   - Análise de índices

### Monitoramento e Manutenção

1. **Verificação de Integridade**:
```java
public boolean checkDatabaseIntegrity() {
    try (Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery("PRAGMA integrity_check;");
        return "ok".equalsIgnoreCase(rs.getString(1));
    }
}
```

2. **Otimização Regular**:
```java
public void optimizeDatabase() {
    try (Statement stmt = conn.createStatement()) {
        stmt.execute("VACUUM;");
        stmt.execute("ANALYZE;");
    }
}
```

### Migração e Versionamento

1. **Sistema de Migrations**:
   - Controle de versão do schema
   - Atualizações incrementais
   - Rollback seguro

2. **Exemplo de Migration**:
```java
@Migration(version = "1.0")
public void createInitialSchema() {
    executeSQL("CREATE TABLE IF NOT EXISTS courses (...);");
    executeSQL("CREATE INDEX idx_courses_title ON courses(title);");
}
```
---

## 📉 Diagrama Entidade-Relacionamento (ER)

O diagrama ER abaixo representa a estrutura do banco de dados do **CapyCourses**, um sistema de cursos online. Ele define as principais entidades e seus relacionamentos, garantindo a organização dos dados e facilitando futuras expansões.

```mermaid

erDiagram
    CourseSettings ||--o{ courses : configures
    CourseSettings {
        int id PK
        date dateStart
        string durationTotal
        boolean isCertificate
        boolean isDateEnd 
        boolean isGradeMiniun
        string title
        boolean visibility
    }


student_courses ||--o{courses : has
student_courses ||--o{ students : has
student_courses ||--o{questions : has
student_courses ||--o{lessons : has
student_courses ||--o{lesson_progress : has
    courses ||--o{ modules : contains
    courses {
        int id PK
        string categoria
        string description
        string name
        string nivel
        float rating
        string title
        int courseSettings_id FK
        int teacher_id FK
    }

    modules ||--o{ lessons : includes
    modules {
        int id PK
        string description
        string duration
        int moduleNumber
        string title
        int course_id FK
        int questionaire_id FK
    }

    lessons {
        int id PK
        string description
        string duration
        string materials
        int moduleNumber
        int numberOfLesson
        string title
        string video_link
        int module_id FK
    }

    modules ||--o{ questionaire : has
    questionaire {
        int id PK
        string description
        string number
        string score
        string title
        int module_id FK
    }

    questionaire ||--o{ questions : contains
    questions {
        int id PK
        string answers
        string correct_answers
        string evaluation_criteria
        string expected_answer
        string multiple_correct_answers
        string number
        string score
        string text
        string type
        int questionaire_id FK
    }

questions ||--o{ StudentAnswer : has

    users ||--o{ students : has
    users ||--o{teachers : has
    users {
        int id PK
        datetime dateRegister
        string email
        string name
        string password
        string typeUser
    }

    students {
        int id PK
        string areaOfInterest
        string cpf
        datetime dateOfBirth
        string education
        string telephone
        int user_id FK
    }

    teachers {
        int id PK
        string areaOfInterest
        string cpf
        datetime dateOfBirth
        string education
        string telephone
        int user_id FK
    }

    courses ||--o{ students : enrolls
    courses ||--o{ teachers : managed_by

    StudentAnswer {
        int id PK
        string answer
        boolean isCorrect
        float score
        int question_id FK
        int questionaire_id FK
        int student_id FK
    }

users ||--O{ forums : create
forums ||--o{ forum_comments : has
    forums {
        int id PK
        string author
        string category
        int commentsCount
        datetime dateTime
        string description
        int likeCount
        string question
        string title
        int viewCount
    }

    forum_comments {
        int id PK
        datetime commentDate
        string commentText
        string userName
        int forum_id FK
    }

    lesson_progress {
        int id PK
        boolean completed
        datetime completionDate
        int lesson_id FK
        int student_course_id FK
    }

    student_courses {
        int id PK
        int completedLessons
        int completedQuestionaires
        date completionDate
        float grade
        int progress
        date startDate
        string status
        int totalLessons
        int totalQuestionaires
        int course_id FK
        int student_id FK
    }
```
---
## 🔹 **Principais Entidades e Relacionamentos**

### 🎓 **Usuários e Perfis**
1. **users**: Representa os usuários do sistema, armazenando informações como nome, e-mail, senha e tipo de usuário (aluno ou professor).
   - Relacionado com **students** (alunos) e **teachers** (professores), garantindo a separação entre os papéis no sistema.
   - Também está ligado a **forums**, permitindo que usuários criem tópicos no fórum.

2. **students**: Entidade específica para alunos, incluindo CPF, telefone, nível de educação e área de interesse.
   - Ligado a **student_courses**, indicando a matrícula em cursos.

3. **teachers**: Similar a **students**, mas voltado para professores, armazenando suas especializações e informações acadêmicas.
   - Relacionado a **courses**, indicando quais cursos são gerenciados por quais professores.

---

### 📚 **Cursos e Módulos**
4. **courses**: Representa os cursos disponíveis na plataforma, com informações como nome, descrição, categoria, nível e avaliação média.
   - Ligado a **modules** (módulos), estruturando o curso em partes menores.
   - Relacionado a **students**, indicando quais alunos estão matriculados.
   - Associado a **CourseSettings**, definindo configurações como emissão de certificados e requisitos para conclusão.

5. **modules**: Cada curso pode conter vários módulos, e cada módulo tem um título, descrição e duração.
   - Relacionado a **lessons** (aulas) e **questionaire** (questionários), organizando o conteúdo de aprendizado.

---

### 🎥 **Aulas e Progresso**
6. **lessons**: Contém as aulas de cada módulo, com título, duração, descrição, materiais complementares e link para o vídeo.
   - Associado a **lesson_progress**, que monitora quais aulas um aluno já completou.

7. **lesson_progress**: Registra o progresso de um aluno dentro de um curso, indicando se ele completou uma aula específica.

---

### 📝 **Avaliações e Questionários**
8. **questionaire**: Representa os questionários dentro de um módulo, contendo perguntas e critérios de avaliação.
   - Ligado a **questions** (questões), estruturando as perguntas de cada avaliação.

9. **questions**: Contém as perguntas dentro de um questionário, incluindo respostas corretas e critérios de avaliação.
   - Relacionado a **StudentAnswer**, registrando as respostas enviadas pelos alunos.

10. **StudentAnswer**: Armazena as respostas enviadas pelos alunos para cada questão, registrando se a resposta está correta e a pontuação obtida.

---

### 📊 **Gerenciamento de Cursos**
11. **student_courses**: Representa a relação entre alunos e cursos, armazenando progresso, notas e status de conclusão.
   - Ligado a **courses**, associando alunos a cursos.
   - Relacionado a **lesson_progress** e **questions**, monitorando o progresso acadêmico.

12. **CourseSettings**: Define as configurações gerais dos cursos, como a possibilidade de obter certificados, duração total e visibilidade.

---

### 💬 **Fórum e Interação**
13. **forums**: Permite a criação de tópicos dentro do fórum por parte dos usuários, promovendo discussões sobre cursos e temas relacionados.
   - Relacionado a **forum_comments**, armazenando os comentários dentro de cada discussão.

14. **forum_comments**: Contém os comentários feitos dentro de cada fórum, armazenando o autor, data e conteúdo da mensagem.


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
| Interface responsiva   | Concluído    | Out/2024       |
| Integração com banco   | Em andamento | Dez/2024       |
| Sistema de permissões  | Planejado    | Jan/2025       |
| API REST               | Planejado    | Mar/2025       |

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
| ValidadorFX       | MIT           |
| BCrypt            | BSD 2-Clause  |
| JSON              | MIT           |
| JavaMail          | CDDL 1.1      |
| Apache Commons    | Apache 2.0    |
| iText             | AGPL v3       |
| PDFBox            | Apache 2.0    |
| JavaFX Swing      | GPL v2        |
| ControlsFX        | MPL 2.0       |
| Java Activation   | CDDL 1.1      |
| PMD               | BSD 3-Clause  |
| Maven Javadoc     | Apache 2.0    |
| Maven Compiler    | Apache 2.0    |

---

### Política de Uso
- O projeto é distribuído sob a licença MIT.
- Você pode usar, modificar e distribuir o código, desde que inclua a licença original.
---

## 💙 Agradecimentos  

> "Nenhum grande projeto é construído sozinho. Cada linha de código, cada ideia compartilhada e cada desafio superado são peças fundamentais dessa jornada." ✨  

### 🌟 Contribuidores Incríveis  

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/StephanyeCunto">
        <img src="https://github.com/StephanyeCunto.png" width="100px;" alt="Stephanye Cunto"/>
        <br />
        <b>Stephanye Cunto</b>
      </a>
      <br />
      💡 UI/UX | Frontend
    </td>
    <td align="center">
      <a href="https://github.com/juliobossigia">
        <img src="https://github.com/juliobossigia.png" width="100px;" alt="Julio Bossigia"/>
        <br />
        <b>Julio Bossigia</b>
      </a>
      <br />
      🔧 Backend | Infra
    </td>
  </tr>
</table>

### 🦫 CapyCourses não seria o mesmo sem vocês!  
Obrigado por cada linha de código, cada ajuste e cada inovação. 🚀💙  

### Reconhecimentos Especiais
- Agradecemos a todos que contribuíram com feedback, código e ideias para o projeto.

---
## 💙 Agradecimentos

> "Nenhum grande projeto é construído sozinho. Cada linha de código, cada ideia compartilhada e cada desafio superado são peças fundamentais dessa jornada." ✨

---

<p align="center">
  <strong>CapyCourses</strong> - Desenvolvido com 🧡 pela equipe CapyCourses<br>
  <sub>Transformando a educação, uma capivara de cada vez 🦫</sub>
</p>
