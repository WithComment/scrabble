package app;

import java.util.*;

import controller_factory.ConfirmPlayControllerFactory;
import controller_factory.EndTurnControllerFactory;
import controller_factory.GetLeaderboardControllerFactory;
import controller_factory.PlaceLetterControllerFactory;
import controller_factory.StartTurnControllerFactory;
import entity.Board;
import entity.Game;
import entity.LetterBag;
import entity.Player;
import entity.TurnManager;
import input_manager.InputManager;
import interface_adapter.GameViewModel;
import interface_adapter.confirm_play.ConfirmPlayController;
import interface_adapter.end_turn.EndTurnController;
import interface_adapter.get_leaderboard.GetLeaderboardController;
import interface_adapter.place_letter.PlaceLetterController;
import interface_adapter.start_turn.StartTurnController;
import use_case.EndTurn.StartTurn;
import view.View;

public class Main {
    public static void main(String[] args) {
        int NumOfPlayers = 2;
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < NumOfPlayers; i++) {
            players.add(new Player(i));
        }
        LetterBag letterBag = new LetterBag();
        Game game = new Game(players);

        TurnManager turnManager = game.getTurnManager();
        turnManager.startTurn();
        for (Player player : players) {
            player.addLetter(letterBag.drawLetters(7));
        }

        Board board = game.getBoard();
        GameViewModel gameViewModel = new GameViewModel(board, players);
        PlaceLetterController placeLetterController = PlaceLetterControllerFactory.create(gameViewModel);
        ConfirmPlayController confirmPlayController = ConfirmPlayControllerFactory.create(gameViewModel);
        GetLeaderboardController getLeaderboardController = GetLeaderboardControllerFactory.create(gameViewModel);
        StartTurnController startTurnController = StartTurnControllerFactory.create(gameViewModel);
        EndTurnController endTurnController = EndTurnControllerFactory.create(gameViewModel);
        startTurnController.execute(turnManager);
        View view = new View(new InputManager(game, gameViewModel, placeLetterController, confirmPlayController, getLeaderboardController, endTurnController), gameViewModel);
    }
}