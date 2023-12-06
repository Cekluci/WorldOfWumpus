package nye.progtech.controller;

public enum MenuOption {
    PALYASZERKESZTO(1, "Pályaszerkesztő"),
    FILEBEOLVASAS(2, "File beolvasás"),
    BETOLTESADATBAZISBOL(3, "Betöltés Adatbázisból"),
    METESADATBAZISBA(4, "Mentés Adatbázisba"),
    JATEK(5, "Játék indítása"),
    SCOREBOARD(6, "Scoreboard"),
    KILEPES(7, "Kilépés");

    private final int value;
    private final String description;

    MenuOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption option : values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return null;
    }
}
