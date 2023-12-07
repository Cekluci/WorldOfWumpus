/**
 * Data Access Object a scoreboard objektumhoz.
 */
package nye.progtech.dao;

public class ScoreBoard {
    /**
     * Játékos neve.
     */
    private final String playerName;
    /**
     * Játékos elért pontszáma.
     */
    private final int playerScore;

    /**
     * Scoreboard konstruktora.
     *
     * @param sbPlayerName játékos neve
     * @param sbPlayerScore játékos pontszáma
     */
    public ScoreBoard(final String sbPlayerName, final int sbPlayerScore) {
        this.playerName = sbPlayerName;
        this.playerScore = sbPlayerScore;
    }

    /**
     * Getter a játékos nevéhez.
     *
     * @return játékos neve
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter a játékos pontszámához.
     *
     * @return pontszám
     */
    public int getPlayerScore() {
        return playerScore;
    }

}
