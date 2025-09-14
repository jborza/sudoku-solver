package com.jborza.sudokusolver.controller;

import com.jborza.sudokusolver.model.PuzzleDTO;
import com.jborza.sudokusolver.model.StepResultDTO;
import com.jborza.sudokusolver.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService sessions;

    public SessionController(SessionService sessions){
        this.sessions = sessions;
    }

    // Create a session: POST body { "grid": [[...9x9 ints, 0 for empty]] }
    @PostMapping
    public ResponseEntity<Map<String, String>> createSession(@Valid @RequestBody PuzzleDTO request) {
        String id = sessions.createSessionFromDTO(request);
        return ResponseEntity.ok(Map.of("sessionId", id));
    }

    // Get current puzzle state
    @GetMapping("/{sessionId}")
    public ResponseEntity<PuzzleDTO> getSession(@PathVariable String sessionId){
        PuzzleDTO dto = sessions.getPuzzleDTO(sessionId);
        return ResponseEntity.ok(dto);
    }

    // Advance one solving step
    @PostMapping("/{sessionId}/step")
    public ResponseEntity<StepResultDTO> step(@PathVariable String sessionId) {
        StepResultDTO result = sessions.step(sessionId);
        return ResponseEntity.ok(result);
    }

    // Reset session with new grid
    @PostMapping("/{sessionId}/reset")
    public ResponseEntity<Void> reset(@PathVariable String sessionId, @Valid @RequestBody PuzzleDTO request) {
        sessions.resetSession(sessionId, request);
        return ResponseEntity.noContent().build();
    }
}
