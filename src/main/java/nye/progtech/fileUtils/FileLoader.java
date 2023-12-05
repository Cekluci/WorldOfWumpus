package nye.progtech.fileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import nye.progtech.model.GameBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++) {
                    if (originalBoard[i][j] == 'G' || originalBoard[i][j] == 'H') {
                        originalBoard[i][j] = '_';
                    }
                }
            }

            return new GameBoard(size, board, originalBoard, heroColumn, heroRow, heroDirection, fileName);
        }
    }

    public static List<String> listFilesInDirectory(String directoryPath) {
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
