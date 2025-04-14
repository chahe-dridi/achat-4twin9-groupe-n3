# Étape 1 : Construction de l'application Angular
FROM node:16 AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier package.json et package-lock.json pour installer les dépendances
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le reste des fichiers du projet Angular dans le conteneur
COPY . .

# Compiler l'application Angular (génère /app/dist)
RUN npm run build

# Étape 2 : Serveur Nginx pour héberger l'application
FROM nginx:alpine

# Copier les fichiers générés dans l'étape de build vers le répertoire de Nginx
COPY --from=build /app/dist/achat-frontend /usr/share/nginx/html

# Exposer le port 80 pour accéder à l'application
EXPOSE 80

# Commande pour démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]
