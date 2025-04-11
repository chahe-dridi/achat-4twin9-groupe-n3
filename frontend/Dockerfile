# Stage 1: Build Angular App
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Only copy dependency files first
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps --no-audit

# Copy project files and build
COPY . .
RUN npm run build --prod

# Stage 2: Serve Angular App
FROM node:18-alpine

# Install http-server
RUN npm install -g http-server

# Set working directory
WORKDIR /app

# Copy built Angular app from builder stage
COPY --from=builder /app/dist/achat-frontend /app

# Expose port
EXPOSE 4200

# Run the app
CMD ["http-server", "-p", "4200", "-c-1"]
