/**
 * ANSI színek értékei.
 */
package nye.progtech;

public final class Colors {

    private Colors() {
        throw new UnsupportedOperationException("This is a utility class,"
                    + "cannot be instanciated.");
    }

    /**
     * Eredeti szín visszaállítása.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * Piros.
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * Zöld.
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * Sárga.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * Kék.
     */
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * Lila.
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * Cián.
     */
    public static final String ANSI_CYAN = "\u001B[36m";
}
