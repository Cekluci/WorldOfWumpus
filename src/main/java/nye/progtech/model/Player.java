/**
 * Player objektum.
 */
package nye.progtech.model;

public class Player {
    /**
     * Játékos neve.
     */
    private String playerName;
    /**
     * Játékos pontszáma.
     */
    private int playerScore;

    /**
     * Player üres konstruktor.
     */
    public Player() {
    }

    /**
     * Getter a játékos nevéhez.
     * @return játékos neve
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter a játékos pontszámához.
     * @param setPlayerName játékos neve
     */
    public void setPlayerName(final String setPlayerName) {
        this.playerName = setPlayerName;
    }

    /**
     * Getter a játékos pontszámához.
     * @return pontszám
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Setter a játékos pontszámához.
     * @param setPlayerScore pontszám
     */
    public void setPlayerScore(final int setPlayerScore) {
        this.playerScore = setPlayerScore;
    }
}
