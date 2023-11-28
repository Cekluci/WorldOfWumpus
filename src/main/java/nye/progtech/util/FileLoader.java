package nye.progtech.util;

import nye.progtech.model.GameBoard;

import java.io.*;

public class FileLoader {
    public static GameBoard loadBoard(String fileName) throws IOException {
        File file = new File("worlds" + File.separator + fileName);
        if (!file.exists()) {
            throw new IOException("A file nem található a worlds mappában: " + fileName);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] firstLine = reader.readLine().split(" ");
            int size = Integer.parseInt(firstLine[0]);
//            System.out.println("size: " + size);
            char heroColumn = firstLine[1].charAt(0);
//            System.out.println("heroColumn: " + heroColumn);
            int heroRow = Integer.parseInt(firstLine[2]);
//            System.out.println("heroRow: " + heroRow);
            char heroDirection = firstLine[3].charAt(0);
//            System.out.println("heroDirection: " + heroDirection);

            char[][] board = new char[size][size];
            char[][] originalBoard = new char[size][size];

            for (int i = 0; i < size;i++) {
                String line = reader.readLine();
                board[i] = line.toCharArray();
                originalBoard[i] = line.toCharArray();
            }

            //Állítsuk át a Hős pozícióját alaphelyzetbe az originalBoardon
            originalBoard[4][1] = '_';

            return new GameBoard(size, board, originalBoard, heroColumn, heroRow, heroDirection, fileName);
        }
    }
}
