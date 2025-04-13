// src/app/services/logging.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment'; // Assuming you are using environments for config

@Injectable({
  providedIn: 'root'
})
export class LoggingService {


  constructor(private http: HttpClient) {}

  logInfo(message: string) {
    this.sendLog('INFO', message);
  }

  logError(message: string) {
    this.sendLog('ERROR', message);
  }

  private sendLog(level: string, message: string) {
    const log = {
      level: level,
      message: message,
      timestamp: new Date()
    };

  
  }
}
