package nye.progtech.DAO;

public class BoardDetails {
    private int boardSize;
    private int heroRowIndex;
    private int heroColIndex;
    private char heroDirection;
    private String mapName;

    public BoardDetails(int boardSize, int heroRowIndex, int heroColIndex, char heroDirection, String mapName) {
        this.boardSize = boardSize;
        this.heroRowIndex = heroRowIndex;
        this.heroColIndex = heroColIndex;
        this.heroDirection = heroDirection;
        this.mapName = mapName;
    }

    public BoardDetails() {
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getHeroRowIndex() {
        return heroRowIndex;
    }

    public void setHeroRowIndex(int heroRowIndex) {
        this.heroRowIndex = heroRowIndex;
    }

    public int getHeroColIndex() {
        return heroColIndex;
    }

    public void setHeroColIndex(int heroColIndex) {
        this.heroColIndex = heroColIndex;
    }

    public char getHeroDirection() {
        return heroDirection;
    }

    public void setHeroDirection(char heroDirection) {
        this.heroDirection = heroDirection;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
