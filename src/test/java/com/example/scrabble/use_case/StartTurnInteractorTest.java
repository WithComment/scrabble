//package com.example.scrabble.use_case;
//
//import com.example.scrabble.data_access.GameDataAccess;
//import com.example.scrabble.use_case.start_turn.StartTurnInteractor;
//import com.example.scrabble.use_case.start_turn.StartTurnInputData;
//import com.example.scrabble.use_case.start_turn.StartTurnOutputData;
//import com.example.scrabble.entity.Game;
//import com.example.scrabble.entity.Player;
//import com.example.scrabble.entity.Play;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class StartTurnInteractorTest {
//
//    private StartTurnInteractor startTurnInteractor;
//    private GameDataAccess gameDao;
//    private Game mockGame;
//    private Player mockPlayer;
//    private Play mockPlay;
//
//    @BeforeEach
//    public void setUp() {
//        gameDao = mock(GameDataAccess.class);
//        startTurnInteractor = new StartTurnInteractor(gameDao);
//        mockGame = mock(Game.class);
//        mockPlayer = mock(Player.class);
//        mockPlay = mock(Play.class);
//
//        when(gameDao.get(anyInt())).thenReturn(mockGame);
//    }
//
//    @Test
//    public void testExecute_StartsTurnSuccessfully() throws IOException, ClassNotFoundException {
//        // Arrange
//        StartTurnInputData inputData = new StartTurnInputData(1);
//
//        when(mockGame.getCurrentPlayer()).thenReturn(mockPlayer);
//        when(mockGame.getCurrentPlay()).thenReturn(mockPlay);
//        doNothing().when(mockGame).startTurn();
//
//        // Act
//        StartTurnOutputData outputData = startTurnInteractor.execute(inputData);
//
//        // Assert
//        verify(gameDao).get(inputData.getGameId());
//        verify(mockGame).startTurn();
//        assertEquals(mockPlayer, outputData.getPlayer());
//        assertEquals(mockPlay, outputData.getPlay());
//    }
//
//    @Test
//    public void testExecute_GameNotFound_ThrowsException() throws IOException, ClassNotFoundException {
//        // Arrange
//        StartTurnInputData inputData = new StartTurnInputData(1);
//
//        when(gameDao.get(anyInt())).thenReturn(null);
//
//        // Act & Assert
//        assertThrows(NullPointerException.class, () -> {
//            startTurnInteractor.execute(inputData);
//        });
//
//        verify(gameDao).get(inputData.getGameId());
//        verify(mockGame, never()).startTurn();
//    }
//
//    @Test
//    public void testExecute_StartTurnIOException() throws IOException, ClassNotFoundException {
//        // Arrange
//        StartTurnInputData inputData = new StartTurnInputData(1);
//        when(mockGame.getCurrentPlayer()).thenReturn(mockPlayer);
//        when(mockGame.getCurrentPlay()).thenReturn(mockPlay);
//        doThrow(IOException.class).when(mockGame).startTurn();
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> {
//            startTurnInteractor.execute(inputData);
//        });
//
//        verify(gameDao).get(inputData.getGameId());
//        verify(mockGame).startTurn();
//    }
//
//    @Test
//    public void testExecute_StartTurnClassNotFoundException() throws IOException, ClassNotFoundException {
//        // Arrange
//        StartTurnInputData inputData = new StartTurnInputData(1);
//        when(mockGame.getCurrentPlayer()).thenReturn(mockPlayer);
//        when(mockGame.getCurrentPlay()).thenReturn(mockPlay);
//        doThrow(ClassNotFoundException.class).when(mockGame).startTurn();
//
//        // Act & Assert
//        assertThrows(ClassNotFoundException.class, () -> {
//            startTurnInteractor.execute(inputData);
//        });
//
//        verify(gameDao).get(inputData.getGameId());
//        verify(mockGame).startTurn();
//    }
//}
