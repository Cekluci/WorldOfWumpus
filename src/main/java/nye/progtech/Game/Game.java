package nye.progtech.Game;

import nye.progtech.Colors;
import nye.progtech.controller.ConsoleController;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.model.Player;

import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

import java.util.Scanner;


public class Game {
    private final Hero hero;
    private final GameBoard gameBoard;
    private final Player player;
    private int wumpusRow;
    private int wumpusCol;

    private int arrows;
    private final int baseRow;
    private final int baseCol;
    private int playerScore;
    private static DBRepositoryInterface dbRepository;
    private final ConsoleController consoleController = new ConsoleController();
    private boolean running;



    public Game(Hero hero, GameBoard gameBoard, Player player) {
        this.hero = hero;
        this.gameBoard = gameBoard;
        this.player = player;

        arrows = hero.getArrows();
        baseRow = hero.getRow();
        baseCol = hero.getColumn();

        playerScore = player.getPlayerScore();


    }

    public void start() {
        running = true;
        gameBoard.displayBoard();
        while (running) {
            iranymutatas(hero.getDirection());
            String command = consoleController.askForGameAction();

            char heroDirection = hero.getDirection();

            switch (command) {
                case "forward":
                    HeroTakeStepForward(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                    break;
                case "backward":
                    HeroTakeStepBackward(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                    break;
                case "left":
                    HeroTakeStepLeft(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                    break;
                case "right":
                    HeroTakeStepRight(hero.getDirection());
                    playerScore++;
                    isSuccessfulGame();
                    break;
                case "direction east":
                    hero.HeroChangeDirection('E');
                    playerScore++;
                    break;
                case "direction west":
                    hero.HeroChangeDirection('W');
                    playerScore++;
                    break;
                case "direction north":
                    hero.HeroChangeDirection('N');
                    playerScore++;
                    break;
                case "direction south":
                    hero.HeroChangeDirection('S');
                    playerScore++;
                    break;
                case "fire":
                    fire();
                    break;
                case "save":
                    saveGameState();
                    break;
                case "exit":
                    running = false;
                    break;
            }
            gameBoard.displayBoard();
        }
    }
    public void HeroTakeStepForward(char direction) {
        int currentRow = hero.getRow();
        int currentCol = hero.getColumn();

        hero.HeroToStepForward(direction);

        int nextStepRow = hero.getRow();
        int nextStepCol = hero.getColumn();
        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);
        gameBoard.setCell(currentRow, currentCol, gameBoard.getCellFromOriginalBoard(currentRow, currentCol));
        gameBoard.setCell(nextStepRow, nextStepCol, 'H');
    }

    public void HeroTakeStepForward_old(char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
                break;
            case 'S':
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
                break;
            case 'W':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
                break;
            case 'N':
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
                break;
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        //updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);


    }

    public void HeroTakeStepBackward(char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
                break;
            case 'S':
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
                break;
            case 'W':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
                break;
            case 'N':
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
                break;
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        //updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }

    public void HeroTakeStepLeft(char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E':
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
                break;
            case 'S':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
                break;
            case 'W':
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
                break;
            case 'N':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
                break;
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        //updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }
    public void HeroTakeStepRight(char direction) {
        int nextStepRow = 0;
        int nextStepCol = 0;

        switch (direction) {
            case 'E':
                nextStepRow = hero.getRow() + 1;
                nextStepCol = hero.getColumn();
                break;
            case 'S':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() - 1;
                break;
            case 'W':
                nextStepRow = hero.getRow() - 1;
                nextStepCol = hero.getColumn();
                break;
            case 'N':
                nextStepRow = hero.getRow();
                nextStepCol = hero.getColumn() + 1;
                break;
        }

        char nextCell = gameBoard.getCell(nextStepRow, nextStepCol);

        //updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol,);

    }

    public void fire() {
        if (arrows > 0) {
            arrows--;
            hero.setArrows(arrows);
            if (isWumpusInLineOfFire()) {
                System.out.println(Colors.ANSI_PURPLE + "A Wumpus meghalt!" + Colors.ANSI_RESET);
                whereIsTheWumpus();
                gameBoard.setCell(wumpusRow, wumpusCol, gameBoard.getCellFromOriginalBoard(wumpusRow, wumpusCol));
                playerScore+= 2;
            } else {
                System.out.println(Colors.ANSI_RED + "Nem találtad el a wumpust." + Colors.ANSI_RESET);
            }
        }
        System.out.println("Nyilaid száma: " + hero.getArrows());
    }


    public boolean isWumpusInLineOfFire() {
        int heroRow = hero.getRow();
        int heroColumn = hero.getColumn();
        char heroDirection = hero.getDirection();

        switch (heroDirection) {
            case 'N':
                for (int i = heroRow; i >= 0; i--) {
                    if (gameBoard.getBoard()[i][heroColumn] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[i][heroColumn] == 'W') {
                        break;
                    }
                }
                break;
            case 'E':
                for (int j = heroColumn;j < gameBoard.getBoard()[heroRow].length; j++) {
                    if (gameBoard.getBoard()[heroRow][j] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[heroRow][j] == 'W') {
                        break;
                    }
                }
                break;
            case 'S':
                for (int i = heroRow; i < gameBoard.getBoard().length; i++) {
                    if (gameBoard.getBoard()[i][heroColumn] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[i][heroColumn] == 'W') {
                        break;
                    }
                }
                break;
            case 'W':
                for (int j = heroColumn; j >= 0; j--) {
                    if (gameBoard.getBoard()[heroRow][j] == 'U') {
                        return true;
                    }
                    if (gameBoard.getBoard()[heroRow][j] == 'W') {
                        break;
                    }
                }
                break;
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

    private void updateBoardAfterSteps(char nextCell, int nextStepRow, int nextStepCol) {
        switch (nextCell) {
            case 'W':
                System.out.println(Colors.ANSI_YELLOW + "Falba ütköznél, érvénytelen lépés!" + Colors.ANSI_RESET);
                break;
            case 'G':
                System.out.println(Colors.ANSI_BLUE + "Gratulálok! Megtaláltad az aranyat!");
                System.out.println("Vidd vissza a kiindulási helyre!" + Colors.ANSI_RESET);
                hero.setHasGold(true);
                playerScore+= 5;
                //gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                //hero.setRow(nextStepRow);
                //hero.setColumn(nextStepCol);
                //gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                break;
            case 'U':
                System.out.println(Colors.ANSI_RED + "A Wumpus megtalált, így sajnos meghaltál." + Colors.ANSI_RESET);
                hero.setIsDead(true);
                running = false;
                break;
            case 'P':
                System.out.println(Colors.ANSI_YELLOW + "Verembe estél, egy nyilat vesztettél." + Colors.ANSI_RESET);
                //gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                //hero.setRow(nextStepRow);
                //hero.setColumn(nextStepCol);
                //gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                if (arrows > 0) {
                    arrows--;
                    hero.setArrows(arrows);
                }
                break;
            case '_':
                System.out.println(Colors.ANSI_YELLOW + "Érvényes lépés!" + Colors.ANSI_RESET);
                //gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                //hero.setRow(nextStepRow);
                //hero.setColumn(nextStepCol);
                //gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                break;
        }
    }

    private void isSuccessfulGame() {
        int heroCurrentRow = hero.getRow();
        int heroCurrentCol = hero.getColumn();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        if (hero.getHasGold() && heroCurrentCol == baseCol && heroCurrentRow == baseRow) {
            System.out.println(Colors.ANSI_YELLOW + "Gratulálok, sikeresen teljesítetted a pályát!" + Colors.ANSI_RESET);
            playerScore+= 10;
            player.setPlayerScore(playerScore);
            dbRepository.saveScoreBoardToDB(player);
            running = false;
        }


    }

    private void saveGameState() {
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();
        String nameOfMap = consoleController.askForMapName();
        gameBoard.setMapName(nameOfMap);
        dbRepository.saveGameBoardToDB(gameBoard);
        dbRepository.saveGameBoardDetailsToDB(gameBoard);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.saveToJSON(gameBoard, gameBoard.getMapName());
    }

    public void iranymutatas (char direction) {
        String defaultColor = Colors.ANSI_RESET;
        String north = "";
        String east = "";
        String west = "";
        String south = "";

        switch (direction) {
            case 'N':
                north = Colors.ANSI_GREEN;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_RESET;
                break;
            case 'E':
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_GREEN;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_RESET;
                break;
            case 'W':
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_GREEN;
                south = Colors.ANSI_RESET;
                break;
            case 'S':
                north = Colors.ANSI_RESET;
                east = Colors.ANSI_RESET;
                west = Colors.ANSI_RESET;
                south = Colors.ANSI_GREEN;
                break;
        }

        System.out.println("    " + north + "N    ");
        System.out.println("    " + north + "↑    ");
        System.out.println(west + "W ← " + defaultColor + "+" + east + " → E");
        System.out.println("    " + south + "↓    ");
        System.out.println("    " + south + "S    " + defaultColor);
    }

}
