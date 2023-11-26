package nye.progtech.repository;

import nye.progtech.model.GameBoard;

public interface LoaderInterface {

    GameBoard loadBoardFromFile(String fileName);
}
