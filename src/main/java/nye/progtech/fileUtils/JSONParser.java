package nye.progtech.fileUtils;

import nye.progtech.model.Hero;

public class JSONParser {
    private int size;
    private String[] board;
    private String[] originalBoard;
    private Hero hero;
    private String mapName;

    public JSONParser(int size, String[] board, String[] originalBoard, Hero hero, String mapName) {
        this.size = size;
        this.board = board;
        this.originalBoard = originalBoard;
        this.hero = hero;
        this.mapName = mapName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public String[] getOriginalBoard() {
        return originalBoard;
    }

    public void setOriginalBoard(String[] originalBoard) {
        this.originalBoard = originalBoard;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
