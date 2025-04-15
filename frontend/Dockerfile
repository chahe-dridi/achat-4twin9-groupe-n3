# Final Dockerfile — No Angular build, just serve existing /dist folder
FROM nginx:alpine

# Copy prebuilt Angular frontend (even if it's broken or incomplete)
COPY ./frontend-dist/achat-frontend /usr/share/nginx/html


# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
