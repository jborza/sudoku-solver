package com.jborza.sudokusolver.model;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private int value; // EMPTY if empty
    public static final int EMPTY = 0;

    private final Set<Integer> possibles = new HashSet<>();

    public Cell(){
        this.value = EMPTY;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public Set<Integer> getPossibles() { return possibles; }

    public boolean isEmpty(){
        return this.value == EMPTY;
    }

    @Override
    public String toString() {
        return "Cell{value=" + value + ", possibles=" + possibles + "}";
    }
}
