package com.example.backend.controller;

import com.example.backend.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired private CalendarService calendarService;

    // Suscríbelo en Google/Outlook: https://calendar.google.com -> "Desde URL"
    @GetMapping(value = "/usuario/{usuarioId}.ics", produces = "text/calendar")
    public ResponseEntity<String> feedIcs(@PathVariable Long usuarioId,
                                          @RequestParam(defaultValue = "30") int minutosBloqueEvento) {
        String ics = calendarService.buildIcsForUsuario(usuarioId, minutosBloqueEvento);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuario-" + usuarioId + ".ics")
                .body(ics);
    }
}
