package com.jborza.sudokusolver.service;

import com.jborza.sudokusolver.model.Puzzle;
import com.jborza.sudokusolver.model.PuzzleDTO;
import com.jborza.sudokusolver.model.StepResultDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<String, Puzzle> sessions = new ConcurrentHashMap<>();
    private final SolverService solver;

    public SessionService(SolverService solver){
        this.solver = solver;
    }


    public String createSessionFromDTO(PuzzleDTO dto) {
        Puzzle p = buildPuzzleFromGrid(dto.getGrid());
        solver.initializePossibles(p);
        String id = UUID.randomUUID().toString();
        sessions.put(id, p);
        return id;
    }

    public PuzzleDTO getPuzzleDTO(String sessionId) {
        Puzzle p = sessions.get(sessionId);
        if (p == null) throw new IllegalArgumentException("Session not found: " + sessionId);
        return PuzzleDTO.fromPuzzle(p);
    }

    public void resetSession(String sessionId, PuzzleDTO dto) {
        Puzzle p = buildPuzzleFromGrid(dto.getGrid());
        solver.initializePossibles(p);
        sessions.put(sessionId, p);
    }

    public StepResultDTO step(String sessionId) {
        Puzzle p = sessions.get(sessionId);
        if (p == null) throw new IllegalArgumentException("Session not found: " + sessionId);
        StepResultDTO res = solver.applyNextTechnique(p);
        res.setPuzzle(PuzzleDTO.fromPuzzle(p));
        return res;
    }

    private Puzzle buildPuzzleFromGrid(int[][] grid) {
        if (grid == null || grid.length != 9) throw new IllegalArgumentException("Grid must be 9x9");
        Puzzle p = new Puzzle();
        for (int r = 0; r < 9; r++) {
            if (grid[r].length != 9) throw new IllegalArgumentException("Grid must be 9x9");
            for (int c = 0; c < 9; c++) {
                int v = grid[r][c];
                if (v < 0 || v > 9) throw new IllegalArgumentException("Cell values must be 0..9");
                p.getCell(r, c).setValue(v);
            }
        }
        return p;
    }
}
