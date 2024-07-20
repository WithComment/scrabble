package app;

import java.util.LinkedList;

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
        Player player1 = new Player();
        Player player2 = new Player();
        LetterBag letterBag = new LetterBag();
        Game game = new Game(new LinkedList<Player>() {
            {
                add(player1);
                add(player2);
            }
        });

        TurnManager turnManager = game.getTurnManager();
        turnManager.startTurn();
        player1.addLetter(letterBag.drawLetters(7));
        player2.addLetter(letterBag.drawLetters(7));

        Board board = game.getBoard();
        GameViewModel gameViewModel = new GameViewModel(game);
        View view = new View(new InputManager(game, gameViewModel), gameViewModel);
    }
}
