package com.abhinav.smart_library_system.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
public class HealthController {

    @GetMapping("/api/status")
    public Map<String, Object> getStatus() {
        return Map.of(
                "status", "Library System Online",
                "serverTime", LocalDateTime.now(),
                "database", "Connected to Neon PostgreSQL"
        );
    }
}