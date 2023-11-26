package nye.progtech.model;

public class GameBoard {
    private int size;
    private char[][] board;
    private Hero hero;
    private String mapName;

    public GameBoard(int size, char[][] board, char heroColumn, int heroRow, char heroDirection, String mapName) {
        this.size = size;
        this.board = board;
        this.hero = new Hero(heroColumn, heroRow, heroDirection, calculateArrows());
        this.mapName = mapName;
        placeHero();
    }

    public GameBoard() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    private void placeHero() {
        this.board[hero.getRow()][hero.getColumn()] = 'H';
    }

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

    public char getCell(int row, int col) {
        if (row >= 0 && row < board.length && col >= 0 && col < board.length) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException("Érvénytelen cella index: (" + row + ", " + col + ".");
        }
    }

    public void displayBoard() {
        // Fejléc sorok számának kiírása
        System.out.print("   "); // Három szóköz a sorszámozás előtt
        for (int i = 0; i < size; i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println(); // Új sor a tábla tetején

        for (int i = 0; i < size; i++) {
            // Sorok számának kiírása
            System.out.print((i + 1) + " ");
            if (i < 9) {
                System.out.print(" ");
            }

            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " "); // Tábla aktuális mezőjének kiírása
            }
            System.out.println(); // Új sor minden sor után
        }
    }
}
