package nye.progtech.fileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nye.progtech.model.GameBoard;

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
