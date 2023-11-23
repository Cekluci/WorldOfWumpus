package nye.progtech;

import java.io.*;

public class FileLoader {
    public static GameBoard loadBoard(String fileName) throws IOException {
        InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream("worlds" + fileName);
        if (inputStream == null) {
            throw new IOException("A file nem található a worlds mappában: " + fileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] firstLine = reader.readLine().split(" ");
            int size = Integer.parseInt(firstLine[0]);
            char heroColumn = firstLine[1].charAt(0);
            int heroRow = Integer.parseInt(firstLine[2]);
            char heroDirection = firstLine[3].charAt(0);

            char[][] board = new char[size][size];

            for (int i = 0; i < size;i++) {
                String line = reader.readLine();
                board[i] = line.toCharArray();
            }

            return new GameBoard(size, board, heroColumn, heroRow, heroDirection);
        }
    }
}
