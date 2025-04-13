# Étape 1 : Construction de l'application Angular
FROM node:18 AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier package.json et package-lock.json pour installer les dépendances
COPY package.json package-lock.json ./

# Installer les dépendances, incluant Angular CLI globalement
RUN npm install --legacy-peer-deps
RUN npm install -g @angular/cli  # Installation globale de Angular CLI

# Copier le reste des fichiers du projet Angular dans le conteneur
COPY . .

# Compiler l'application Angular (génère /app/dist)
RUN export NODE_OPTIONS=--openssl-legacy-provider && ng build

# Étape 2 : Serveur Nginx pour héberger l'application
FROM nginx:alpine

# Copier les fichiers générés dans l'étape de build vers le répertoire de Nginx
COPY --from=build /app/dist/achat-frontend /usr/share/nginx/html

# Exposer le port 80 pour accéder à l'application
EXPOSE 80

# Commande pour démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]

