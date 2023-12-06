package nye.progtech.fileUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nye.progtech.model.GameBoard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JSONHandler {
    public JSONHandler() {
    }

    public void saveToJSON(GameBoard gameBoard, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        String filePath = "Json/" + fileName + ".json";

        try (FileWriter writer = new FileWriter(filePath)) {
            mapper.writeValue(writer, gameBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameBoard loadGameBoardFromJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File("Json" + File.separator + fileName));

            int size = rootNode.path("size").asInt();
            String[] boardString = mapper.convertValue(rootNode.path("board"), String[].class);
            String[] originalBoardString = mapper.convertValue(rootNode.path("originalBoard"), String[].class);
            char[][] boardCharArray = convertToCharArray(boardString);
            char[][] originalBoardCharArray = convertToCharArray(originalBoardString);
            String mapName = mapper.convertValue(rootNode.path("mapName"), String.class);

            for (int i = 0; i < originalBoardCharArray.length; i++) {
                for (int j = 0; j < originalBoardCharArray.length; j++) {
                    if (originalBoardCharArray[i][j] == 'U' || originalBoardCharArray[i][j] == 'H' || originalBoardCharArray[i][j] == 'G') {
                        originalBoardCharArray[i][j] = '_';
                    }
                }
            }

            JsonNode heroNode = rootNode.path("hero");
            char heroColumn = (char) (heroNode.path("column").asInt() + 65);
            int heroRow = heroNode.path("row").asInt() + 1;
            char heroDirection = heroNode.path("direction").asText().charAt(0);

            return new GameBoard(size, boardCharArray, originalBoardCharArray, heroColumn, heroRow, heroDirection, mapName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static char[][] convertToCharArray(String[] stringArray) {
        char[][] charArray = new char[stringArray.length][];
        for (int i = 0; i < stringArray.length; i++) {
            charArray[i] = stringArray[i].toCharArray();
        }
        return charArray;
    }

    public void saveToXML(GameBoard gameBoard, String fileName) {
        XmlMapper xmlMapper = new XmlMapper();
        String filePath = "xml/" + fileName + ".xml";

        try (FileWriter writer = new FileWriter(filePath)) {
            xmlMapper.writeValue(writer, gameBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
