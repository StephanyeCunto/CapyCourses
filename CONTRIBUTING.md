# Guia de Contribuição

Obrigado por considerar contribuir para o **CapyCourses**! Este guia tem como objetivo facilitar o processo de contribuição e garantir que todos sigam as mesmas diretrizes. Por favor, leia atentamente antes de enviar qualquer contribuição.

---

## Como Contribuir

### 1. Reportando Problemas (Issues)
Se você encontrar um bug, tiver uma sugestão ou quiser solicitar uma nova funcionalidade, siga estas etapas:
1. Verifique se o problema já foi reportado na [seção de issues](https://github.com/StephanyeCunto/CapyCourses/issues).
2. Se não foi reportado, crie um novo **issue**.
3. Forneça um título claro e uma descrição detalhada do problema ou sugestão.
4. Inclua exemplos de código, screenshots ou links relevantes, se aplicável.

### 2. Enviando Pull Requests (PRs)
Se você deseja contribuir com código, siga estas etapas:
1. **Fork** o repositório e crie um branch a partir da branch `main`.
   ```bash
   git checkout -b minha-feature

1. Faça suas alterações no código, seguindo as [boas práticas de código](#boas-práticas-de-código) descritas abaixo.
2. Teste suas alterações localmente para garantir que tudo funcione corretamente.
3. Faça commit das suas alterações com mensagens claras e descritivas. Use o padrão:
   ```bash
   git commit -m "tipo(escopo): descrição breve"
   ```
   Exemplo:
   ```bash
   git commit -m "feat(autenticação): adiciona login com Google"

      Onde:
   - **tipo**: Descreve a natureza da alteração. Tipos comuns incluem:
     - `feat`: Nova funcionalidade.
     - `fix`: Correção de bugs.
     - `docs`: Alterações na documentação.
     - `style`: Mudanças de formatação (espaços, vírgulas, etc.).
     - `refactor`: Refatoração de código sem adicionar funcionalidades.
     - `test`: Adição ou alteração de testes.
     - `chore`: Tarefas de manutenção (atualizações de dependências, configurações, etc.).
   - **escopo**: Indica a parte do projeto afetada (ex.: `autenticação`, `ui`, `api`).
   - **descrição breve**: Uma descrição clara e concisa das alterações.

4. Envie as alterações para o seu repositório forkado:
   ```bash
   git push origin minha-feature

5. Abra um **Pull Request (PR)** no repositório original:
   - Acesse o repositório forkado no GitHub.
   - Clique em **Compare & Pull Request**.
   - Descreva suas alterações de forma clara e detalhada no PR. Inclua:
     - O problema ou funcionalidade que está sendo resolvida/implementada.
     - Uma descrição das mudanças realizadas.
     - Capturas de tela ou exemplos, se aplicável.
     - Referências a issues relacionadas (ex.: "Resolve #123").
6. Aguarde a revisão do PR. Os mantenedores podem solicitar alterações ou aprovar a contribuição.

---

## Boas Práticas de Código

Para manter a qualidade do código, siga estas diretrizes:
1. **Comentários**: Adicione comentários explicativos sempre que necessário.
2. **Formatação**: Siga o estilo de código já utilizado no projeto.
3. **Testes**: Adicione testes para novas funcionalidades ou correções de bugs.
4. **Commits**: Escreva mensagens de commit claras e descritivas. Use o padrão:
 ```bash
  git commit -m "Sprint 00 - Descrição da alteração realizada

```
---
## Ambiente de Desenvolvimento

Para configurar o ambiente de desenvolvimento localmente, siga estas etapas:

1. Clone o repositório:
   ```bash
   git clone https://github.com/StephanyeCunto/CapyCourses.git

2. Instale as dependências:
   ```bash
   npm install  # ou yarn install, dependendo do projeto
   
3. Execute o projeto localmente:
   ```bash
   npm start  # ou yarn start

## Código de Conduta

Ao contribuir para este projeto, você concorda em seguir nosso [Código de Conduta](CODE_OF_CONDUCT.md). Respeite todos os contribuidores e mantenha um ambiente inclusivo e colaborativo. Comportamentos inadequados, como assédio, linguagem ofensiva ou desrespeito, não serão tolerados.

Se você testemunhar ou sofrer qualquer comportamento inadequado, entre em contato com os mantenedores do projeto via e-mail: **stephanyecristine6@gmail.com**.

---

## Dúvidas?

Se você tiver dúvidas ou precisar de ajuda, sinta-se à vontade para:
- Abrir um **issue** no repositório.
- Entrar em contato via e-mail: **stephanyecristine6@gmail.com**.

---

## Agradecimentos

Agradecemos a todos os contribuidores que ajudam a melhorar este projeto. Sua colaboração é muito valiosa! ❤️

### O que foi ajustado:
1. Adicionei uma explicação detalhada sobre como configurar o ambiente de desenvolvimento.
2. Mantive a formatação clara e organizada para facilitar a leitura.
3. Incluí exemplos práticos de comandos `git` para ajudar os contribuidores.

Se precisar de mais ajustes ou tiver outras solicitações, é só avisar! 😊




