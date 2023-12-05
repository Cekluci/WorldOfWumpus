package nye.progtech.Game;

import nye.progtech.controller.ConsoleController;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;

import java.util.Scanner;

public class MapEditor {

    private Hero hero;
    private GameBoard gameBoard;
    private Scanner scanner = new Scanner(System.in);
    private ConsoleController consoleController = new ConsoleController();

    public MapEditor(Hero hero, GameBoard gameBoard) {
        this.hero = hero;
        this.gameBoard = gameBoard;

    }

    public MapEditor() {
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void startEditor() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Üdvözöllek a pályaszerkesztőben!");

        //while (running) {
            int size = consoleController.askForBoardSize();
            int heroRow = consoleController.askForHeroRow();
            char heroColC = consoleController.askForHeroColumn();
            char heroDir = consoleController.askForHeroDirection();
            String mapName = consoleController.askForMapName();

            char[][] newBoard = createEmptyBoard(size);

            GameBoard newGameBoard = new GameBoard(size, newBoard, newBoard, heroColC, heroRow, heroDir, mapName);


        //}
        newGameBoard.displayBoard();
        System.out.println("-----szerkesztés után-----");
        editGameBoard(newGameBoard);
    }

    public void editGameBoard(GameBoard gameBoard) {
        int wumpusCount = 0;
        int goldCount = 0;
        while (true) {
            String input = consoleController.askForEditorInput();

            if ("kilép".equalsIgnoreCase(input)) {
                System.out.println("Kilépés a szerkesztőből.");
                break;
            }

            String[] parts = input.split(",");
            String command = parts[0].trim();
            int row = Integer.parseInt(parts[1].trim());
            char col = parts[2].trim().charAt(0);
            int colInt = Character.toUpperCase(col) - 64;
            char content = parts[3].trim().charAt(0);

            if (content == 'G') {
                goldCount++;
            } else if (content == 'U') {
                wumpusCount++;
            }

            switch (command) {
                case "add":
                    if (row - 1 == 0 || colInt - 1 == 0) {
                        System.out.println("Nem rakhatsz ide semmit, ez itt fal.");
                    } else if (row - 1 > gameBoard.getSize() || colInt - 1 > gameBoard.getSize()) {
                        System.out.println("A pálya határain kívül vagy.");
                    } else if (gameBoard.getCell(row - 1, colInt - 1) != '_') {
                        System.out.println("Ez a mező már foglalt!");
                    } else {
                        if (wumpusCount > 1 ){
                            System.out.println("Nem lehet 1-nél több Wumpus a pályán!");
                        } else if (goldCount > 1) {
                            System.out.println("Nem lehet 1-nél több aranya a pályán!");
                        } else {
                            gameBoard.setCell(row - 1, colInt - 1, content);
                        }
                    }
                    break;
                case "delete":
                    if (row - 1 == 0 || colInt - 1 == 0) {
                        System.out.println("Nem törölhetsz falat!");
                    } else if (gameBoard.getCell(row - 1, colInt - 1) == '_') {
                        System.out.println("Ez a mező már üres!");
                    } else if (gameBoard.getCell(row - 1, colInt - 1) == 'H') {
                        System.out.println("A Hőst nem törölheted a pályáról!");
                    } else {
                        gameBoard.setCell(row - 1,colInt - 1, '_');
                    }
            }
            gameBoard.displayBoard();

        }
        //consoleController.closeScanner();
    }

    public char[][] createEmptyBoard(int size) {
        char[][] emptyBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == 5 || j == 5) {
                    emptyBoard[i][j] = 'W';
                } else {
                    emptyBoard[i][j] = '_';
                }
            }
        }
        return emptyBoard;
    }
}
