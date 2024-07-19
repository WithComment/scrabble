package input_manager;

import entity.*;
import interface_adapter.ViewModel;
import interface_adapter.remove_piece.RemoveLetterController;
import use_case.remove_letter.*;
import view.Input;
import view.View;


public class InputManager {
    private Letter selectedLetter;
    private Game game;
    private ViewModel viewModel;

    public InputManager(Game game, ViewModel viewModel){
        this.game = game;
        this.viewModel = viewModel;
    }
    public void handleInput(Input input){
        if (input.getType().equals("GridInput")){
            if (input.getInput().equals("rclick")){
                System.out.println("Remove letter");
                removeLetter(input.getX(), input.getY());
            } else if (input.getInput().equals("lclick")){
                if (selectedLetter != null) {
                    System.out.println("Place letter");
                    //PLACE LETTER GOES HERE
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
        }
    }

    private void removeLetter(int x, int y){
        Play play = game.getLastPlay();
        Board board = game.getBoard();
        Tile selectedTile = board.getCell(x, y);
        RemoveLetterOutputData presenter = new RemoveLetterPresenter();
        RemoveLetterInteractor interactor = new RemoveLetterInteractor(presenter);
        RemoveLetterController controller = new RemoveLetterController(interactor);
        controller.execute(x, y, play, selectedTile, board);
    }
    /** methods: useCaseName(relevant data eg: x, y position){
     *              UseCaseNamePresenter useCaseNamePresenter = new useCaseNamePresenter();
     *              useCaseNameInteractor useCaseNameInteractor = new UseCaseNameInteractor(useCaseNamePresenter)
     *              UseCaseNameController useCaseNameController = new useCaseNameController(useCaseNameInteractor);
     *              useCaseNameController.execute(relevant data);
     } */
}
