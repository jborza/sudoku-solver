package com.jborza.sudokusolver.model;

public class Puzzle {
    // 9x9 cells
    private final Cell[][] cells = new Cell[9][9];
    public Puzzle() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c] = new Cell();
            }
        }
    }

    public Cell getCell(int r, int c) { return cells[r][c]; }
    public Cell[][] getCells() { return cells; }
}
