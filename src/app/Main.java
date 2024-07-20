package app;

import java.util.*;

import entity.Board;
import entity.Game;
import entity.LetterBag;
import entity.Player;
import entity.TurnManager;
import input_manager.InputManager;
import interface_adapter.GameViewModel;
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
        GameViewModel gameViewModel = new GameViewModel(game);
        View view = new View(new InputManager(game, gameViewModel), gameViewModel);
    }
}
