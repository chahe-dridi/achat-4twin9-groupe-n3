import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

    private LOG_API = 'http://localhost:8089/logs'; // Endpoint Spring Boot pour enregistrer les logs

    constructor(private http: HttpClient) {}
  
    log(message: string, level: string = 'INFO') {
      const logEntry = { message, level, timestamp: new Date().toISOString() };
  
      console.log(`[${level}] ${message}`); // Affichage console
      this.http.post(this.LOG_API, logEntry).subscribe(); // Envoi au backend
    }
  }