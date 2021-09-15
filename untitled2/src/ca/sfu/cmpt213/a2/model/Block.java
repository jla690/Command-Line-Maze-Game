package ca.cmpt213.a3.maze;

/*
Form the basis of the maze, the maze is a 2d array of block objects
 */
public class Block {
    private int position;

    private char symbol;
    private boolean explored;

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }


}
