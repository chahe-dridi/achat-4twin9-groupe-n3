# Use a Node.js base image
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json for installing dependencies
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps --no-audit

# Copy the rest of the project files
COPY . .

# Build the Angular app for production
RUN npm run build --prod

# Install http-server to serve the Angular app
RUN npm install -g http-server

# Expose port 4200
EXPOSE 4200

# Serve the application using http-server
CMD ["http-server", "-p", "4200", "-c-1", "dist/achat-frontend"]
