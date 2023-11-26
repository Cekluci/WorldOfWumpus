package nye.progtech.util;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
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
