# Shopbuy

## Informações do projeto

- Grupo:

  - Caio Vinícius G. de Oliveira Dagostim
  - Emanuel Cardoso Tavecia
  - Gabriel Alves Teixeira
  - Guilherme Conti Machado

- Deploy:
  - https://shopbuy.onrender.com/swagger-ui/index.html
    - Obs: por ser um serviço gratuito, pode demorar para abrir.

## Rodando o projeto

- Para rodar o projeto, siga as instruções a seguir, conforme seu sistema operacional.

  - Irá rodar utilizando o banco que está no ar via serviço de deploy Render.

- **Linux/macOS**
  - Na pasta do projeto, rode o comando:
    - `./mvnw clean package`
  - Rode o projeto:
    - `java -jar target/buy-0.0.1-SNAPSHOT.jar`
  - O projeto irá rodar na porta padrão 8080, e você poderá acessar a documentação acessando a URL http://localhost:8080/swagger-ui.html
- **Windows**
  - Na pasta do projeto, rode o comando:
    - `mvnw.cmd clean package`
  - Rode o projeto:
    - `java -jar target/buy-0.0.1-SNAPSHOT.jar`
  - O projeto irá rodar na porta padrão 8080, e você poderá acessar a documentação acessando a URL http://localhost:8080/swagger-ui.html
