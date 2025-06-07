package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    // Returns the first (earliest) desktop Session
    public SessionResponseDTO getFirstDesktopSession() {
        return sessionMapper.toDto(sessionRepository.getFirstDesktopSession(DeviceType.DESKTOP));
    }

    // Returns only Sessions from Active users that were ended before 2025
    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 0, 0);

        List<Session> sessions = sessionRepository.getSessionsFromActiveUsersEndedBefore2025(endDate);

        return sessions.stream()
                .map(sessionMapper::toDto)
                .toList();
    }
}
