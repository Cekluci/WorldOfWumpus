package nye.progtech.Game;

import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;

import java.util.Scanner;

public class MapEditor {

    private Hero hero;
    private GameBoard gameBoard;

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
        //GameBoard newGameBoard = new GameBoard();

        //while (running) {
            System.out.println("Tábla mérete: ");
            int size = scanner.nextInt();
            System.out.println("Hős sora: ");
            int heroRow = scanner.nextInt();
            System.out.println("Hős oszlopa: ");
            int heroCol = scanner.nextInt();
            char heroColC = (char) (heroCol + 64);
            System.out.println("Hős iránya (N/E/W/S): ");
            char heroDir = scanner.next().charAt(0);
            scanner.nextLine();
            System.out.println("Pálya neve: ");
            String mapName = scanner.nextLine();

            char[][] newBoard = createEmptyBoard(size);

            GameBoard newGameBoard = new GameBoard(size, newBoard, newBoard, heroColC, heroRow, heroDir, mapName);


        //}
        newGameBoard.displayBoard();
        System.out.println("-----szerkesztés után-----");
        editGameBoard(newGameBoard);
    }

    public void editGameBoard(GameBoard gameBoard) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add meg a pályaelemet a következő formátumban: [add|delete], sorszám, oszlopszám, pályaelem");
        System.out.println("Pályaelem lehet a következő: U - Wumpus, P - Verem, G - Arany");

        String input = scanner.nextLine();

        String[] parts = input.split(",");
        String command = parts[0].trim();
        int row = Integer.parseInt(parts[1].trim());
        int col = Integer.parseInt(parts[2].trim());
        char content = parts[3].trim().charAt(0);

        switch (command) {
            case "add":
                if (row - 1 == 0 || col - 1 == 0) {
                    System.out.println("Nem rakhatsz ide semmit, ez itt fal.");
                } else if (gameBoard.getCell(row - 1, col - 1) != '_') {
                    System.out.println("Ez a mező már foglalt!");
                } else {
                    gameBoard.setCell(row - 1,col - 1, content);
                }
                break;
            case "delete":
                if (row - 1 == 0 || col - 1 == 0) {
                    System.out.println("Nem törölhetsz falat!");
                } else if (gameBoard.getCell(row - 1, col - 1) == '_') {
                    System.out.println("Ez a mező már üres!");
                } else if (gameBoard.getCell(row - 1, col - 1) == 'H') {
                    System.out.println("A Hőst nem törölheted a pályáról!");
                } else {
                    gameBoard.setCell(row - 1,col - 1, '_');
                }
        }
        gameBoard.displayBoard();
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
