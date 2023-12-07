import nye.progtech.fileutils.FileLoader;
import nye.progtech.model.GameBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileLoaderTest {

    @Test
    void testLoadBoard() throws IOException {
        GameBoard gameBoard = FileLoader.loadBoard("testWorld.txt");
        assertNotNull(gameBoard);
    }

    @Test
    void testLoadBoardThrowIOExceptionWhenFileIsMissing() {
        assertThrows(IOException.class, () -> {
            FileLoader.loadBoard("NotExisting.txt");
        });
    }

    @Test
    void testListFilesInDirectory(@TempDir Path tempDir) throws IOException {
        File newFile1 = tempDir.resolve("testFile1.txt").toFile();
        assertTrue(newFile1.createNewFile());
        File newFile2 = tempDir.resolve("testFile2.txt").toFile();
        assertTrue(newFile2.createNewFile());

        List<String> fileNames = FileLoader.listFilesInDirectory(tempDir.toString());
        assertTrue(fileNames.contains("testFile1.txt"));
        assertTrue(fileNames.contains("testFile2.txt"));
        assertEquals(2, fileNames.size());
    }

    @Test
    void testListFilesInEmptyDirectoryShouldReturnEmptyList(@TempDir Path tempDir) {
        List<String> fileNames = FileLoader.listFilesInDirectory(tempDir.toString());
        assertTrue(fileNames.isEmpty());
    }

    @Test
    void testListFilesInNonExistingDirectoryShouldReturnEmptyList() {
        List<String> fileNames = FileLoader.listFilesInDirectory("nonExistingDir");
        assertTrue(fileNames.isEmpty());
    }
}
