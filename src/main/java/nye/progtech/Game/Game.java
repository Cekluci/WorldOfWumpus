package nye.progtech.Game;

import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;

import java.util.Scanner;

public class Game {
    private Hero hero;
    private GameBoard gameBoard;

    public Game(Hero hero, GameBoard gameBoard) {
        this.hero = hero;
        this.gameBoard = gameBoard;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Az alábbi parancsok közül választhatsz, mindig vedd figyelembe, ");
            System.out.println("hogy az irányok az éppen aktuális irány alapján kerülnek végrehajtásra!");
            System.out.println("forward: Előre lépés.");
            System.out.println("backward: Hátra lépés.");
            System.out.println("left: Balra lépés.");
            System.out.println("right: Jobbra lépés");
            System.out.println("Adj meg egy parancsot: ");
            String command = scanner.nextLine();

            char heroDirection = hero.getDirection();

            switch (command) {
                case "forward":
//                    System.out.println("Hero Direction: " + hero.getDirection());
//                    System.out.println("Hero rowIndex: " + hero.getRow() + ", colIndex: " + hero.getColumn());
                    HeroTakeStepForward(hero.getDirection());
                    break;
                case "backward":
                    HeroTakeStepBackward(hero.getDirection());
                    break;
                case "left":
                    HeroTakeStepLeft(hero.getDirection());
                    break;
                case "right":
                    HeroTakeStepRight(hero.getDirection());
                    break;
            }
            gameBoard.displayBoard();
        }
        scanner.close();
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

        switch (nextCell) {
            case 'W':
                System.out.println("Falba ütköznél, érvénytelen lépés!");
                break;
            case 'G':
                System.out.println("Gratulálok! Megtaláltad az aranyat!");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), '_');
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                //Arany felvétele metódus
                break;
            case 'U':
                System.out.println("A Wumpus megtalált, így sajnos meghaltál.");
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

        switch (nextCell) {
            case 'W':
                System.out.println("Falba ütköznél, érvénytelen lépés!");
                break;
            case 'G':
                System.out.println("Gratulálok! Megtaláltad az aranyat!");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), '_');
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                //Arany felvétele metódus
                break;
            case 'U':
                System.out.println("A Wumpus megtalált, így sajnos meghaltál.");
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

        switch (nextCell) {
            case 'W':
                System.out.println("Falba ütköznél, érvénytelen lépés!");
                break;
            case 'G':
                System.out.println("Gratulálok! Megtaláltad az aranyat!");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), '_');
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                //Arany felvétele metódus
                break;
            case 'U':
                System.out.println("A Wumpus megtalált, így sajnos meghaltál.");
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

        switch (nextCell) {
            case 'W':
                System.out.println("Falba ütköznél, érvénytelen lépés!");
                break;
            case 'G':
                System.out.println("Gratulálok! Megtaláltad az aranyat!");
                gameBoard.setCell(hero.getRow(), hero.getColumn(), '_');
                hero.setRow(nextStepRow);
                hero.setColumn(nextStepCol);
                gameBoard.setCell(nextStepRow, nextStepCol, 'H');
                //Arany felvétele metódus
                break;
            case 'U':
                System.out.println("A Wumpus megtalált, így sajnos meghaltál.");
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
}
