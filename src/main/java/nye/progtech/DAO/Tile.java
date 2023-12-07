/**
 * Data Access Object a pálya elemeihez (mezőhöz).
 */
package nye.progtech.DAO;

/**
 * Data Access Object osztály a pálya elemeihez.
 */
public class Tile {
    /**
     * Mező sora.
     */
    private int row;
    /**
     * Mező oszlopa.
     */
    private int column;
    /**
     * Mező tartalma.
     */
    private char content;
    /**
     * Pálya neve.
     */
    private String mapName;

    /**
     * Konstruktor a pályaelemhez.
     * @param tRow sor
     * @param tColumn oszlopo
     * @param tContent tartalom
     * @param tMapName pályanév
     */
    public Tile(final int tRow, final int tColumn,
                final char tContent, final String tMapName) {
        this.row = tRow;
        this.column = tColumn;
        this.content = tContent;
        this.mapName = tMapName;
    }

    /**
     * Üres konstruktor.
     */
    public Tile() {

    }

    /**
     * Getter a sorhoz.
     * @return sor
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter a sorhoz.
     * @param setRow sor
     */
    public void setRow(final int setRow) {
        this.row = setRow;
    }

    /**
     * Getter az oszlophoz.
     * @return oszlop
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter az oszlophoz.
     * @param setColumn oszlop
     */
    public void setColumn(final int setColumn) {
        this.column = setColumn;
    }

    /**
     * Getter a tartalomhoz.
     * @return tartalom
     */
    public char getContent() {
        return content;
    }

    /**
     * Setter a tartalomhoz.
     * @param setContent tartalom
     */
    public void setContent(final char setContent) {
        this.content = setContent;
    }

    /**
     * Getter a pálya nevéhez.
     * @return pálya neve
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Setter a pálya nevéhez.
     * @param setMapName pálya neve
     */
    public void setMapName(final String setMapName) {
        this.mapName = setMapName;
    }
}
