# Imagem oficial do PostgreSQL
FROM postgres:13

# Variáveis de ambiente para configurar o PostgreSQL
ENV POSTGRES_DB=tgid
ENV POSTGRES_USER=tgid
ENV POSTGRES_PASSWORD=tgid

# Adicione script SQL personalizado para criar o banco de dados
COPY ./init.sql /docker-entrypoint-initdb.d/

# Porta padrão do PostgreSQL
EXPOSE 5432

# Comando de inicialização padrão
CMD ["postgres"]