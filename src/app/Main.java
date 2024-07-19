package app;

import javax.swing.*;

import entity.Board;
import entity.Game;
import input_manager.InputManager;
import interface_adapter.GameViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import view.View;
import view.ViewManager;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Board board = game.getBoard();
        ViewModel viewModel = new GameViewModel("game", game);
        View view = new View(new InputManager(game, viewModel));
    }
}
