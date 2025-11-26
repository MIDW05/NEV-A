package com.example.backend.service;

public interface CalendarService {
    String buildIcsForUsuario(Long usuarioId, int minutosBloqueEvento);
}
