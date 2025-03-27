# Board-De-Tarefas-Java 🚀

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Board-De-Tarefas-Java é uma aplicação Java para gerenciamento de boards e cards. Este projeto foi desenvolvido durante o evento **Decola Tech 2025 Avanade** e, posteriormente, refatorado e aprimorado para refletir o aprendizado obtido no bootcamp, além de incorporar melhorias contínuas adquiridas ao longo do tempo.

## Índice

- [Visão Geral](#vis%C3%A3o-geral)
- [Características](#caracter%C3%ADsticas)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação e Execução](#instala%C3%A7%C3%A3o-e-execu%C3%A7%C3%A3o)
- [Documentação das Alterações](#documenta%C3%A7%C3%A3o-das-altera%C3%A7%C3%B5es)
- [Contribuição](#contribui%C3%A7%C3%A3o)
- [Licença](#licen%C3%A7a)

## Visão Geral 👀

Este projeto visa gerenciar boards e cards, permitindo criar, atualizar, mover e cancelar cards dentro de um board de tarefas. A aplicação incorpora melhorias de código, novas funcionalidades e alterações na estrutura do banco de dados para proporcionar um gerenciamento de tarefas mais eficiente e robusto.

## Características ✨

- Gerenciamento de boards e cards
- Validação de status dos cards antes de alterações
- Movimento entre colunas com verificação de estado
- Cancelamento de cards com registro do motivo (campo `cancel_reason`)
- Migrations para atualização da estrutura do banco de dados
- Métodos sobrecarregados no DAO para flexibilidade nas operações de atualização

## Tecnologias Utilizadas 💻

- Java 11+
- JDBC
- Banco de Dados MySQL / DBeaver
- Gradle 
- Principais bibliotecas:
  -Java Standard Library: Utilizada para funcionalidades básicas de linguagem e manipulação de dados.
  -JDBC (Java Database Connectivity): Utilizada para conexão e manipulação de banco de dados SQL.
  -Lombok: Utilizada para reduzir o código boilerplate, como getters, setters, construtores, etc.
  -MySQL Connector/J: Driver JDBC para conectar o Java com o banco de dados MySQL.

  

## Instalação e Execução ⚙️

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/silassefas1/Board-De-Tarefas-Java.git


2. Importe o projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse, etc.).

3. Configure as variáveis de ambiente e as configurações de conexão com o banco de dados conforme necessário.

4. Execute as migrations para atualizar a estrutura do banco de dados.

## Documentação das Alterações 📝

A seguir, encontram-se as principais mudanças e melhorias implementadas no projeto, comparando-o com o projeto original da Digital Innovation One.

### Comparação entre os Projetos

Este documento apresenta as diferenças entre o projeto original da Digital Innovation One e o projeto aprimorado "Board-De-Tarefas". A análise inclui alterações em código, novos recursos adicionados, refatorações e melhorias de performance ou organização.

### Alterações em Código

- **CardEntity - verifyStatus**
  - **Alteração:** Implementação do método verifyStatus para validar o status de um card antes de permitir alterações.

- **CardEntity - getCurrentColumn**
  - **Alteração:** Novo método criado para encapsular a lógica de busca da coluna atual do card, evitando duplicação de código e facilitando a manutenção.

- **CardService - cardCancel**
  - **Alteração:** Adição da chamada ao método verifyStatus antes de mover o card para a coluna de cancelamento, garantindo que o card esteja em um estado apropriado.

- **CardService - moveToNextColumn**
  - **Alteração:** Refatoração para incluir a verificação de status do card utilizando o método verifyStatus da classe CardEntity, além de utilizar o método getCurrentColumn para determinar a coluna atual.

- **CardDAO - Métodos de Atualização (Overload)**
  - **Alteração:** Criação de métodos sobrecarregados no DAO para atualizar o card no banco de dados. Cada método permite atualizar diferentes conjuntos de parâmetros (por exemplo, apenas a coluna, ou título e descrição, etc.), oferecendo maior flexibilidade para diferentes cenários de atualização.

- **CardDAO - moveToColumn**
  - **Alteração:** Modificação do método para lidar com a adição do campo cancel_reason ao mover um card para a coluna de cancelamento, assegurando que essa informação seja persistida corretamente.

- **Migration Scripts**
  - **Alteração:** Inclusão de novos scripts de migração para refletir as mudanças na estrutura do banco de dados, como a adição do campo cancel_reason e, possivelmente, ajustes em índices ou relacionamentos para otimização das consultas.

### Novos Recursos Adicionados

- **CardEntity - verifyStatus**
  - **Descrição:** Método que verifica se um card pode ser alterado com base no seu status atual e na coluna em que se encontra.

- **CardStateException**
  - **Descrição:** Nova exceção personalizada para indicar problemas relacionados ao estado do card, proporcionando mensagens de erro mais específicas.

- **cancel_reason**
  - **Descrição:** Novo campo adicionado à estrutura do banco de dados para armazenar o motivo do cancelamento de um card, permitindo um controle mais detalhado dos cancelamentos.

- **Migration Scripts**
  - **Descrição:** Adição de novas migrations para modificar a estrutura do banco de dados, garantindo a compatibilidade com as novas funcionalidades e mantendo a integridade dos dados.

### Refatorações

- **Centralização da Lógica de Validação**
  - **Descrição:** A lógica de validação do estado do card foi centralizada no método verifyStatus na classe CardEntity, promovendo a reutilização do código e facilitando a manutenção.

- **Introdução de Método Auxiliar**
  - **Descrição:** Criação do método getCurrentColumn na classe CardEntity para encapsular a lógica de busca da coluna atual, evitando duplicações e aumentando a clareza do código.

- **Tratamento de Exceções Específicas**
  - **Descrição:** Uso da exceção personalizada CardStateException para gerenciar erros relacionados ao estado dos cards, melhorando a clareza e a rastreabilidade das falhas.

- **Melhoria no DAO e Persistência**
  - **Descrição:** Ajustes nos métodos de atualização do banco de dados (incluindo a criação dos métodos sobrecarregados) para acomodar o novo campo cancel_reason, garantindo que as mudanças no estado do card sejam refletidas corretamente na base de dados.

### Melhorias de Performance ou Organização

- **Manutenção e Reutilização do Código**
  - **Descrição:** Ao centralizar a lógica de validação e a busca pela coluna atual (com verifyStatus e getCurrentColumn), o código se torna mais modular e fácil de manter, reduzindo duplicações e facilitando futuras alterações.

- **Clareza no Fluxo de Execução**
  - **Descrição:** A introdução de verificações explícitas e o tratamento de exceções específicas tornam o fluxo de execução mais previsível, facilitando a identificação e correção de problemas.

- **Evolução na Estrutura do Banco de Dados**
  - **Descrição:** As novas migrations não só adicionam o campo cancel_reason, mas também podem incluir melhorias na organização e performance das consultas (como a criação de índices), permitindo que o sistema suporte melhor o gerenciamento de cards e seus estados.

Essas alterações refletem um esforço para tornar o código mais robusto, modular e fácil de manter, ao mesmo tempo em que introduzem novas funcionalidades que melhoram a gestão dos cards no sistema, aprimoram a estrutura do banco de dados e oferecem maior flexibilidade na atualização dos registros por meio de métodos sobrecarregados.

## Contribuição 🤝

Contribuições são bem-vindas! Se você deseja melhorar este projeto, siga os passos abaixo:

1. Fork o repositório.
2. Crie uma branch com a sua feature: `git checkout -b minha-nova-feature`
3. Faça commit das suas alterações: `git commit -am 'Adiciona nova feature'`
4. Envie para a branch: `git push origin minha-nova-feature`
5. Abra um Pull Request.

## Licença 📄

Este projeto está licenciado sob a MIT License.

Este README foi atualizado para refletir todas as melhorias e alterações implementadas no projeto, evidenciando o esforço de aprendizado e evolução contínua durante o evento Decola Tech 2025 Avanade e o bootcamp.
