package com.jborza.sudokusolver.model;

import java.util.ArrayList;
import java.util.List;

public class PuzzleDTO {
    // grid: 9x9 integers, 0 for empty
    private int[][] grid;

    // For responses we also include possibles per cell (nullable for requests)
    private List<List<List<Integer>>> possibles;

    public PuzzleDTO() {}

    public int[][] getGrid() { return grid; }
    public void setGrid(int[][] grid) { this.grid = grid; }

    public List<List<List<Integer>>> getPossibles() { return possibles; }
    public void setPossibles(List<List<List<Integer>>> possibles) { this.possibles = possibles; }

    // helper: create DTO from Puzzle
    public static PuzzleDTO fromPuzzle(Puzzle p) {
        PuzzleDTO dto = new PuzzleDTO();
        int[][] g = new int[9][9];
        List<List<List<Integer>>> poss = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            List<List<Integer>> rowPoss = new ArrayList<>();
            for (int c = 0; c < 9; c++) {
                Cell cell = p.getCell(r, c);
                g[r][c] = cell.getValue();
                List<Integer> ps = new ArrayList<>(cell.getPossibles());
                rowPoss.add(ps);
            }
            poss.add(rowPoss);
        }
        dto.setGrid(g);
        dto.setPossibles(poss);
        return dto;
    }
}