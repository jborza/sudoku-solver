package com.jborza.sudokusolver.technique;

import com.jborza.sudokusolver.model.Puzzle;
import com.jborza.sudokusolver.model.Cell;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(1)
public class SingleCandidateTechnique implements SolvingTechnique{
    @Override
    public String name() {
        return "Single Candidate";
    }

    @Override
    public Optional<Change> apply(Puzzle puzzle) {
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                Cell cell = puzzle.getCell(r,c);
                if(cell.isEmpty() && cell.getPossibles().size() == 1){
                    int v = cell.getPossibles().iterator().next();
                    return Optional.of(new Change(r,c,v));
                }
            }
        }
        return Optional.empty();
    }
}
