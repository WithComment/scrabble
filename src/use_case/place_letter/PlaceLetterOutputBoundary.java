package use_case.place_letter;

public interface PlaceLetterOutputBoundary {
  void prepareSuccessView(PlaceLetterOutputData data);
  void prepareFailView(String error);
}
