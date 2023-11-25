package nye.progtech.DAO;

public class Tile {
    private int row;
    private int column;
    private char content;
    private String mapName;

    public Tile(int row, int column, char content, String mapName) {
        this.row = row;
        this.column = column;
        this.content = content;
        this.mapName = mapName;
    }

    public Tile() {

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
