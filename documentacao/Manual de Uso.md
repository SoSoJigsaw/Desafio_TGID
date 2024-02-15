# Manual de Uso 📘

## Índice:
- [Preparando o Ambiente](#Preparando-o-Ambiente)
- [Usando o Software](#Usando-o-Software)

## Preparando o Ambiente

### 1. Clonando o Repositório
- Acesse a página do repositório
- Clique no botão "Code" e copie o URL exibido
- No Terminal ou Bash, navegue até o diretório que deseja clonar o repositório
- Execute o comando ```git clone <URL>```
- O Git começará a baixar os arquivos do repositório. Aguarde até que o processo seja concluído.

### 2. Docker e Docker Compose
- Caso não tenha instalado, é necessário instalar o [Docker Desktop](https://www.docker.com/products/docker-desktop/). Para baixar e utilizar o serviço, será necessário criar uma conta.
- Com o Docker Desktop instalado, execute o programa para que o servidor Docker seja inicializado. Será necessário também fazer o login em sua conta Docker
- Dentro do diretório raiz do projeto, onde se encontra o arquivo `docker-compose.yml`, execute diretamente o arquivo (caso a IDE que esteja utilizando tenha compatibilidade com o Docker) ou então abra um terminal no sistema operacional / IDE de sua preferência, e então execute:
  ```docker
   docker-compose up -d --build
  ```
Dessa forma, os containers configurados dentro do arquivo docker compose serão construídos e executados. Aguarde a inicialização de todos os containers para prosseguir para os próximos passos.

### 3. SpringBoot
- <b>Java Development Kit (JDK)</b>: Certifique-se de ter o JDK instalado em seu sistema. O Spring Boot requer o JDK 8 ou superior. Você pode verificar a instalação executando `java -version` no terminal.
- Na IDE de sua escolha, faça a build do projeto Maven, para que todas as dependências do projeto listadas no `pom.xml` possam estar disponível e assim garantir a inicialização bem sucedida do Springboot
- Para uma maior praticidade na execução e também para poder visualizar da melhor forma os logs ou depurar, execute a classe principal do aplicativo com a anotação `@SpringBootApplication`. que no caso é a classe `TgidApplication` apartir da IDE de sua preferência (recomenda-se IntelliJ IDEA ou Eclipse)
- A inicialização da aplicação só será bem sucedida se o servidor do Docker estiver sendo executado. A aplicação depende da execução dos containers do PostgreSQL, ZooKeeper e Kafka, que podem serem executados a partir do arquivo `docker-compose.yml`. Se o servidor Docker estiver em execução, não há necessidade de executar o arquivo do Docker Compose, já que na inicialização da própria aplicação Springboot isso ocorrerá.

### 4. Vue.js
- Caso não tenha o Node instalado, instale primariamente o [Node.js](https://nodejs.org/en/download/current) e o gerenciador de pacotes `npm` (é provável que, com a instalação do node, o npm seja também instalado)
- Dentro do diretório raíz do projeto Vue.js, que no caso é o diretório `frontend`, instale as dependências do projeto:
  ```node
  npm install
  ```
- Instale então o Vue CLI globalmente:
  ```node
  npm install -g @vue/cli
  ```
- Para executar o projeto, utilize:
    ```node
    npm run dev
    ```
Com a execução do projeto vue, o terminal avisará a url para acessar a aplicação. Por padrão, o Vue.js é executado localmente na porta 5173. Portanto, a url fornecida deverá ser `http://localhost:5173/`. Acesse o link, e assim você estará pronto para começar a utilizar a aplicação!

### ATENÇÃO: Para o uso da aplicação, é necessário que tanto o projeto do `SpringBoot` quanto o projeto do `Vue.js` estejam sendo executados ao mesmo tempo. 

## Usando o Software
![Home Software](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/gif-readme.gif)
<br>
A interface da aplicação é bem simples, contendo apenas três seções, cada uma para realizar diferentes funcionalidades. As seções são `Transações`, `Cadastrar` e `Acesse os Dados`.

### 1. Realizar Transações
Acessando a seção `Transações`, você pode optar tanto por realizar um depósito ou então um saque. O agente que realiza os depósitos e saques é um determinado cliente.

Tanto para realizar o depósito quanto para realizar o saque, é necessário apenas preencher três campos: selecionar um cliente, selecionar uma empresa, e então digitar o valor da transação. Fornecendo todos esses dados, um botão de "Confirmar" aparecerá.

#### Realizando um depósito
![Depósito](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/deposito.gif)

#### Realizando um saque
![Saque](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/saque.gif)

#### Transação mal sucedida
Uma transação pode não ser concretizada por diversas razões. O cliente ou empresa fornecidos podem na realidade não existirem, o valor da transação fornecido pode ser um valor negativo, nulo ou zero, e também o cliente ou a empresa pode não ter saldo suficiente para concretizar a transação. Em todos esses casos, a aplicação gerará um aviso ao usuário, indicando a ele o porquê da transação ter sido negada.
<br>
![Saldo Insuficiente](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/saldo-insuficiente.gif)

### 2. Cadastrar Clientes ou Empresas
Acessando a seção `Cadastrar`, você pode realizar o cadastro tanto de um cliente quanto de uma empresa na base de dados da aplicação.

Nos dois casos, será pedido dados cadastrais próprios de cada agente, sendo todos eles requeridos (não podem serem nulos). Caso algum dado não esteja no formato exigido, como por exemplo o email informado não tiver o formato exigido para um email, a aplicação informará o usuário. Após preencher todos os campos com valores válidos, um botão de "Confirmar" aparecerá para confirmar a operação.

#### Cadastrando um Cliente
![Cadastro Cliente](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/cadastro-cliente.gif)

#### Cadastrando uma Empresa
![Cadastro Empresa](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/cadastro-empresa.gif)


#### Cadastro mal sucedido
Uma tentativa de cadastro pode ser mal sucedida por diversas razões. O CPF ou CNPJ informado pode ser inválido ou então já foi utilizado para cadastrar outro cliente ou empresa, o saldo informado pode ser nulo ou negativo, entre outros invalidações possíveis. Em todos os casos, a aplicação alertará o usuário de qual erro foi cometido de forma clara, para que ele possa corrigi-lo devidamente.
<br>
![CPF Inválido](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/cpf-invalido.gif)

### 3. Visualizar os dados de Clientes, Empresas e Transações
Acessando a seção `Acesse os Dados`, o usuário poderá acessar os dados referentes aos clientes, empresas e transações realizadas que estão salvas na base de dados.
<br>
![Tabelas](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/tabelas.gif)

#### Deletar um registro
Através das tabelas, o usuário pode deletar um cliente, empresa ou transação caso deseje. Antes de ser concluída a exclusão, a aplicação gerará um alerta de confirmação, para evitar uma exclusão não desejada.
<br>
![Tabelas Deletar](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/tabelas-deletar.gif)

#### Alterar a taxa
Na tabela de Empresas, é possível editar o valor das taxas de depósito e saque de uma empresa. Fornecendo um valor válido (não nulo, negativo ou zero) a operação será realizada com sucesso e a aplicação fornecerá um alerta de confirmação.
<br>
![Alterar Taxa](https://github.com/SoSoJigsaw/Desafio_TGID/blob/main/img/manual%20do%20usuario/alterar-taxa.gif)