import nye.progtech.controller.ConsoleController;
import nye.progtech.game.Game;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.model.Player;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class gameTest {

    @Test
    void testHeroTakeStepForwardWhenFacingEast() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepForward('E');

        verify(mockHero).heroToMove(1, 2);
    }

    @Test
    void testHeroTakeStepBackwardWhenFacingEast() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepBackward('E');

        verify(mockHero).heroToMove(1, 0);
    }

    @Test
    void testHeroTakeStepLeftWhenFacingEast() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepLeft('E');

        verify(mockHero).heroToMove(0, 1);
    }

    @Test
    void testHeroTakeStepRightWhenFacingEast() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepRight('E');

        verify(mockHero).heroToMove(2, 1);
    }

    @Test
    void testHeroTakeStepForwardWhenFacingSouth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepForward('S');

        verify(mockHero).heroToMove(2, 1);
    }

    @Test
    void testHeroTakeStepBackwardWhenFacingSouth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepBackward('S');

        verify(mockHero).heroToMove(0, 1);
    }

    @Test
    void testHeroTakeStepLeftWhenFacingSouth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepLeft('S');

        verify(mockHero).heroToMove(1, 2);
    }

    @Test
    void testHeroTakeStepRightWhenFacingSouth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepRight('S');

        verify(mockHero).heroToMove(1, 0);
    }

    @Test
    void testHeroTakeStepForwardWhenFacingWest() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepForward('W');

        verify(mockHero).heroToMove(1, 0);
    }

    @Test
    void testHeroTakeStepBackwardWhenFacingWest() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepBackward('W');

        verify(mockHero).heroToMove(1, 2);
    }

    @Test
    void testHeroTakeStepLeftWhenFacingWest() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepLeft('W');

        verify(mockHero).heroToMove(2, 1);
    }

    @Test
    void testHeroTakeStepRightWhenFacingWest() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepRight('W');

        verify(mockHero).heroToMove(0, 1);
    }

    @Test
    void testHeroTakeStepForwardWhenFacingNorth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepForward('N');

        verify(mockHero).heroToMove(0, 1);
    }

    @Test
    void testHeroTakeStepBackwardWhenFacingNorth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepBackward('N');

        verify(mockHero).heroToMove(2, 1);
    }

    @Test
    void testHeroTakeStepLeftWhenFacingNorth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepLeft('N');

        verify(mockHero).heroToMove(1, 0);
    }

    @Test
    void testHeroTakeStepRightWhenFacingNorth() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockGameBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        Game game = new Game(mockHero, mockGameBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);

        when(mockGameBoard.getCell(anyInt(), anyInt())).thenReturn('_');

        game.heroTakeStepRight('N');

        verify(mockHero).heroToMove(1, 2);
    }

    @Test
    void testIsWumpusInLineOfFire() {
        Hero mockHero = Mockito.mock(Hero.class);
        GameBoard mockBoard = Mockito.mock(GameBoard.class);
        Player mockPlayer = Mockito.mock(Player.class);
        char[][] mockBoardChar = new char[3][3];
        Game game = new Game(mockHero, mockBoard, mockPlayer);

        when(mockHero.getRow()).thenReturn(1);
        when(mockHero.getColumn()).thenReturn(1);
        when(mockHero.getDirection()).thenReturn('N');

        mockBoardChar[0][1] = 'U';
        when(mockBoard.getBoard()).thenReturn(mockBoardChar);

        boolean result = game.isWumpusInLineOfFire();
        assertTrue(result);
    }
}
