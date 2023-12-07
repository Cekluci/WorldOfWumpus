package nye.progtech.model;

import nye.progtech.Colors;

public class Hero {
    private int column;
    private int row;
    private char direction;
    private int arrows;
    private boolean hasGold;
    private boolean isDead;

    public Hero(char column, int row, char direction, int arrows) {
        this.column = convertColumnToIndex(column);
        this.row = row - 1;
        this.direction = direction;
        this.arrows = arrows;
        this.hasGold = false;
        this.isDead = false;
    }

    public Hero() {

    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public boolean getHasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void HeroChangeDirection(char newDir) {
        char currentDirection = this.getDirection();
        if (currentDirection == newDir) {
            System.out.println(Colors.ANSI_YELLOW + "Már ebben az irányban állsz." + Colors.ANSI_RESET);
        } else {
            this.setDirection(newDir);
        }
    }

    public void HeroToMove(int row, int col) {
        setRow(row);
        setColumn(col);
    }

    private int convertColumnToIndex(char columnLabel) {

        return Character.toUpperCase(columnLabel) - 'A';
    }

}
