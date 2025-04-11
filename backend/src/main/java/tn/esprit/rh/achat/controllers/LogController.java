package tn.esprit.rh.achat.controllers;

import org.springframework.web.bind.annotation.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/logs")
public class LogController {
    private static final String LOG_FILE = "/logs/angular-logs.log"; // Fichier de logs

    @PostMapping
    public void log(@RequestBody LogEntry logEntry) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write("[" + logEntry.getLevel() + "] " + logEntry.getTimestamp() + " - " + logEntry.getMessage() + "\n");
            writer.flush(); // Assure que les logs sont écrits immédiatement
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public class LogEntry {
    private String message;
    private String level;
    private String timestamp;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}}