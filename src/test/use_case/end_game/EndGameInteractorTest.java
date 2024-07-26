package test.use_case.end_game;

import entity.Game;
import entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.end_game.EndGameInputData;
import use_case.end_game.EndGameInteractor;
import use_case.end_game.EndGameOutputBoundary;
import use_case.end_game.EndGameOutputData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class EndGameInteractorTest {

    @Test
    public void testInteractorFail(){
        EndGameOutputBoundary failurePresenter = new EndGameOutputBoundary() {
            @Override
            public void prepareSuccessView(EndGameOutputData endGameOutputData) {
                fail("Should not have successed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("The game should not be over yet", error);
            }
        };
        EndGameInteractor endGameInteractor = new EndGameInteractor(failurePresenter);

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(1));
        players.add(new Player(2));
        players.add(new Player(3));
        Game game = new Game(players);
        for (Player player : game.getPlayers()) {
            player.addLetter(game.getLetterBag().drawLetters(7));
        }

        EndGameInputData endGameInputData = new EndGameInputData(game);
        endGameInteractor.execute(endGameInputData);
    }

    @Test void testInteractorSuccess(){
        ArrayList<Player> expectedWinners = new ArrayList<>();
        EndGameOutputBoundary successPresenter = new EndGameOutputBoundary() {
            @Override
            public void prepareSuccessView(EndGameOutputData endGameOutputData) {
                assertEquals(expectedWinners, endGameOutputData.getWinners());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not have failed");
            }
        };
        EndGameInteractor endGameInteractor = new EndGameInteractor(successPresenter);

        ArrayList<Player> players = new ArrayList<>();
        Player player0 = new Player(0);
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        Player player3 = new Player(3);
        players.add(player0);
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Game game = new Game(players);
        player0.addLetter(game.getLetterBag().drawLetters(7));
        player1.addLetter(game.getLetterBag().drawLetters(7));
        player2.addLetter(game.getLetterBag().drawLetters(7));

        player0.addTempScore(0);
        player0.addTempScore(0);
        player0.addTempScore(0);
        player3.addTempScore(100);


        expectedWinners.add(player3);

        EndGameInputData endGameInputData = new EndGameInputData(game);
        endGameInteractor.execute(endGameInputData);
    }
}




