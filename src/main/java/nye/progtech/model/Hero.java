package nye.progtech.model;

public class Hero {
    private int column;
    private int row;
    private char direction;
    private int arrows;

    public Hero(char column, int row, char direction, int arrows) {
        this.column = convertColumnToIndex(column);
        this.row = row - 1;
        this.direction = direction;
        this.arrows = arrows;
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

    private int convertColumnToIndex(char columnLabel) {

        return Character.toUpperCase(columnLabel) - 'A';
    }

    //Előre
    public void moveForward() {
        int currentRow = this.getRow();
        int currentColumn = this.getColumn();
        int futureColumn;
        int futureRow;
        char currentDirection = this.getDirection();

        switch (currentDirection) {
            case 'E':
                this.setColumn(this.getColumn() + 1);
                break;
            case 'S':
                this.setRow(this.getRow() + 1);
                break;
            case 'W':
                this.setColumn(this.getColumn() - 1);
                break;
            case 'N':
                this.setRow(this.getRow() - 1);
        }

    }
}
