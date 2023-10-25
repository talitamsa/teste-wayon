# Documentação do Projeto de Agendamento de Transferências Financeiras

Este projeto foi desenvolvido como parte de um teste técnico e envolve o desenvolvimento de uma aplicação que permite agendar transferências financeiras. A aplicação inclui uma API RESTful desenvolvida em Spring Boot para o backend e um frontend desenvolvido em Angular CLI. Neste README, você encontrará informações sobre as decisões arquiteturais, as versões de linguagem e ferramentas utilizadas, além de instruções para a execução do projeto.

## Decisões Arquiteturais

### Backend (Spring Boot)

O backend foi desenvolvido usando o framework Spring Boot, que é amplamente conhecido por sua eficiência, segurança e facilidade de desenvolvimento. Algumas das principais decisões arquiteturais incluem:

- **API RESTful**: O backend consiste em uma API RESTful que segue boas práticas de design de APIs, como o uso de verbos HTTP apropriados (GET, POST) e endpoints descritivos.

- **Banco de Dados H2 em Memória**: A persistência de dados é feita em um banco de dados H2 em memória, que é fácil de configurar e usar para fins de teste.

- **Validação de Dados**: A API valida todos os dados de entrada, garantindo que apenas transferências válidas sejam agendadas. Isso inclui a validação da conta de origem, conta de destino, valor da transferência e datas.

- **Cálculo de Taxas**: As taxas de transferência são calculadas com base na data de transferência de acordo com as regras especificadas no teste. Se a taxa for igual a zero, o sistema lança um alerta e impede a transferência.

### Frontend (Angular CLI)

O frontend foi desenvolvido usando o Angular CLI, um framework JavaScript amplamente utilizado para o desenvolvimento de aplicativos web. As principais decisões arquiteturais incluem:

- **Single Page Application (SPA)**: O frontend é uma SPA que oferece uma experiência de usuário fluida e responsiva.

- **Separação de Componentes**: O código do frontend é organizado em componentes reutilizáveis, seguindo boas práticas de desenvolvimento.

- **Chamadas à API RESTful**: O frontend faz chamadas à API RESTful do backend para agendar transferências e obter o extrato.

- **Validação de Entrada**: A interface do usuário valida os campos de entrada, fornecendo feedback instantâneo ao usuário.

## Versões de Linguagem e Ferramentas Utilizadas

- Angular CLI: 15.2.6
- Node.js: 18.18.2
- Java: 17
- Spring Boot: 3.1.4
- IDE para Backend: IntelliJ IDEA
- IDE para Frontend: Visual Studio Code

## Instruções para a Execução do Projeto

Para executar o projeto em seu ambiente local, siga estas etapas:

### Backend (Spring Boot)

1. Certifique-se de que o Java (versão 17) esteja instalado em sua máquina.
2. Abra o projeto backend em sua IDE preferida (por exemplo, IntelliJ IDEA).
3. Execute o projeto Spring Boot.
4. O backend estará em execução em `http://localhost:1515`.

### Frontend (Angular CLI)

1. Certifique-se de que o Node.js (versão 18.18.2) esteja instalado em sua máquina.
2. Abra o terminal e navegue até a pasta do projeto frontend.
3. Instale as dependências do projeto executando o seguinte comando:
   ```
   npm install
   ```
4. Inicie o servidor de desenvolvimento com o seguinte comando:
   ```
   npm run start
   ```
5. O frontend estará em execução em `http://localhost:4200`.

Acesse o frontend em seu navegador e comece a usar o aplicativo para agendar transferências e visualizar o extrato.

Este projeto também está disponível no meu [repositório no GitHub](https://github.com/talitamsa/teste-wayon), onde você pode acompanhar as etapas do desenvolvimento através dos commits.

Espero que tenha uma boa experiência com a aplicação e não hesite em entrar em contato caso tenha alguma dúvida!
