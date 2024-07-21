package input_manager;

import controller_factory.PlaceLetterControllerFactory;
import entity.*;
import interface_adapter.GameViewModel;
import interface_adapter.confirm_play.ConfirmPlayController;
import interface_adapter.place_letter.PlaceLetterController;
import use_case.get_leaderboard.GetLeaderboardInputBoundary;
import use_case.get_leaderboard.GetLeaderboardInputData;
import view.Input;


public class InputManager {
    private Letter selectedLetter;
    private Game game;
    private PlaceLetterController placeLetterController;
    private ConfirmPlayController confirmPlayController;
    private GetLeaderboardInputBoundary getLeaderboardInputBoundary;

    public InputManager(
        Game game, 
        PlaceLetterController placeLetterController,
        ConfirmPlayController confirmPlayController
    ){
        this.game = game;
        this.placeLetterController = placeLetterController;
        this.confirmPlayController = confirmPlayController;
    }

    public void handleInput(Input input){

        if (input.getType().equals("GridInput")){
            if (input.getInput().equals("rclick")){
                System.out.println("Remove letter");
                //removeLetter(input.getX(), input.getY());
            } else if (input.getInput().equals("lclick")){
                if (selectedLetter != null) {
                    System.out.println("Place letter");
                    placeLetter(input.getX(), input.getY(), selectedLetter);
                } else{
                    System.out.println("No selected letter");
                    return;
                }
            }
        } else if (input.getType().equals("HandInput")){
            if (input.getInput().equals("lclick")){
                System.out.println("Letter selected");
                Player currentPlayer = game.getTurnManager().GetCurrentPlayer();
                selectedLetter = currentPlayer.getInventory().get(input.getPositionInHand());
            }
        } else if (input.getType().equals("ConfirmPlay")){
            System.out.println("Confirm play");
            confirmPlay();
        }
    }

//    private void removeLetter(int x, int y){
//        Play play = game.getLastPlay();
//        Board board = game.getBoard();
//        Tile selectedTile = board.getCell(x, y);
//        RemoveLetterOutputData presenter = new RemoveLetterPresenter();
//        RemoveLetterInteractor interactor = new RemoveLetterInteractor(presenter);
//        RemoveLetterController controller = new RemoveLetterController(interactor);
//        controller.execute(x, y, play, selectedTile, board);
//    }

    private void placeLetter(int x, int y, Letter letter){
        Play play = game.getTurnManager().getCurrentPlay();
        Board board = game.getBoard();
        placeLetterController.execute(x, y, letter, board, play);
    }
    
    private void confirmPlay() {
        Play play = game.getTurnManager().getCurrentPlay();
        Board board = game.getBoard();
        confirmPlayController.execute(play, board);
        getLeaderboardInputBoundary.execute(new GetLeaderboardInputData(game.getPlayers()));
        
    }

    public Game getGame(){
        return game;
    }
    /** methods: useCaseName(relevant data eg: x, y position){
     *              UseCaseNamePresenter useCaseNamePresenter = new useCaseNamePresenter();
     *              useCaseNameInteractor useCaseNameInteractor = new UseCaseNameInteractor(useCaseNamePresenter)
     *              UseCaseNameController useCaseNameController = new useCaseNameController(useCaseNameInteractor);
     *              useCaseNameController.execute(relevant data);
     } */
}
