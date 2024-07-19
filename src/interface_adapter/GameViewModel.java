package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import entity.Game;

public class GameViewModel extends ViewModel {
      
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private Game game;

  public GameViewModel(String viewName, Game game) {
    super(viewName);
    this.game = game;
  }
  
  @Override
  public void firePropertyChanged() {
    support.firePropertyChange("Game", null, this.game);
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public Game getGame() {
    return game;
  }
  
  public void setGame(Game game) {
    this.game = game;
    firePropertyChanged();
  }
}
