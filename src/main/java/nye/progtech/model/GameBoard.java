/**
 * Gameboard model.
 */
package nye.progtech.model;

import nye.progtech.Colors;

public class GameBoard {
    /**
     * Gameboard mérete.
     */
    private int size;

    /**
     * gamboard matrix.
     */
    private char[][] board;

    /**
     * üres gameboard matrix, az alap
     * pályaelemekkel.
     */
    private char[][] originalBoard;
    /**
     * Hero objektum.
     */
    private Hero hero;
    /**
     * Pálya neve.
     */
    private String mapName;
    /**
     * Az a maximális méret, amikor még nem kell igazítani
     * a pályaelemek közötti szóközöket,
     * mert minden sorszám 1 karakterből áll.
     */
    private static final int BOARD_MAX_ALIGNMENT = 9;

    /**
     * GameBoard konstruktor.
     *
     * @param gbSize pálya mérete
     * @param gbBoard pálya matrix
     * @param gbOriginalBoard üres pálya matrix
     * @param gbHeroColumn hős oszlopa
     * @param gbHeroRow hős sora
     * @param gbHeroDirection hős iránya
     * @param gbMapName pálya neve
     */
    public GameBoard(final int gbSize, final char[][] gbBoard,
                     final char[][] gbOriginalBoard,
                     final char gbHeroColumn, final int gbHeroRow,
                     final char gbHeroDirection, final String gbMapName) {
        this.size = gbSize;
        this.board = gbBoard;
        this.originalBoard = gbOriginalBoard;
        this.hero = new Hero(gbHeroColumn, gbHeroRow,
                        gbHeroDirection, calculateArrows());
        this.mapName = gbMapName;
        placeHero();
    }

    /**
     * GameBoard üres konstruktor.
     */
    public GameBoard() {

    }

    /**
     * Getter pálya méretéhez.
     *
     * @return pálya mérete
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter pálya méretéhez.
     *
     * @param setSize pálya mérete
     */
    public void setSize(final int setSize) {
        this.size = setSize;
    }

    /**
     * Getter a pálya matrixhoz.
     *
     * @return pálya matrix
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Setter a pálya matrixhoz.
     *
     * @param setBoard palya matrix
     */
    public void setBoard(final char[][] setBoard) {
        this.board = setBoard;
    }

    /**
     * Getter az üres palya matrixhoz.
     *
     * @return üres pálya matrix
     */
    public char[][] getOriginalBoard() {
        return originalBoard;
    }

    /**
     * Setter az üres pálya matrixhoz.
     *
     * @param setOriginalBoard üres pálya matrix
     */
    public void setOriginalBoard(final char[][] setOriginalBoard) {
        this.originalBoard = setOriginalBoard;
    }

    /**
     * Getter a Hős objektumhoz.
     *
     * @return hős
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Setter a Hős objektumhoz.
     *
     * @param setHero hős
     */
    public void setHero(final Hero setHero) {
        this.hero = setHero;
    }

    /**
     * Getter a pálya nevéhez.
     *
     * @return pálya neve
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Setter a pálya nevéhez.
     *
     * @param setMapName pálya neve
     */
    public void setMapName(final String setMapName) {
        this.mapName = setMapName;
    }

    /**
     * Hős elhelyezése a pályán.
     */
    private void placeHero() {
        this.board[hero.getRow()][hero.getColumn()] = 'H';
    }

    /**
     * Nyilak számának meghatározása.
     *
     * @return nyilak száma
     */
    private int calculateArrows() {
        int wumpusCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 'U') {
                    wumpusCount++;
                }
            }
        }
        return wumpusCount;
    }

    /**
     * Egy adott mező tartalmának kigyűjtése.
     *
     * @param row sor
     * @param col oszlop
     * @return mező tartalma
     */
    public char getCell(final int row, final int col) {
        if (row >= 0 && row < board.length && col >= 0 && col < board.length) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException("Érvénytelen cella index: ("
                    + row
                    + ", "
                    + col
                    + ".");
        }
    }

    /**
     * Egy adott mező tartalmának kinyerése
     * az üres pálya matrixból.
     *
     * @param row sor
     * @param col oszlop
     * @return üres pályamező tartalma
     */
    public char getCellFromOriginalBoard(final int row, final int col) {
        if (row >= 0
                && row < originalBoard.length
                && col >= 0
                && col < originalBoard.length) {
            return originalBoard[row][col];
        } else {
            throw new IllegalArgumentException("Érvénytelen cella index: ("
                    + row
                    + ", "
                    + col
                    + ".");
        }
    }

    /**
     * Pálya mező tartalmának megadása.
     *
     * @param row sor
     * @param col oszlop
     * @param cellItem mező tartalma
     */
    public void setCell(final int row, final int col, final char cellItem) {
        if (row >= 0 && row < board.length && col >= 0 && col < board.length) {
            board[row][col] = cellItem;
        } else {
            throw new IllegalArgumentException("Érvénytelen cella index: ("
                    + row
                    + ", "
                    + col
                    + ".");
        }
    }

    /**
     * Pálya kirajzolása a konzolra.
     */
    public void displayBoard() {
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
            if (i < BOARD_MAX_ALIGNMENT) {
                System.out.print(" ");
            }

            for (int j = 0; j < size; j++) {
                if (board[i][j] == 'W') {
                    System.out.print(Colors.ANSI_PURPLE
                            + board[i][j]
                            + " "
                            + Colors.ANSI_RESET);
                } else if (board[i][j] == 'H') {
                    System.out.print(Colors.ANSI_BLUE
                            + board[i][j]
                            + " "
                            + Colors.ANSI_RESET);
                } else if (board[i][j] == 'P') {
                    System.out.print(Colors.ANSI_CYAN
                            + board[i][j]
                            + " "
                            + Colors.ANSI_RESET);
                } else if (board[i][j] == 'G') {
                    System.out.print(Colors.ANSI_YELLOW
                            + board[i][j]
                            + " "
                            + Colors.ANSI_RESET);
                } else if (board[i][j] == 'U') {
                    System.out.print(Colors.ANSI_RED
                            + board[i][j]
                            + " "
                            + Colors.ANSI_RESET);
                } else {
                    System.out.print(board[i][j] + " ");
                }

            }
            System.out.println();
        }
    }

    /**
     * Kezdeti, üres pálya létrehozása.
     *
     * @param cebSize méret
     * @return pálya matrix
     */
    public char[][] createEmptyBoard(final int cebSize) {
        char[][] emptyBoard = new char[cebSize][cebSize];
        for (int i = 0; i < cebSize; i++) {
            for (int j = 0; j < cebSize; j++) {
                if (i == 0 || j == 0 || i == cebSize - 1 || j == cebSize - 1) {
                    emptyBoard[i][j] = 'W';
                } else {
                    emptyBoard[i][j] = '_';
                }
            }
        }
        return emptyBoard;
    }


}
