package input_manager;

import entity.Letter;
import view.Input;
import entity.Game;
import entity.Player;

public class InputManager {
    private Letter selectedLetter;
    private Game game;

    public InputManager(Game game){
        this.game = game;
    }
    public void handleInput(Input input){
        if (input.getType().equals("GridInput")){
            if (input.getInput().equals("rclick")){
                System.out.println("Remove letter");
                //RemoveLetter Controller here
            } else if (input.getInput().equals("lclick")){
                if (selectedLetter != null) {
                    System.out.println("Place letter");
                    //PlaceLetter Controller use the selectedLetter Letter variable
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
    /** methods: useCaseName(relevant data eg: x, y position){
     *              UseCaseNameController useCaseNameController = new useCaseNameController(useCaseNameInteractor);
     *              useCaseNameController.execute(relevant data);
     } */
}
