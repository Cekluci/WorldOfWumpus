/**
 * Ez a package tartalmazza az összes controllert.
 */
package nye.progtech.controller;

public enum MenuOption {
    /**
     * Pályaszerkesztés menüpont.
     */
    PALYASZERKESZTO(1, "Pályaszerkesztő"),

    /**
     * File beolvasás menüpont.
     */
    FILEBEOLVASAS(2, "File beolvasás"),
    /**
     * Betöltés adatbázisból menüpont.
     */
    BETOLTESADATBAZISBOL(3, "Betöltés Adatbázisból"),
    /**
     * Mentés adatbázisba menüpont.
     */
    METESADATBAZISBA(4, "Mentés Adatbázisba"),
    /**
     * Játék indítása menüpont.
     */
    JATEK(5, "Játék indítása"),
    /**
     * Scoreboard megjelenítése menüpont.
     */
    SCOREBOARD(6, "Scoreboard"),
    /**
     * Kilépés a játékból.
     */
    KILEPES(7, "Kilépés");

    /**
     * Kiválasztott menüpont value.
     */
    private final int value;
    /**
     * Kiválasztott menüpont description.
     */
    private final String description;

    MenuOption(final int chosenValue, final String chosenDescription) {
        this.value = chosenValue;
        this.description = chosenDescription;
    }

    /**
     * Kiválasztott menü value.
     *
     * @return value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Kiválasztott menü description.
     *
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Kiválasztott menüelem int-re konvertálás.
     *
     * @param value kiválasztott menü value.
     *
     * @return MenuOption objektum.
     */
    public static MenuOption fromInt(final int value) {
        for (MenuOption option : values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return null;
    }
}
