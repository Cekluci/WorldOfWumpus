/**
 * Data Access Object a pálya részletekhez.
 */
package nye.progtech.dao;

public class BoardDetails {
    /**
     * Pálya mérete.
     */
    private int boardSize;
    /**
     * Hős sora.
     */
    private int heroRowIndex;
    /**
     * Hős oszlopa.
     */
    private int heroColIndex;
    /**
     * Hős iránya.
     */
    private char heroDirection;
    /**
     * Pálya neve.
     */
    private String mapName;

    /**
     * konstruktor a pálya részletekhez.
     *
     * @param detailBoardSize pálya mérete
     * @param detailHeroRowIndex hős sora
     * @param detailHeroColIndex hős oszlopa
     * @param detailHeroDirection hős iránya
     * @param detailMapName pálya neve
     */
    public BoardDetails(final int detailBoardSize, final int detailHeroRowIndex,
                        final int detailHeroColIndex,
                        final char detailHeroDirection,
                        final String detailMapName) {
        this.boardSize = detailBoardSize;
        this.heroRowIndex = detailHeroRowIndex;
        this.heroColIndex = detailHeroColIndex;
        this.heroDirection = detailHeroDirection;
        this.mapName = detailMapName;
    }

    /**
     * Üres konstruktor a pálya részletekhez.
     */
    public BoardDetails() {
    }

    /**
     * Getter a pálya mérethez.
     *
     * @return boardSize
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Setter a pályamérethez.
     *
     * @param setBoardSize méret
     */
    public void setBoardSize(final int setBoardSize) {
        this.boardSize = setBoardSize;
    }

    /**
     * Getter a hős sor indexhez.
     *
     * @return heroRowIndex
     */
    public int getHeroRowIndex() {
        return heroRowIndex;
    }

    /**
     * Setter a hős sor indexhez.
     *
     * @param setHeroRowIndex hős sor index.
     */
    public void setHeroRowIndex(final int setHeroRowIndex) {
        this.heroRowIndex = setHeroRowIndex;
    }

    /**
     * Getter a hős oszlop indexhez.
     *
     * @return heroColIndex
     */
    public int getHeroColIndex() {
        return heroColIndex;
    }

    /**
     * Setter a hős oszlop indexhez.
     *
     * @param setHeroColIndex hős oszlop index.
     */
    public void setHeroColIndex(final int setHeroColIndex) {
        this.heroColIndex = setHeroColIndex;
    }

    /**
     * Getter a hős irányához.
     *
     * @return heroDirection
     */
    public char getHeroDirection() {
        return heroDirection;
    }

    /**
     * Setter a hős irányához.
     *
     * @param setHeroDirection hős iránya
     */
    public void setHeroDirection(final char setHeroDirection) {
        this.heroDirection = setHeroDirection;
    }

    /**
     * Getter a pályanévhez.
     *
     * @return mapName
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Setter a pálya névhez.
     *
     * @param setMapName pálya neve.
     */
    public void setMapName(final String setMapName) {
        this.mapName = setMapName;
    }
}
