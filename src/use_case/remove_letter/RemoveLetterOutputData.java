package use_case.remove_letter;

public class RemoveLetterOutputData {
    private boolean removeSuccessful;
    public RemoveLetterOutputData(boolean removeSuccessful){
        this.removeSuccessful = removeSuccessful;
    }
    public boolean isRemoveSuccessful(){
        return removeSuccessful;
    }
}
