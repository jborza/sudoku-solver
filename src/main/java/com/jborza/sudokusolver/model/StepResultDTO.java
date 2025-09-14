package com.jborza.sudokusolver.model;

import java.util.ArrayList;
import java.util.List;

public class StepResultDTO {
    // TODO use something else than String
    private String technique; // name of technique applied or "none"
    private boolean changed;
    private PuzzleDTO puzzle;
    private List<String> notes = new ArrayList<>();

    public StepResultDTO() {}

    public String getTechnique() { return technique; }
    public void setTechnique(String technique) { this.technique = technique; }

    public boolean isChanged() { return changed; }
    public void setChanged(boolean changed) { this.changed = changed; }

    public PuzzleDTO getPuzzle() { return puzzle; }
    public void setPuzzle(PuzzleDTO puzzle) { this.puzzle = puzzle; }

    public List<String> getNotes() { return notes; }
    public void setNotes(List<String> notes) { this.notes = notes; }
}