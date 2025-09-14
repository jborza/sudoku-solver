package com.jborza.sudokusolver.technique;

import com.jborza.sudokusolver.model.Puzzle;

import java.util.Optional;

public interface SolvingTechnique {
    String name();

    Optional<Change> apply(Puzzle puzzle);
}
