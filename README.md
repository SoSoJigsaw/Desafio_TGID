# Desafio Tgid
<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">

  <img src="img/logoTgid.png" width="100%" height="20%">

  <br>
  <br>
  
  <p align="center">
    <a href="https://www.java.com/pt-BR/"><img src="https://img.shields.io/badge/Backend Langugage%3A-Java-orange"/></a>
    <a href="https://www.java.com/pt-BR/"><img src="https://img.shields.io/badge/Backend Framework%3A-SpringBoot-green"/></a>
    <a href="https://www.java.com/pt-BR/"><img src="https://img.shields.io/badge/Gerenciador de Dependências%3A-Maven-purple"/></a>
    <a href="https://www.javascript.com/"><img src="https://img.shields.io/badge/Testes Unitários%3A-JUnit5 e Mockito-red"/></a>
  </p>

  <p align="center">
    <a href="https://embraer.com/br/pt"><img src="https://img.shields.io/badge/Frontend%20Language%3A-Typescript-blue"/></a>
    <a href="https://www.javascript.com/"><img src="https://img.shields.io/badge/Frontend Language%3A-JavaScript-yellow"/></a>
    <a href="https://www.javascript.com/"><img src="https://img.shields.io/badge/Frontend Framework%3A-Vue.js-green"/></a>
  </p>
  
  <p align="center">
    <a href="http://fatecsjc-prd.azurewebsites.net/"><img src="https://img.shields.io/badge/Banco de Dados%3A-PostgreSQL-green"/></a>
    <a href="https://www.javascript.com/"><img src="https://img.shields.io/badge/DevOps%3A-Docker e Docker Compose-silver"/></a>
  </p>
</div>

## Desafio
Criação de um sistema utilizando ao menos dois usuários (Empresa e Cliente). Tanto o CPF quanto o CNPJ precisam serem validados. Para cada Empresa, deve haver ao menos um tipo de taxa de sistema que será 
incidida no momento da transação (seja saque ou depósito ). As Empresas devem ter um saldo que advém dos depósitos e saques realizados por Clientes na sua empresa, e já com o abate das taxas de administração. Clientes podem fazer depósitos e saques pelas Empresas (a depender dos saldos das empresas). No momento em que a transação é realizada, deve ser enviado um callback para Empresa informando a transação, e algum tipo de 
notificação para o Cliente (seja e-mail, SMS ou algo do tipo).

<br>

• <b>Pontos principais:</b> Lógica para regras de negócio, Modelagem de Dados, Clean Code, Manutenibilidade de código, Tratamento de Erros e Desacoplamento de componentes.

<br>

## Requisitos (Diferenciais)
- SpringBoot
- Documentação
- Propostas de Arquitetura
- Testes

<br>

## Solução
![gif]()
- Foi desenvolvida uma aplicação baseada em web que permite realizar transações (depósito ou saque), cadastrar novos clientes ou empresas, assim como ter acesso aos dados existentes no banco de dados. Muito além da criação da API no backend, foi também realizada uma interface simples e intuitiva que facilita o uso do usuário. Todas as exceções conhecidas foram tratadas e, com relação ao CPF e o CNPJ, foram criados algoritmos de autenticação, considerando não apenas o número de dígitos, mas também fazendo a verificação dos dígitos de controle, que são os dois últimos presentes tanto no CPF quanto no CNPJ.
- Além do tratamento de erros dentro do Spring Boot, foi configurado para o frontend Vue.js lidar com os erros retornados pelos controllers, realizando assim um evento que informa ao usuário a respeito da exceção ocorrida.
- Foram criados também métodos de envio de Callbacks para as empresas, assim como notificações por email aos clientes (usando o Spring Kafka), a respeito das transações que eles estão envolvidos e alterando então o saldo para o valor esperado. Esse saldo, no caso das empresas, é calculado considerando o valor da taxa para o tipo de transação definido anteriormente.
- Por fim, as funções vitais dos Controllers, Repositories e Services foram testados criando métodos de teste com a utilização de JUnit5 e Mockito.  

<br>

## Tecnologias Utilizadas
<details>
<summary>Front-End</summary>

* [JavaScript (ES6)](https://www.javascript.com)
* [TypeScript](https://www.typescriptlang.org/)
* [HTML5](https://www.w3schools.com/css/)
* [CSS3](https://www.w3schools.com/css/)
* [Vue.js](https://vuejs.org/)


</details>

<details>
<summary>Back-End</summary>

* [Spring boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Hibernate](https://hibernate.org/)
* [Apache Kafka](https://spring.io/projects/spring-kafka)
* [Apache Maven](https://maven.apache.org/)

</details>

<details>
<summary>Banco de Dados e DevOps</summary>

* [PostgreSQL](https://www.postgresql.org/)
* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)

</details>
<details>
<summary>Testes Unitários e de Integração</summary>

* [JUnit5](https://junit.org/junit5/)

* [Mockito](https://site.mockito.org/)

</details>

<details>
<summary>Outras ferramentas</summary>

* [Github](https://github.com/)

* [IntelliJ IDE](https://www.jetbrains.com/pt-br/idea/)

* [Visual Studio Code](https://code.visualstudio.com/)

</details>

<br>

## Estrutura do Banco (DER e Modelo Lógico)
O projeto conta com três entidades, sendo que em uma delas, a entidade `Transacao`, possui duas chaves estrangeiras, portanto possui relacionamento com as demais. As outras duas entidades são `Cliente` e `Empresa`.
![DER]()
![Modelo Lógico]()

<br>

## Manual de Uso
Caso tenha interesse em executar o projeto, o Manual de Uso ensina como instalar as dependências e como funciona cada uma das funcionalidades do projeto. [Acesse ele aqui]().

<br>

## Sumarização de Classes
Caso tenha interesse em analisar para qual funcionalidade cada classe serve, assim como a motivação de cada um de seus atributos e métodos, seus retornos, [acesse a sumarização de classes por aqui]().

<br>

## Sumarização de Testes
Caso tenha interesse em analisar os testes realizados e os resultados esperados, [acesse a sumarização de testes aqui]().

<br>

## Documentação Técnica
Nessa seção, será explorado alguns conceitos que foram implementados ao projeto e especificamente ao código, com o intuito de diminuir as chances de erros ocorrerem e para seguir as boas práticas convencionadas no desenvolvimento SpringBoot.

<br>

### 1. Implementação da Lógica de Negócios
Seguindo as boas práticas no ambiente de desenvolvimento Spring Boot, e a arquitetura adotada (arquitetura em camadas), a lógica de negócios da aplicação se desenvolveu dentro das classes que se encontram no package `service`. Ou seja, são nos services que a lógica de negócios se desenvolveu, sendo um service para cada entidade do banco de dados (Cliente, Empresa, Transacao). 

<br>

Dentro desses services, foram desenvolvidos métodos relacionados a rotinas do banco de dados (no caso, rotinas do Spring Data JPA). Portanto, os métodos incluem lógicas para:
- Criação de objetos de uma determinada Entity
- Geração de listas com todos os objetos de uma determinada Entity 
- Exclusão de um objeto de uma determinada Entity
- Modificação do valor de um atributo de um objeto de uma determinada Entity

<br>

Além destes métodos previsíveis e com estrutura habitual, alguns métodos da classe `TransacaoServiceImpl` demonstram um nível maior de complexidade lógica, já que dentro destes ocorre não somente mudanças na entidade Transacao, mas sim em todas as três entidades. Isso ocorre pelo fato da entidade Transacao possuir duas chaves estrangeiras que o conectam às entidades Cliente e Empresa. Ademais, os métodos `realizarDeposito` e `realizarSaque` realizam diversas operações com dependências externas, além de serem os métodos acionadores das notificações para empresas e emails para clientes.   

<br>

Por fim, é importante dizer que os métodos dos services procuram tratar os erros previsíveis durante a execução da lógica de negócios ou ao interagir com os componentes de persistência de dados, através de condicionais, `try/catch` e declarando uma exceção na descrição do próprio método com `throws`.

<br> 

### 2. O uso de contêineres Docker e o Docker Compose
Docker é uma plataforma de software que permite automatizar o processo de implantação de aplicativos em contêineres, oferecendo uma abordagem para "empacotar" um aplicativo com todas as suas dependências em um contêiner virtual. Já o Docker Compose é uma ferramenta que facilita a definição e execução de aplicativos Docker compostos por vários contêineres. 

<br>

Para esse projeto, optei por transformar todos os serviços envolvidos em contêineres:
- Banco de Dados PostgreSQL
- Zookeeper 
- Apache Kafka
- Spring Boot
- Vue.js

<br>

Há diversas vantagens em se utilizar o Docker. Vejo a utilização de contêineres como uma boa prática de desenvolvimento, já que eles são capazes de <b>isolar as dependências</b>, garantindo que o ambiente de execução seja consistente em diferentes máquinas e ambientes, e também por sua <b>portabilidade</b>, que permite que o contêiner seja executado em qualquer lugar que suporte Docker, independentemente do sistema operacional ou da infraestrutura subjacente. Pensando na possibilidade de alguém quiser executar o projeto, sem o uso do Docker haveria um problema, pois sendo o banco de dados local, seria impossível a execução do Spring Boot, assim como os métodos do KafkaProducer e KafkaConsumer retornariam erros.

<br>

Já a utilização do Docker Compose veio da necessidade de uma orquestração simples dos cinco contêineres que estão sendo utilizados no projeto. Outra necessidade que havia e com o Docker Compose pude resolver, é o de garantir que um determinado serviço seja iniciado antes de outro, ou seja, o Docker Compose garante que os serviços sejam iniciados na ordem correta e tenha acesso uns aos outros conforme necessário. Esse era um problema que eu enfrentava com relação o Apache Kafka, já que o serviço necessita de que o Zookeeper esteja sendo executado antes dele, caso contrário, o Kafka apresentará diversos erros. Assim como, para iniciar o Spring Boot, é necessário que o contêiner do PostgreSQL, Zookeeper e Kafka estejam em execução.

<br>

### 3. Tratamento de Erros/Exceções
Ao invés de utilizar apenas exceções nativas do Spring e de suas dependências, foram criadas também classes que estendem a classe `RuntimeException` para se ter exceções personalizadas que atendiam a erros comuns que poderíam serem lançados entre os métodos e operações das classes. Essas exceções personalizadas correspondem às operações que ocorrem nos Controllers e Services, atendendo à lógica de negócio do projeto. 

<br>

A utilização do tratamento de erros visava dar à aplicação mais robustez, garantindo que ela possa lidar com situações inesperadas sem falhar abruptamente. Através das exceções personalizadas, o feedback (output) fornecido consegue se tornar mais significativo ao usuário, pois consegue descrever de forma simples qual é o erro que está ocorrendo, além de tornar mais fácil o entendimento do erro para diagnosticar os problemas quando eles ocorrerem. Ou seja, as exceções personalizadas facilitam o rastreamento e a depuração de erros, pois fornecem informações detalhadas sobre a natureza específica do problema. Além disso, as exceções personalizadas tornam o código mais legível e claro, indicando explicitamente as condições excepcionais que podem ocorrer.

<br>

Basicamente, todos os erros que poderíam serem gerados dentro da aplicação a partir de seus métodos que estão relacionados à lógica de negócio, foram devidamente tratados, criando-se classes de exceção a elas e incluindo as mesmas nos métodos do Controller e Service, seja com condições lógicas como o `if` ou então através do `try/catch`.

<br>

Além do tratamento de erros através do uso de exceções personalizadas, que retornam uma mensagem no console do Springboot, alguns erros foram tratados também a nível do usuário da do frontend. Para esses casos, os erros foram tratados de forma a enviar uma resposta `ResponseEntity<?>` informando um HTTP Status de `Bad Request`, junto a um body que descreve o erro. Esse tipo de tratamento de erro é útil na ocasião em que o resultado da requisição influencia o comportamento do Vue.js. Um exemplo disso é quando há uma exceção lançada por um CPF inválido: neste caso, o tratamento do erro é feito enviando uma `ResponseEntity<?>` com body "CPF Inválido". Assim que o Vue.js reconhece essa resposta, ele cria alertas que avisam ao usuário do frontend a respeito do erro ocorrido e, ao mesmo tempo, o erro é descrito no console do Spring.

<br>

### 4. Injeção de Dependências
Injeção de dependências é um padrão de design utilizado no paradigma orientado a objetos, onde as dependências de um objeto são fornecidas a ele por meio de construtores, métodos de configuração ou de forma direta, em vez de o próprio objeto criar essas dependências. A importância da injeção de dependências no Spring Boot reside no fato de que ela promove um código mais limpo, modular e de fácil manutenção.

<br>

O Spring Boot utiliza principalmente dois tipos de injeção de dependências: a injeção de dependências por construtor e a injeção de dependências por meio de anotações, como `@Autowired`

<br>

Como boa prática de desenvolvimento em Spring Boot, eu escolhi para Controllers e Services, utilizar a <b>injeção de dependências por construtor</b>, já que traz maior clareza e transparência, pelo fato de todas as dependências serem explicitamente declaradas no construtor da classe, facilitando a compreensão do código e a identificação das dependências necessárias. Além disso, sua característica de imutabilidade é considerada uma prática recomendada. Por exemplo, os controllers geralmente têm uma ou algumas dependências que são essenciais para o seu funcionamento, como serviços para lidar com a lógica de negócios e acesso a dados. A injeção por construtor facilita a passagem dessas dependências de forma clara e explícita, tornando o código mais fácil de entender, testar e manter.

<br>

As classes de teste também utilizam a injeção de dependências por construtor, já que facilita a criação de instâncias da classe de teste com dependências mockadas ou simuladas. É extremamente útil em testes unitários, onde se deseja isolar a unidade de código sendo testada e fornecer mocks para suas dependências. 

<br>

### 5. Arquitetura em camadas (ou Arquitetura de três camadas)
Quando se trata de desenvolver um software, a arquitetura é fundamental para garantir que o sistema seja bem organizado, escalável, e de fácil manutenção. Por isso, optei para esse projeto a `Arquitetura em Camadas", que é um padrão arquitetural similar ao MVC, mas que é considerada uma implementação mais moderna e flexível, pois permite uma maior separação de responsabilidades e uma melhor modularidade do código.

<br>

Essa arquitetura organiza o sistema em camadas distintas, cada uma responsável por uma função específica. Essa abordagem promove a separação de responsabilidades e facilita a manutenção, escalabilidade e testabilidade do código. Um exemplo de como essa arquitetura divide o sistema em camadas distintas, é o fato de que Controllers, Services e Repositories possuem atribuições diferentes.

<br>

Quanto a sua característica de modularidade do código, cada camada pode ser desenvolvida, testada e mantida de forma independente. Isso facilita a reutilização de código e permite que diferentes partes do sistema sejam modificadas sem afetar o restante do código. O Controller, Service e Repository são componentes independentes que podem ser desenvolvidos separadamente e substituídos conforme necessário, sem afetar as outras partes do sistema. 

<br>

#### Controllers
- Os controllers são responsáveis por receber as requisições HTTP, chamar os métodos dos services apropriados e retornar as respostas adequadas. Essa camada lida com a interação entre o cliente (usuário) e a lógica de negócios (Service).
#### Services
- Contêm a lógica de negócios da aplicação. São responsáveis por realizar operações mais complexas, coordenando a interação entre os repositories e executando ações específicas do domínio da aplicação. Ou seja, o service encapsula a lógica de negócios e manipula os dados.
#### Repositories
- São responsáveis por acessar e manipular os dados no banco de dados. Eles fornecem métodos para realizar operações de leitura e gravação no banco de dados de forma transparente para as outras camadas da aplicação. Ou seja, o repository lida com a persistência e recuperação de dados.	

<br>

### 6. Desacoplamento de componentes
O desacoplamento de componentes é um princípio de design em engenharia de software que preconiza a redução das dependências entre diferentes partes de um sistema. Em outras palavras, componentes desacoplados são aqueles que têm o mínimo possível de conhecimento sobre a implementação interna um do outro, o que os torna mais independentes e flexíveis. O desacoplamento promove a modularidade, facilita a manutenção, testabilidade e escalabilidade do código.

<br>

Como práticas de desacoplamento no projeto, foram utilizados:
- <b>Injeção de Dependências:</b> com a injeção de dependência, as dependências de um componente são injetadas por um contêiner de IoC, em vez de serem instanciadas pelo próprio componente. Isso reduz as dependências diretas entre os componentes. Como método principal, foi utilizado a injeção de dependências por construtor.
- <b>Interfaces:</b> definir interfaces para os componentes permite que diferentes implementações sejam trocadas sem afetar os clientes dessas interfaces, promovendo assim um alto grau de desacoplamento. Dentro do projeto, foram criadas interfaces para Controllers, Services, Repositories e demais componentes.
- <b>Uso de DTOs (Data Transfer Objects):</b> o uso de DTOs para transferência de dados ajuda a reduzir o acoplamento entre as entidades. No projeto, alguns DTOs foram criados com o intuito de formular a response de requisições em um formato que se aproxima do JSON, assim como para receber dados JSON de requests e poder manipulá-los no Spring Boot.
- <b>Eventos e Listeners:</b> são componentes que reagem a eventos sem terem conhecimento direto uns dos outros. Isso é útil para desacoplar a lógica de negócios. O Apache Kafka, que está sendo utilizado na aplicação, pode ser considerado como uma forma de implementar a comunicação assíncrona entre componentes do sistema. Simplesmente, um produtor publica mensagens em um tópico do Kafka e um consumidor escuta esse tópico usando a anotação `@KafkaListener` no Spring Boot. Quando o consumidor detecta uma nova mensagem no tópico, ele executa um método específico para processar essa mensagem, sem ter conhecimento direto do produtor.

<br>

### 7. Mensageria com Apache Kafka
O Apache Kafka é uma plataforma de streaming distribuída que permite a publicação e subscrição de fluxos de dados em tempo real. Ele permite que os serviços comuniquem-se de forma assíncrona, o que pode melhorar a escalabilidade e a resiliência da aplicação. O Kafka pode ser usado para processar grandes volumes de dados em tempo real, permitindo que as aplicações do Spring Boot processem e reajam a eventos em tempo real.

<br>

Apesar de não ser um projeto de microsserviços, decidi adotar o Apache Kafka visando um deploy futuro que tenha um nível maior de complexidade, já que o Kafka traz uma solução flexível e expansível. Isso significa que, se no futuro a aplicação migrar para uma arquitetura baseada em microserviços, já terá a infraestrutura necessária em vigor. Minha decisão pelo Apache Kafka foi também por suas várias vantagens: 
- Ao utilizar o Kafka, você pode executar operações de forma assíncrona. Isso significa que, quando um tópico é ouvido pelo KafkaListener, o método anotado com `@KafkaListener` pode ser executado de forma independente do restante da lógica do aplicativo. Isso pode melhorar o desempenho geral do aplicativo, permitindo que outras operações continuem enquanto o método do KafkaListener é executado.
- Kafka foi projetado para ser altamente escalável, mesmo em um ambiente não distribuído. Isso significa que, à medida que o volume de mensagens aumenta, o Kafka pode lidar com essa carga adicional de forma eficiente, garantindo que o aplicativo Spring Boot possa crescer sem comprometer o desempenho.
- Kafka oferece recursos de persistência de mensagens, garantindo que as mensagens não sejam perdidas mesmo em caso de falha do consumidor. Isso significa que, mesmo que o consumidor do Kafka não esteja disponível por algum motivo, as mensagens ainda serão processadas assim que o consumidor estiver de volta online.
- Kafka é ideal para lidar com fluxos de dados em tempo real. Mesmo em um aplicativo que não é baseado em microserviços, pode-se aproveitar essa capacidade do Kafka para processar eventos em tempo real, como a criação e envio de e-mails, que é especificamente a finalidade de uso para essa aplicação.

<br>

#### Envio de emails aos Clientes
Para enviar emails aos clientes envolvidos em transações, foi utilizado componentes do Spring Mail para criar um objeto no formato necessário (`MimeMessage`) e `JavaEmailSender` para enfim confirmar o envio. Antes do evento de envio do email, como essa funcionalidade é baseada na utilização de um servidor Apache Kafka, foi necessário converter um objeto do tipo `EmailDTO` em String, enviar então essa String através do `KafkaTemplate` para o servidor Kafka no tópico previamente definido. A mensagem enviada pelo Produtor é basicamente uma String contendo todos os dados necessários para a criação de um email (destinatário, assunto, corpo). Logo em seguida, de forma assíncrona, a mensagem produzida pelo Produtor (`KakfaProducer`) é ouvida pelo Consumidor (`KafkaConsumer`) através do método com anotação `@KafkaListener`, e então o método do Listener é executado, sem a necessidade de executar a chamada do método de maneira manual ou em declaração no código. Com a execução do método do Consumidor, a String passada pelo Produtor é novamente configurada como um objeto `EmailDTO`, para que depois os atributos desse objeto sejam os argumentos do objeto email que está sendo criado. Por fim, um método do  `JavaMailSender` é chamado com `email` como parâmetro, e assim é realizado o envio.
 
<br>

#### Callback para as Empresas
Um callback é uma função que é passada como argumento para outra função e é executada após a conclusão dessa função. No contexto de uma chamada REST assíncrona, o callback é executado quando a resposta da chamada é recebida. Para realizar o callback para a Empresa, foi utilizado o `RestTemplate`, no qual um callback pode ser enviado como parte de uma chamada assíncrona a um serviço REST externo.

<br>

### 8. Testes Unitários e de Integração

<br>

#### Testes Unitários
Para garantir a qualidade e facilitar a manutenção do código, foram criados testes unitários para todas as classes existentes no projeto. Foi utilizado para os testes o JUnit5 junto ao Mockito. [Você pode acessar a Documentação de cada classe de teste aqui]().

<br>

Os testes unitários foram de grande ajuda para o meu processo de desenvolvimento, pois que me permitiu fazer alterações no código com mais confiança, já que os testes me passaram a garantia de que qualquer regressão seria detectada.

<br>

#### Testes de Integração


<br>

### 9. Validação de CPF e CNPJ
Para validar o CPF e CNPJ, foram criadas classes onde há um método com algoritmos de validação, que fazem diversos procedimentos de teste para verificar não só o número de dígitos fornecidos, mas também se o CPF ou CNPJ se enquadra nas regras dos dígitos de controle.

<br>

Os dígitos de controle são dígitos verificadores utilizados para garantir a integridade dos números de identificação. No caso do CPF, são os dois últimos dígitos do número, que são calculados com base nos nove primeiros dígitos por meio de um algoritmo específico, conhecido como Módulo 11. Esses dígitos têm a função de verificar se o número do CPF é válido.

<br>

Já no caso do CNPJ, os dois últimos dígitos também são dígitos verificadores, mas o cálculo é um pouco diferente. Eles são calculados com base nos doze primeiros dígitos do número de CNPJ, também utilizando um algoritmo específico para garantir a validade do número.

<br>

Portanto, a partir dos métodos criados para validar ou não CPF ou CNPJ, não há a possibilidade de cadastramento de uma Empresa ou Cliente apenas usando o número de dígitos que esses documentos possuem, evitando assim fraudes ou erros de digitação por parte do usuário.

<br>

Quando ocorre a validação, caso ocorra um comportamento de erro, ocorre uma exceção que já está tratada: `CnpjInvalidoException` e `CpfInvalidoException`. No caso de não ocorrer nenhuma exceção, e o retorno do método `isValid()` seja `false`, a respectiva classe Controller (que é a responsável pela chamada dos métodos validadores) retornará uma response `Bad Request` com body "CPF Inválido" ou "CPNJ Válido". Essa response é acessada pelo frontend, determinando assim seu comportamento, como a criação de alertas para informar ao usuário que o CPF/CNPJ fornecido não é válido. No console do Spring, a invalidação também é informada.

<br>

## Sobre o desenvolvedor

<div style="display: flex; flex-direction: column; justify-content: center; align-items: center; gap: 10px;">
  
<img src="img/perfil_felipe.png" alt="Sobre mim">

<br>
<br>

<h3 align="center"> Acesse meu portfólio ou rede profissional:</h3>
              
<div style="display: flex; flex-direction: row;">
  <p align="center" ">                                                      
    <a href="https://github.com/SoSoJigsaw" style="width: fit-content; height: auto;">
      <img src="img/github.png" style="width: fit-content;">
    </a>
    <a href="https://www.linkedin.com/in/sosojigsaw/" style="width: fit-content; height: auto;">
      <img src="img/linkedin.png" style="width: fit-content">
    </a>
  </p>
</div>
            



<br>
<br>




<div style="display: flex; flex-direction: row; justify-content: center; align-items: center;">      

   <h3 align="center">Meu <b>Skill Set</b>:</h3>

   <table align="left">
     <tbody>
       <div style="display: flex; justify-content: space-between; width: 100%;">
       <tr> 
         <td colspan="2">
           <table align="center">
            <thead>
                <tr>
                    <th></th>
                    <th>Tecnologia</th>
                    <th>Experiência</th>
                    <th>Tempo de Uso</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td align="center"><img src="img/tecnologias/java.svg" width="75px" height="auto" align="center"></td>
                    <td><em>Java</em></td>
                    <td>Avançado</td>
                    <td>5 anos</td>
                </tr>
                <tr>
                    <td align="center"><img src="img/tecnologias/springboot.svg" width="50px" height="auto" align="center"></td>
                    <td><em>SpringBoot</em></td>
                    <td>Avançado</td>
                    <td>4 anos</td>
                </tr>
                <tr>
                      <td align="center">
                        <p align="center">
                          <img src="img/tecnologias/hibernate.png" width="150px" height="auto" align="center">
                          <img src="https://www.baeldung.com/wp-content/uploads/2021/02/lsd-module-icon-1.png" width="50px" height="auto" align="center"></p>
                      </td>
                      <td><em>JPA e Hibernate</em></td>
                      <td>Avançado</td>
                      <td>4 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" width="50px" height="auto" align="center"></td>
                      <td><em>SpringBoot Security</em></td>
                      <td>Avançado</td>
                      <td>4 anos</td>
                </tr>
                <tr>
                      <td align="center">
                        <p align="center">
                          <img src="img/tecnologias/postgre.svg" width="60px" height="auto" align="center">
                          <img src="img/tecnologias/oracle.svg" width="75px" height="auto" align="center">
                          <img src="img/tecnologias/MySQL.svg" width="75px" height="auto" align="center">
                      </td>
                      <td><em>SQL e PL/SQL</em></td>
                      <td>Avançado</td>
                      <td>5 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="img/tecnologias/junit.png" width="50px" height="auto" align="center"></td>
                      <td><em>JUnit5</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr>
                <tr>
                      <td align="center"><img src="img/tecnologias/mockito.png" width="150px" height="auto" align="center"></td>
                      <td><em>Mockito</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr>
                <tr>
                      <td align="center">
                        <p align="center">
                          <img src="img/tecnologias/git.svg" width="50px" height="auto" align="center">
                          <img src="img/tecnologias/github.png" width="75px" height="auto" align="center">
                        </p>  
                      </td> 
                      <td><em>Git e Github</em></td>
                      <td>Avançado</td>
                      <td>5 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="img/tecnologias/scrum.png" width="50px" height="auto" align="center"></td>
                      <td><em>Metodologia Ágil Scrum</em></td>
                      <td>Avançado</td>
                      <td>4 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/docker.svg" width="50px" height="auto"></td>
                      <td><em>Docker</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/docker-compose.png" width="75px" height="auto" align="center"></td>
                      <td><em>Docker Compose</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr> 
            </div>
            </tbody>
          </table>
         </td>
         <td colspan="2">
           <table align="center">
            <thead>
                <tr>
                    <th></th>
                    <th>Tecnologia</th>
                    <th>Experiência</th>
                    <th>Tempo de Uso</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                      <td align="center"><img src="img/tecnologias/apache-kafka.png" width="50px" height="auto" align="center"></td>
                      <td><em>Apache Kafka</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr>  
                <tr>
                      <td align="center"><img src="https://profilinator.rishav.dev/skills-assets/javascript-original.svg" width="50px" height="auto" align="center"></td>
                      <td><em>JavaScript</em></td>
                      <td>Avançado</td>
                      <td>3 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="https://profilinator.rishav.dev/skills-assets/typescript-original.svg" width="50px" height="auto" align="center"></td>
                      <td><em>TypeScript</em></td>
                      <td>Avançado</td>
                      <td>2 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="https://profilinator.rishav.dev/skills-assets/vuejs-original-wordmark.svg" width="50px" height="auto" align="center"></td>
                      <td><em>Vue.js</em></td>
                      <td>Avançado</td>
                      <td>3 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="https://profilinator.rishav.dev/skills-assets/react-original-wordmark.svg" width="50px" height="auto" align="center"></td>
                      <td><em>React.js</em></td>
                      <td>Intermediário</td>
                      <td>1 ano</td>
                </tr>
                <tr>
                      <td align="center"><img src="img/tecnologias/css3.svg" width="50px" height="auto" align="center"></td>
                      <td><em>CSS3</em></td>
                      <td>Avançado</td>
                      <td>5 anos</td>
                </tr>
                <tr>
                      <td align="center"><img src="img/tecnologias/html5.svg" width="50px" height="auto" align="center"></td>
                      <td><em>HTML5</em></td>
                      <td>Avançado</td>
                      <td>5 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="https://www.qfs.de/fileadmin/Webdata/logos-icons/JavaFX.png" width="75px" height="auto" align="center"></td>
                      <td><em>JavaFx</em></td>
                      <td>Intermediário</td>
                      <td>2 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/python.svg" width="50px" height="auto" align="center"></td>
                      <td><em><b>Python</b></em></td>
                      <td>Avançado</td>
                      <td>5 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/flask-python.png" width="50px" height="auto" align="center" 
                                           filter: sepia(100%) hue-rotate(60deg) brightness(0.6) saturate(5);></td>
                      <td><em>Flask</em></td>
                      <td>Avançado</td>
                      <td>4 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/pandas.png" width="150px" height="auto" align="center"></td>
                      <td><em>Pandas</em></td>
                      <td>Avançado</td>
                      <td>3 anos</td>
                </tr>  
                <tr>
                      <td align="center"><img src="img/tecnologias/sqlAlchmy.png" width="150px" height="auto" align="center"></td>
                      <td><em>SQLAlchemy</em></td>
                      <td>Avançado</td>
                      <td>4 anos</td>
                </tr>   
                <tr>
                      <td align="center"><img src="img/tecnologias/selenium.png" width="50px" height="auto" align="center"></td>
                      <td><em>Selenium</em></td>
                      <td>Intermediário</td>
                      <td>3 anos</td>
                </tr>   
              </tbody>
            </table>
         </td>
       </tr>
     </tbody>
  </table>   
     
        
</div>              
</div>

<p align="right">(<a href="#top">Voltar ao Topo</a>)</p>
