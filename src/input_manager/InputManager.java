package input_manager;

import controller_factory.PlaceLetterControllerFactory;
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

        if (input.getType().equals("GridInput")){
            handleGridInput(input);
        } else if (input.getType().equals("HandInput")){
            handleHandInput(input);
        } else if (input.getType().equals("ConfirmPlay")){
            System.out.println("Confirm play");
            confirmPlay();
        }
    }

    private void handleGridInput(Input input) {
        if (input.getInput().equals("rclick")) {
            System.out.println("Remove letter");
            removeLetter(input.getX(), input.getY());
        } else if (input.getInput().equals("lclick")) {
            Letter selectedLetter = gameViewModel.getSelectedLetter();
            if (selectedLetter != null) {
                System.out.println("Place letter");
                placeLetter(input.getX(), input.getY(), selectedLetter);
            } else {
                System.out.println("No selected letter");
                return;
            }
        }
    }

    private void handleHandInput(Input input){
        if (input.getInput().equals("lclick")){
            System.out.println("Letter selected");
            Player currentPlayer = gameViewModel.getPlayer();
            gameViewModel.setSelectedLetter(currentPlayer.getInventory().get(input.getPositionInHand()));
        }
    }

    private void removeLetter(int x, int y){
        try{
            Play play = game.getLastPlay();
            Board board = game.getBoard();
            Tile selectedTile = board.getCell(x, y);
            RemoveLetterController controller = RemoveLetterControllerFactory.createRemoveLetterController(gameViewModel);
            controller.execute(x, y, play, selectedTile, board);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void placeLetter(int x, int y, Letter letter){
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
