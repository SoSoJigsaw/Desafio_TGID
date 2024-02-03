# Imagem base do Node.js
FROM node:14-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo package.json e package-lock.json para dentro do container
COPY package*.json ./

# Instala as dependências do projeto
RUN npm install

# Copia todos os arquivos do projeto para dentro do container
COPY . .

# Compila o projeto Vue.js para produção
RUN npm run build

# Comando para servir o aplicativo Vue.js quando o container for iniciado
CMD ["npm", "run", "serve"]