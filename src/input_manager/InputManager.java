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


public class InputManager {
    private Game game;
    private PlaceLetterController placeLetterController;
    private ConfirmPlayController confirmPlayController;
    private GetLeaderboardController getLeaderboardController;
    private EndTurnController endTurnController;
    private GameViewModel gameViewModel;

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

    private void handleHandInput(Input input){
        if (input.getInput().equals("lclick")){
            System.out.println("Letter selected");
            int currentPlayer = gameViewModel.getPlayer();
            gameViewModel.setSelectedLetter(game.getPlayerInventory(currentPlayer).get(input.getPositionInHand()).getLetter());
        }
    }

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

    private void placeLetter(int x, int y, Character letter){
        Play play = gameViewModel.getPlay();
        Board board = gameViewModel.getBoard();
        placeLetterController.execute(x, y, letter, board, play);
    }
    
    private void confirmPlay() {
        Play play = gameViewModel.getPlay();
        Board board = gameViewModel.getBoard();
        confirmPlayController.execute(play, board);
        getLeaderboardController.execute(gameViewModel.getLeaderboard());
        endTurnController.execute(false, game);
    }
}
