package com.jborza.sudokusolver.service;

import com.jborza.sudokusolver.model.Cell;
import com.jborza.sudokusolver.model.Puzzle;
import com.jborza.sudokusolver.model.StepResultDTO;
import com.jborza.sudokusolver.technique.SolvingTechnique;
import com.jborza.sudokusolver.technique.Change;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class SolverService {
    private final List<SolvingTechnique> techniques;

    public SolverService(List<SolvingTechnique> techniques) {
        this.techniques = techniques;
    }

    // Initialize possibles for all empty cells
    public void initializePossibles(Puzzle p) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Cell cell = p.getCell(r, c);
                cell.getPossibles().clear();
                if (cell.getValue() != 0) continue;
                Set<Integer> used = new HashSet<>();
                // row
                for (int cc = 0; cc < 9; cc++) {
                    int v = p.getCell(r, cc).getValue();
                    if (v != Cell.EMPTY) used.add(v);
                }
                // column
                for (int rr = 0; rr < 9; rr++) {
                    int v = p.getCell(rr, c).getValue();
                    if (v != Cell.EMPTY) used.add(v);
                }
                // box
                int br = (r / 3) * 3, bc = (c / 3) * 3;
                for (int rr = br; rr < br + 3; rr++) {
                    for (int cc = bc; cc < bc + 3; cc++) {
                        int v = p.getCell(rr, cc).getValue();
                        if (v != Cell.EMPTY) used.add(v);
                    }
                }
                for (int n = 1; n <= 9; n++) {
                    if (!used.contains(n)) {
                        cell.getPossibles().add(n);
                    }
                }

            }
        }
    }

    // Apply the next available technique (the first that makes progress)
// Try each technique in order; apply the first that makes progress
    public StepResultDTO applyNextTechnique(Puzzle puzzle) {
        initializePossibles(puzzle);

        for (SolvingTechnique tech : techniques) {
            Optional<Change> change = tech.apply(puzzle);
            if (change.isPresent()) {
                applyChange(puzzle, change.get());
                StepResultDTO res = new StepResultDTO();
                res.setTechnique(tech.name());
                res.setChanged(true);
                res.getNotes().add("Set cell " + toCellRef(change.get().row(), change.get().column())
                        + " to " + change.get().value() + " by " + tech.name());
                return res;
            }
        }

        StepResultDTO res = new StepResultDTO();
        res.setTechnique("none");
        res.setChanged(false);
        res.getNotes().add("No further progress with current techniques");
        return res;
    }

    private void applyChange(Puzzle p, Change ch) {
        p.getCell(ch.row(), ch.column()).setValue(ch.value());
        initializePossibles(p);
    }

    private String toCellRef(int r, int c) {
        return "(" + (r + 1) + "," + (c + 1) + ")";
    }
}