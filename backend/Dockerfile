# Imagem base do OpenJDK
FROM openjdk:11-jre-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR do Spring Boot para dentro do container
COPY ./target/*.jar app.jar

# Comando para executar a aplicação Spring Boot quando o container for iniciado
CMD ["java", "-jar", "app.jar"]