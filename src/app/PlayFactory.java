package app;

import view.PlayView;

public class PlayFactory {
  
  private PlayFactory() {
    // This class should not be instantiated
  }

  public static PlayView createPlayView() {
    return new PlayView();
  }
}
