/**
 * Játék vezérlése.
 */
package nye.progtech.Game;

import nye.progtech.Colors;
import nye.progtech.controller.ConsoleController;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.model.Player;

import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

public class Game {
    /**
     * Hero objektum.
     */
    private final Hero hero;
    /**
     * GameBoard objektum.
     */
    private final GameBoard gameBoard;
    /**
     * A játékos objektuma.
     */
    private final Player player;
    /**
     * Wumpus sora.
     */
    private int wumpusRow;
    /**
     * Wumpus oszlopa.
     */
    private int wumpusCol;

    /**
     * Hős nyilainak száma.
     */
    private int arrows;
    /**
     * Bázis sor, ahova vissza kell térni az arannyal.
     */
    private final int baseRow;
    /**
     * Bázis oszlop, ahova vissza kell térni az arannyal.
     */
    private final int baseCol;
    /**
     * Játékos pontszáma.
     */
    private int playerScore;

    /**
     * Console Controller inicializálás, az interakciókhoz.
     */
    private final ConsoleController consoleController = new ConsoleController();
    /**
     * running boolean, ami számon tartja, hogy fut-e a játék mód.
     */
    private boolean running;
    /**
     * Ennyi pont jár, ha kinyírjuk a Wumpust.
     */
    private static final int WUMPUS_KILL_POINT = 5;
    /**
     * Ennyi pont jár a játék befejezéséért.
     */
    private static final int GAME_END_REWARD = 10;


    /**
     * Game objektum konstruktora.
     * @param gHero hős
     * @param gGameBoard pálya
     * @param gPlayer játékos
     */
    public Game(final Hero gHero, final GameBoard gGameBoard, final Player gPlayer) {
        this.hero = gHero;
        this.gameBoard = gGameBoard;
        this.player = gPlayer;

        arrows = hero.getArrows();
        baseRow = hero.getRow();
        baseCol = hero.getColumn();

        playerScore = player.getPlayerScore();


    }

    /**
     * Játék indítása.
     */
    public void start() {
        running = true;
        gameBoard.displayBoard();
        while (running) {
            iranymutatas(hero.getDirection());
            String command = consoleController.askForGameAction();

            switch (command) {
                case "forward" -> {
                    heroTakeStepForward(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                }
                case "backward" -> {
                    heroTakeStepBackward(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                }
                case "left" -> {
                    heroTakeStepLeft(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                }
                case "right" -> {
                    heroTakeStepRight(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                }
                case "direction east" -> {
                    hero.heroChangeDirection('E');
                    playerScore++;
                }
                case "direction west" -> {
                    hero.heroChangeDirection('W');
                    playerScore++;
                }
                case "direction north" -> {
                    hero.heroChangeDirection('N');
                    playerScore++;
                }
                case "direction south" -> {
                    hero.heroChangeDirection('S');
                    playerScore++;
                }
                case "fire" -> fire();
                case "save" -> saveGameState();
                case "exit" -> running = false;
                default -> System.out.println("Nem megfelelő parancs, próbáld újra.");
            }
            gameBoard.displayBoard();
        }
    }

    /**
     * Hős előre lépésének logikája, és ellenőrzése.
     * @param direction irány
     */
    public void heroTakeStepForward(final char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
            }
            case 'S' -> {
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
            }
            case 'W' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
            }
            case 'N' -> {
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
            }
            default -> System.out.println("Nem megfelelő irány.");
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);


    }
    /**
     * Hős hátra lépésének logikája, és ellenőrzése.
     * @param direction irány
     */
    public void heroTakeStepBackward(final char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
            }
            case 'S' -> {
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
            }
            case 'W' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
            }
            case 'N' -> {
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
            }
            default -> System.out.println("Nem megfelelő irány.");
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }
    /**
     * Hős balra lépésének logikája, és ellenőrzése.
     * @param direction irány
     */
    public void heroTakeStepLeft(final char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E' -> {
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
            }
            case 'S' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
            }
            case 'W' -> {
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
            }
            case 'N' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
            }
            default -> System.out.println("Nem megfelelő irány.");
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }
    /**
     * Hős jobbra lépésének logikája, és ellenőrzése.
     * @param direction irány
     */
    public void heroTakeStepRight(final char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E' -> {
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
            }
            case 'S' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
            }
            case 'W' -> {
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
            }
            case 'N' -> {
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
            }
            default -> System.out.println("Nem megfelelő irány.");
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }

    /**
     * Nyíl kilővése.
     */
    public void fire() {
        if (arrows > 0) {
            arrows--;
            hero.setArrows(arrows);
            if (isWumpusInLineOfFire()) {
                System.out.println(Colors.ANSI_PURPLE + "A Wumpus meghalt!" + Colors.ANSI_RESET);
                whereIsTheWumpus();
                gameBoard.setCell(wumpusRow, wumpusCol, gameBoard.getCellFromOriginalBoard(wumpusRow, wumpusCol));
                playerScore += 2;
            } else {
                System.out.println(Colors.ANSI_RED + "Nem találtad el a wumpust." + Colors.ANSI_RESET);
            }
        }
        System.out.println("Nyilaid száma: " + hero.getArrows());
    }

    /**
     * Megnézi, hogy a wumpus irányba van-e, amikor tüzelünk a nyíllal.
     * @return boolean true vagy false.
     */
    public boolean isWumpusInLineOfFire() {
        int heroRow = hero.getRow();
        int heroColumn = hero.getColumn();
        char heroDirection = hero.getDirection();

        switch (heroDirection) {
            case 'N' -> {
                for (int i = heroRow; i >= 0; i--) {
                    if (gameBoard.getBoard()[i][heroColumn] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[i][heroColumn] == 'W') {
                        break;
                    }
                }
            }
            case 'E' -> {
                for (int j = heroColumn; j < gameBoard.getBoard()[heroRow].length; j++) {
                    if (gameBoard.getBoard()[heroRow][j] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[heroRow][j] == 'W') {
                        break;
                    }
                }
            }
            case 'S' -> {
                for (int i = heroRow; i < gameBoard.getBoard().length; i++) {
                    if (gameBoard.getBoard()[i][heroColumn] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[i][heroColumn] == 'W') {
                        break;
                    }
                }
            }
            case 'W' -> {
                for (int j = heroColumn; j >= 0; j--) {
                    if (gameBoard.getBoard()[heroRow][j] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[heroRow][j] == 'W') {
                        break;
                    }
                }
            }
            default -> System.out.println("Nem megfelelő irány.");
        }
        return false;
    }

    private void whereIsTheWumpus() {
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            for (int j = 0; j < gameBoard.getBoard().length; j++) {
                if (gameBoard.getBoard()[i][j] == 'U') {
                    wumpusRow = i;
                    wumpusCol = j;
                }
            }
        }
    }

    private void updateBoardAfterSteps(final char nextCell, final int nextStepRow, final int nextStepCol) {
        switch (nextCell) {
            case 'W' ->
                    System.out.println(Colors.ANSI_YELLOW + "Falba ütköznél, érvénytelen lépés!" + Colors.ANSI_RESET);
            case 'G' -> {
                System.out.println(Colors.ANSI_BLUE + "Gratulálok! Megtaláltad az aranyat!");
                System.out.println("Vidd vissza a kiindulási helyre!" + Colors.ANSI_RESET);
                hero.setHasGold(true);
                playerScore += WUMPUS_KILL_POINT;
                gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                hero.heroToMove(nextStepRow, nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
            }
            case 'U' -> {
                System.out.println(Colors.ANSI_RED + "A Wumpus megtalált, így sajnos meghaltál." + Colors.ANSI_RESET);
                hero.setIsDead(true);
                running = false;
            }
            case 'P' -> {
                System.out.println(Colors.ANSI_YELLOW + "Verembe estél, egy nyilat vesztettél." + Colors.ANSI_RESET);
                gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                hero.heroToMove(nextStepRow, nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                if (arrows > 0) {
                    arrows--;
                    hero.setArrows(arrows);
                }
            }
            case '_' -> {
                System.out.println(Colors.ANSI_YELLOW + "Érvényes lépés!" + Colors.ANSI_RESET);
                gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                hero.heroToMove(nextStepRow, nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
            }
            default -> System.out.println("Nem megfelelő irány.");
        }
    }

    private void isSuccessfulGame() {
        int heroCurrentRow = hero.getRow();
        int heroCurrentCol = hero.getColumn();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        if (hero.getHasGold() && heroCurrentCol == baseCol && heroCurrentRow == baseRow) {
            System.out.println(Colors.ANSI_YELLOW + "Gratulálok, sikeresen teljesítetted a pályát!" + Colors.ANSI_RESET);
            playerScore += GAME_END_REWARD;
            player.setPlayerScore(playerScore);
            dbRepository.saveScoreBoardToDB(player);
            running = false;
        }


    }

    private void saveGameState() {
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();
        String nameOfMap = ConsoleController.askForMapName();
        gameBoard.setMapName(nameOfMap);
        dbRepository.saveGameBoardToDB(gameBoard);
        dbRepository.saveGameBoardDetailsToDB(gameBoard);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.saveToJSON(gameBoard, gameBoard.getMapName());
    }

    /**
     * Kirajzolja az irányokat (Merre van észak, dél, kelet, nyugat).
     * @param direction irány
     */
    public void iranymutatas(final char direction) {
        String defaultColor = Colors.ANSI_RESET;
        String north = "";
        String east = "";
        String west = "";
        String south = "";

        switch (direction) {
            case 'N' -> {
                north = Colors.ANSI_GREEN;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_RESET;
            }
            case 'E' -> {
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_GREEN;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_RESET;
            }
            case 'W' -> {
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_GREEN;
                south = Colors.ANSI_RESET;
            }
            case 'S' -> {
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_GREEN;
            }
            default -> System.out.println("Nem megfelelő irány.");
        }

        System.out.println("    " + north + "N    ");
        System.out.println("    " + north + "↑    ");
        System.out.println(west + "W ← " + defaultColor + "+" + east + " → E");
        System.out.println("    " + south + "↓    ");
        System.out.println("    " + south + "S    " + defaultColor);
    }

}
