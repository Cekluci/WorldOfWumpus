package nye.progtech.Game;

import nye.progtech.controller.ConsoleController;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.model.Player;

import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

import java.util.Scanner;


public class Game {
    private Hero hero;
    private GameBoard gameBoard;
    private Player player;
    private int wumpusRow;
    private int wumpusCol;

    private int arrows;
    private int baseRow;
    private int baseCol;
    private int playerScore;
    private static DBRepositoryInterface dbRepository;
    private ConsoleController consoleController = new ConsoleController();
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
        while (running) {
            iranymutatas(hero.getDirection());
            gameBoard.displayBoard();
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
                    HeroChangeDirection('E');
                    playerScore++;
                    break;
                case "direction west":
                    HeroChangeDirection('W');
                    playerScore++;
                    break;
                case "direction north":
                    HeroChangeDirection('N');
                    playerScore++;
                    break;
                case "direction south":
                    HeroChangeDirection('S');
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

    public void HeroChangeDirection(char newDir) {
        char currentDirection = hero.getDirection();
        if (currentDirection == newDir) {
            System.out.println("Már ebben az irányban állsz.");
        } else {
            hero.setDirection(newDir);
        }
    }

    public void HeroTakeStepForward(char direction) {
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

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);


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

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

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

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

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

        updateBoardAfterSteps(nextCell, nextStepRow, nextStepCol);

    }

    public void fire() {
        if (arrows > 0) {
            arrows--;
            hero.setArrows(arrows);
            if (isWumpusInLineOfFire()) {
                System.out.println("A Wumpus meghalt!");
                whereIsTheWumpus();
                gameBoard.setCell(wumpusRow, wumpusCol, '_');
                playerScore+= 2;
            } else {
                System.out.println("Nem találtad el a wumpust.");
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
                System.out.println("Falba ütköznél, érvénytelen lépés!");
                break;
            case 'G':
                System.out.println("Gratulálok! Megtaláltad az aranyat!");
                System.out.println("Vidd vissza a kiindulási helyre!");
                hero.setHasGold(true);
                playerScore+= 5;
                gameBoard.setCell(hero.getRow(), hero.getColumn(), '_');
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                break;
            case 'U':
                System.out.println("A Wumpus megtalált, így sajnos meghaltál.");
                playerScore -= 2;
                hero.setIsDead(false);
                player.setPlayerScore(playerScore);
                dbRepository.saveScoreBoardToDB(player);
                running = false;

                break;
            case 'P':
                System.out.println("Verembe estél, egy nyilat vesztettél.");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                break;
            case '_':
                System.out.println("Érvényes lépés!");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), gameBoard.getCellFromOriginalBoard(hero.getRow(), hero.getColumn()));
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                break;
        }
    }

    private void isSuccessfulGame() {
        int heroCurrentRow = hero.getRow();
        int heroCurrentCol = hero.getColumn();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        if (hero.getHasGold() && heroCurrentCol == baseCol && heroCurrentRow == baseRow) {
            System.out.println("Gratulálok, sikeresen teljesítetted a pályát!");
            playerScore+= 10;
            player.setPlayerScore(playerScore);
            dbRepository.saveScoreBoardToDB(player);
            running = false;
        }


    }

    private void saveGameState() {
        dbRepository.saveGameBoardToDB(gameBoard);
        dbRepository.saveGameBoardDetailsToDB(gameBoard);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.saveToJSON(gameBoard, gameBoard.getMapName() + ".json");
    }

    public void iranymutatas (char direction) {
        String defaultColor = "\u001B[0m";
        String north = "";
        String east = "";
        String west = "";
        String south = "";

        switch (direction) {
            case 'N':
                north = "\u001B[32m";
                east = "\u001B[0m";
                west = "\u001B[0m";
                south = "\u001B[0m";
                break;
            case 'E':
                north = "\u001B[0m";
                east = "\u001B[32m";
                west = "\u001B[0m";
                south = "\u001B[0m";
                break;
            case 'W':
                north = "\u001B[0m";
                east = "\u001B[0m";
                west = "\u001B[32m";
                south = "\u001B[0m";
                break;
            case 'S':
                north = "\u001B[0m";
                east = "\u001B[0m";
                west = "\u001B[0m";
                south = "\u001B[32m";
                break;
        }

        System.out.println("    " + north + "N    ");
        System.out.println("    " + north + "↑    ");
        System.out.println(west + "W ← " + defaultColor + "+" + east + " → E");
        System.out.println("    " + south + "↓    ");
        System.out.println("    " + south + "S    " + defaultColor);
    }

}
