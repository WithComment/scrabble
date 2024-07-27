package input_manager;

import controller_factory.RemoveLetterControllerFactory;
import entity.*;
import interface_adapter.GameViewModel;
import interface_adapter.confirm_play.ConfirmPlayController;
import interface_adapter.end_turn.EndTurnController;
import interface_adapter.get_leaderboard.GetLeaderboardController;
import interface_adapter.place_letter.PlaceLetterController;
import interface_adapter.remove_piece.RemoveLetterController;
import view.Input;

/**
 * Manages the input handling for the game.
 * Processes different types of inputs and delegates actions to the appropriate controllers.
 */
public class InputManager {
    private Game game;
    private PlaceLetterController placeLetterController;
    private ConfirmPlayController confirmPlayController;
    private GetLeaderboardController getLeaderboardController;
    private EndTurnController endTurnController;
    private GameViewModel gameViewModel;

    /**
     * Constructs an InputManager with the specified game, view model, and controllers.
     * @param game The game instance.
     * @param gameViewModel The view model for the game.
     * @param placeLetterController The controller for placing letters.
     * @param confirmPlayController The controller for confirming plays.
     * @param getLeaderboardController The controller for getting the leaderboard.
     * @param endTurnController The controller for ending turns.
     */
    public InputManager(
        Game game,
        GameViewModel gameViewModel,
        PlaceLetterController placeLetterController,
        ConfirmPlayController confirmPlayController,
        GetLeaderboardController getLeaderboardController,
        EndTurnController endTurnController
    ){
        this.game = game;
        this.gameViewModel = gameViewModel;
        this.placeLetterController = placeLetterController;
        this.confirmPlayController = confirmPlayController;
        this.getLeaderboardController = getLeaderboardController;
        this.endTurnController = endTurnController;
    }

    /**
     * Handles the input based on its type.
     * @param input The input to handle.
     */
    public void handleInput(Input input){

        switch (input.getType()) {
            case "GridInput":
                handleGridInput(input);
                break;
            case "HandInput":
                handleHandInput(input);
                break;
            case "ConfirmPlay":
                System.out.println("Confirm play");
                confirmPlay();
                break;
        }
    }

    /**
     * Handles grid input actions such as placing or removing letters.
     * @param input The grid input to handle.
     */
    private void handleGridInput(Input input) {
        if (input.getInput().equals("rclick")) {
            System.out.println("Remove letter");
            removeLetter(input.getX(), input.getY());
        } else if (input.getInput().equals("lclick")) {
            Character selectedLetter = gameViewModel.getSelectedLetter();
            if (selectedLetter != null) {
                System.out.println("Place letter");
                placeLetter(input.getX(), input.getY(), selectedLetter);
            } else {
                System.out.println("No selected letter");
            }
        }
    }

    /**
     * Handles hand input actions such as selecting a letter.
     * @param input The hand input to handle.
     */
    private void handleHandInput(Input input){
        if (input.getInput().equals("lclick")){
            System.out.println("Letter selected");
            int currentPlayer = gameViewModel.getPlayer();
            gameViewModel.setSelectedLetter(game.getPlayerInventory(currentPlayer).get(input.getPositionInHand()).getLetter());
        }
    }

    /**
     * Removes a letter from the board at the specified coordinates.
     * @param x The x-coordinate of the letter to remove.
     * @param y The y-coordinate of the letter to remove.
     */
    private void removeLetter(int x, int y){
        try{
            Play play = game.getLastPlay();
            Board board = game.getBoard();
            RemoveLetterController controller = RemoveLetterControllerFactory.createRemoveLetterController(gameViewModel);
            controller.execute(x, y, play, board);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Places a letter on the board at the specified coordinates.
     * @param x The x-coordinate to place the letter.
     * @param y The y-coordinate to place the letter.
     * @param letter The letter to place.
     */
    private void placeLetter(int x, int y, Character letter){
        Play play = gameViewModel.getPlay();
        Board board = gameViewModel.getBoard();
        placeLetterController.execute(x, y, letter, board, play);
    }

    /**
     * Confirms the current play and updates the leaderboard and turn.
     */
    private void confirmPlay() {
        Play play = gameViewModel.getPlay();
        Board board = gameViewModel.getBoard();
        confirmPlayController.execute(play, board);
        getLeaderboardController.execute(gameViewModel.getLeaderboard());
        endTurnController.execute(false, game);
    }
}
