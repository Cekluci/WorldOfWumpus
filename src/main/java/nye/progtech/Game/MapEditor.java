package nye.progtech.Game;

import nye.progtech.controller.ConsoleController;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.model.GameBoard;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

public class MapEditor {

    private final GameBoard gameBoard = new GameBoard();
    private final ConsoleController consoleController = new ConsoleController();
    DBRepositoryInterface dbRepository = new DBRepositoryImpl();
    JSONHandler jsonHandler = new JSONHandler();

    public MapEditor() {
    }


    public void startEditor() {

        System.out.println("Üdvözöllek a pályaszerkesztőben!");

        int size = ConsoleController.askForBoardSize();
        int heroRow = ConsoleController.askForHeroRow();
        char heroColC = ConsoleController.askForHeroColumn();
        char heroDir = ConsoleController.askForHeroDirection();
        String mapName = ConsoleController.askForMapName();

        char[][] newBoard = gameBoard.createEmptyBoard(size);

        GameBoard newGameBoard = new GameBoard(size, newBoard, newBoard, heroColC, heroRow, heroDir, mapName);

        newGameBoard.displayBoard();
        System.out.println("-----szerkesztés után-----");
        editGameBoard(newGameBoard);
    }

    public void editGameBoard(GameBoard gameBoard) {
        int wumpusCount = 0;
        int goldCount = 0;
        while (true) {
            String input = ConsoleController.askForEditorInput();

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
                        if (gameBoard.getSize() <= 8 && wumpusCount > 1 ){
                            System.out.println("Nem lehet 1-nél több Wumpus a pályán!");
                        } else if (gameBoard.getSize() >= 9 && gameBoard.getSize() <= 14 && wumpusCount > 2) {
                            System.out.println("Nem lehet 2-nél több Wumpus a pályán!");
                        } else if (gameBoard.getSize() > 14 && wumpusCount > 3) {
                            System.out.println("Nem lehet 3-nál több Wumpus a pályán!");
                        } else {
                            if (goldCount > 1) {
                                System.out.println("Nem lehet 1-nél több arany a pályán!");
                            } else {
                                gameBoard.setCell(row - 1, colInt - 1, content);
                            }
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
        dbRepository.saveGameBoardToDB(gameBoard);
        dbRepository.saveGameBoardDetailsToDB(gameBoard);

        jsonHandler.saveToJSON(gameBoard, gameBoard.getMapName());

        System.out.println("A tábla mentve az adatbázisba.");
    }

}
