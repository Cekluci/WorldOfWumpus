/**
 * Hero model.
 */
package nye.progtech.model;

import nye.progtech.Colors;

public class Hero {
    /**
     * Hős oszlopa.
     */
    private int column;
    /**
     * Hős sora.
     */
    private int row;
    /**
     * Hős iránya.
     */
    private char direction;
    /**
     * Hős nyilainak száma.
     */
    private int arrows;
    /**
     * Van-e a hősnél arany?.
     */
    private boolean hasGold;
    /**
     * Hős halott-e.
     */
    private boolean isDead;

    /**
     * Hős konstruktora.
     *
     * @param heroColumn hős oszlopa
     * @param heroRow hős sora
     * @param heroDirection hős iránya
     * @param heroArrows hős nyilainak száma
     */
    public Hero(final char heroColumn, final int heroRow,
                final char heroDirection, final int heroArrows) {
        this.column = convertColumnToIndex(heroColumn);
        this.row = heroRow - 1;
        this.direction = heroDirection;
        this.arrows = heroArrows;
        this.hasGold = false;
        this.isDead = false;
    }

    /**
     * Getter a hős oszlopához.
     *
     * @return hős oszlopa
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter a hős oszlopához.
     *
     * @param setColumn hős oszlopa
     */
    public void setColumn(final int setColumn) {
        this.column = setColumn;
    }

    /**
     * Getter a hős sorára.
     *
     * @return hős sora.
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter a hős sorára.
     *
     * @param setRow hős sora
     */
    public void setRow(final int setRow) {
        this.row = setRow;
    }

    /**
     * Hős iránya.
     *
     * @return hős iránya
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Setter a hős irányára.
     *
     * @param setDirection hős iránya
     */
    public void setDirection(final char setDirection) {
        this.direction = setDirection;
    }

    /**
     * Getter a hős nyilainak számára.
     *
     * @return hős nyilainak száma.
     */
    public int getArrows() {
        return arrows;
    }

    /**
     * Setter a hős nyilainak számához.
     *
     * @param setArrows nyilak száma.
     */
    public void setArrows(final int setArrows) {
        this.arrows = setArrows;
    }

    /**
     * Van-e a hősnél arany?.
     *
     * @return van vagy nincs, true vagy false
     */
    public boolean getHasGold() {
        return hasGold;
    }

    /**
     * Setter a van-e aranya a hősnek logikához.
     *
     * @param setHasGold true or false
     */
    public void setHasGold(final boolean setHasGold) {
        this.hasGold = setHasGold;
    }

    /**
     * Beállítjuk, hogy a hős halott-e.
     *
     * @param setIsDead true or false.
     */
    public void setIsDead(final boolean setIsDead) {
        this.isDead = setIsDead;
    }

    /**
     * Hős irányának beállítása.
     *
     * @param newDir új irány
     */
    public void heroChangeDirection(final char newDir) {
        char currentDirection = this.getDirection();
        if (currentDirection == newDir) {
            System.out.println(Colors.ANSI_YELLOW
                    + "Már ebben az irányban állsz."
                    + Colors.ANSI_RESET);
        } else {
            this.setDirection(newDir);
        }
    }

    /**
     * Hős mozgatása a megadott mezőre.
     *
     * @param htmRow új sor index
     * @param htmCol új oszlop index
     */
    public void heroToMove(final int htmRow, final int htmCol) {
        setRow(htmRow);
        setColumn(htmCol);
    }

    private int convertColumnToIndex(final char columnLabel) {

        return Character.toUpperCase(columnLabel) - 'A';
    }

}
