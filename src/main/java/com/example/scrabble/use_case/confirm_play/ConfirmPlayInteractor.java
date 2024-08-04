package com.example.scrabble.use_case.confirm_play;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Play;
import com.example.scrabble.use_case.InvalidPlayException;


/**
 * Usecase interactor for confirming a play.
 * Responsible for checking if a play is valid and updating the player's score.
 * *
 * <p>
 * Constants:
 * <ul>
 * <li>{@code INLINE_MSG} - Message indicating that all letters must be in-line.
 * <li>{@code CONTINUOUS_MSG} - Message indicating that the.
 * <li>{@code CENTER_MSG} - Message indicating that the first word must cover
 * the center.
 * </ul>
 */
@Service
public class ConfirmPlayInteractor implements ConfirmPlayInputBoundary {

  private final GameDataAccess gameDao;
  public static final String INLINE_MSG = "All letters must be in-line.";
  public static final String CONTINUOUS_MSG = "The letters must be continuous.";
  public static final String CENTER_MSG = "The first word must cover the center.";
  public static final String CONNECTED_MSG = "The word must be connected to another word.";

  @Autowired
  public ConfirmPlayInteractor(GameDataAccess gameDao) {
    this.gameDao = gameDao;
  }

  private boolean isNotInline(List<Move> moves) {
    Move fMove = moves.getFirst();
    Move lMove = moves.getLast();

    int x, y;
    boolean inVLine, inHLine;
    for (Move move : moves) {
      x = move.getX();
      y = move.getY();
      // If a move has the same x or y as the first and last moves, it is in-line.
      inVLine = x == fMove.getX() && x == lMove.getX();
      inHLine = y == fMove.getY() && y == lMove.getY();
      if (!(inVLine || inHLine)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the letters form only one main word.
   * @param moves list of moves
   * @param board the game board
   * @return true if the letters form one main word, false otherwise.
   */
  private boolean hasGap(Play play, Board board) {
    List<Move> moves = play.getMoves();
    Move fMove = moves.get(0);
    int start, end;
    if (play.isVertical()) {
      int x = fMove.getX();
      start = fMove.getY();
      end = fMove.getY();

      // Since the moves can be in any order, we need to find the start and end y values.
      for (Move move : moves) {
        start = Integer.min(start, move.getY());
        end = Integer.max(end, move.getY());
      }

      for (int i = start; i <= end; i++) {
        if (board.getCell(x, i).isEmpty()) {
          return true;
        }
      }
    } else {
      int y = fMove.getY();
      start = fMove.getX();
      end = fMove.getX();
      for (Move move : moves) {
        start = Integer.min(start, move.getX());
        end = Integer.max(end, move.getX());
      }
      for (int i = start; i <= end; i++) {
        if (board.getCell(i, y).isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isFirstPlay(Board board) {
    for (int i = 0; i < board.getWidth(); i++) {
      for (int j = 0; j < board.getHeight(); j++) {
        if (board.isConfirmed(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isNotCenter(List<Move> moves) {
    for (Move move : moves) {
      if (move.getX() == 7 && move.getY() == 7) {
        return false;
      }
    }
    return true;
  }

  private boolean isIsolated(List<Move> moves, Board board) {
    int x, y;
    for (Move move : moves) {
      x = move.getX();
      y = move.getY();
      if (x > 0 && board.isConfirmed(x - 1, y)) return false;
      if (x < board.getWidth() - 1 && board.isConfirmed(x + 1, y)) return false;
      if (y > 0 && board.isConfirmed(x, y - 1)) return false;
      if (y < board.getHeight() - 1 && board.isConfirmed(x, y + 1)) return false;
    }
    return true;
  }

  @Override
  public ConfirmPlayOutputData execute(ConfirmPlayInputData data) {

    Game game = gameDao.get(data.getGameId());
    Board board = game.getBoard();
    Play play = game.getCurrentPlay();
    List<Move> moves = play.getMoves();
 
    if (moves.isEmpty()) {
      return new ConfirmPlayOutputData(true);
    }
    
    if (moves.size() > 1) {
      if (isNotInline(moves)) {
        throw new InvalidPlayException(INLINE_MSG);
      }

      if (hasGap(play, board)) {
        throw new InvalidPlayException(CONTINUOUS_MSG);
      }
    }

    if (isFirstPlay(board)) {
      if (isNotCenter(moves)) {
        throw new InvalidPlayException(CENTER_MSG);
      }
    } else if (isIsolated(moves, board)) {
      throw new InvalidPlayException(CONNECTED_MSG);
    }

    return new ConfirmPlayOutputData(true);
  }
}
