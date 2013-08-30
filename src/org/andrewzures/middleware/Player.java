package org.andrewzures.middleware;

public interface Player {
    public int makeMove(Board board);
    public String getType();
    public String getSymbol();
}
