/**
 * File-ok betöltése a programba.
 */
package nye.progtech.fileUtils;

import nye.progtech.model.GameBoard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileLoader {
    /**
     * Példa pálya, a hős megadott kezdeti sora.
     */
    private static final int INITIAL_HERO_ROW = 3;
    /**
     * Példapálya, a hős megadott kezdeti oszlopa.
     */
    private static final int INITIAL_HERO_COLUMN = 1;
    private FileLoader() {
        throw new UnsupportedOperationException("This is a utility class,"
                + "cannot be instanciated.");
    }

    /**
     * GameBoard objektum feltöltése TXT file-ból.
     * @param fileName file neve
     * @return GameBoard objektum
     * @throws IOException Input Output exception
     */
    public static GameBoard loadBoard(final String fileName) throws IOException {
        File file = new File("worlds" + File.separator + fileName);
        if (!file.exists()) {
            throw new IOException("A file nem található a worlds mappában: " + fileName);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] firstLine = reader.readLine().split(" ");
            int size = Integer.parseInt(firstLine[0]);
            char heroColumn = firstLine[INITIAL_HERO_COLUMN].charAt(0);
            int heroRow = Integer.parseInt(firstLine[2]);
            char heroDirection = firstLine[INITIAL_HERO_ROW].charAt(0);

            char[][] board = new char[size][size];
            char[][] originalBoard = new char[size][size];

            for (int i = 0; i < size; i++) {
                String line = reader.readLine();
                board[i] = line.toCharArray();
                originalBoard[i] = line.toCharArray();
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (originalBoard[i][j] == 'G' || originalBoard[i][j] == 'H' || originalBoard[i][j] == 'U') {
                        originalBoard[i][j] = '_';
                    }
                }
            }

            return new GameBoard(size, board, originalBoard, heroColumn, heroRow, heroDirection, fileName);
        }
    }

    /**
     * File-ok listázása a megadott könyvtárban.
     * @param directoryPath elérési útvonal
     * @return file lista.
     */
    public static List<String> listFilesInDirectory(final String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }


}
