package nye.progtech;

public class GameBoard {
    private int size;
    private char[][] board;
    private Hero hero;

    public GameBoard(int size, char[][] board, char heroColumn, int heroRow, char heroDirection) {
        this.size = size;
        this.board = board;
        this.hero = new Hero(heroColumn, heroRow, heroDirection, calculateArrows());
        placeHero();
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

    public void displayBoard_orig() {
        //Sorok
        System.out.println("  ");
        for (int i = 0; i < size; i++) {
            System.out.println((char)('A' + i) + " ");
        }
        //System.out.println(); //Új sor

        for (int i = 0; i < size; i++) {
            //Sorok száma
            //Oszlopok
            System.out.print(" ");
            System.out.print((i + 1));
            if (i < 9) {
                System.out.print(" ");
            }

            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " "); //GabeBoard aktuális mezőjének kiírása
            }
            System.out.print(" ");
        }
    }

    public void displayBoard_new() {
        //Sorok
        System.out.println("  ");
        for (int i = 0; i < size; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; i < size; i++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        //System.out.println(); //Új sor

//        for (int i = 0; i < size; i++) {
//            //Sorok száma
//            //Oszlopok
//            System.out.print(" ");
//            System.out.print((i + 1));
//            if (i < 9) {
//                System.out.print(" ");
//            }
//
//            for (int j = 0; j < size; j++) {
//                System.out.print(board[i][j] + " "); //GabeBoard aktuális mezőjének kiírása
//            }
//            System.out.print(" ");
//        }
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
