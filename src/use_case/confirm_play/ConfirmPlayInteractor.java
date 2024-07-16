package use_case.confirm_play;

import java.util.LinkedList;
import java.util.List;

import entity.Board;
import entity.Letter;
import entity.Move;
import entity.Play;
import entity.Player;
import entity.Tile;

public class ConfirmPlayInteractor implements ConfirmPlayInputBoundary {

  private final ConfirmPlayOutputBoundary presenter;
  public static final String INLINE_MSG = "All letters must be in-line.";
  public static final String CONTINUOUS_MSG = "The letters must be continuous.";
  public static final String CENTER_MSG = "The first word must cover the center.";

  public ConfirmPlayInteractor(ConfirmPlayOutputBoundary presenter) {
    this.presenter = presenter;
  }

  private boolean isNotInline(List<Move> moves) {
    Move fMove = moves.get(0);
    Move lMove = moves.get(moves.size() - 1);
    int fX = fMove.getX();
    int fY = fMove.getY();
    int lX = lMove.getX();
    int lY = lMove.getY();

    int x, y;
    boolean inVLine, inHLine;
    for (Move move : moves) {
      x = move.getX();
      y = move.getY();
      inVLine = lX == fX && x == lX;
      inHLine = lY == fY && y == lY;
      if (!(inVLine || inHLine)) {
        return true;
      }
    }
    return false;
  }

  private boolean isVertical(List<Move> moves) {
    return moves.get(0).getX() == moves.get(moves.size() - 1).getX();
  }

  private boolean hasGap(List<Move> moves, Board board) {
    int n = moves.size();
    Move fMove = moves.get(0);
    int start, end;
    if (isVertical(moves)) {
      int x = fMove.getX();
      start = fMove.getY();
      end = fMove.getY();
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

  private boolean isNotCenter(List<Move> moves) {
    for (Move move : moves) {
      if (move.getX() == 7 && move.getY() == 7) {
        return false;
      }
    }
    return true;
  }

  private List<Tile> getVTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<Tile>();
    while (y >= 0 && !board.getCell(x, y).isEmpty()) y--;
    y++;
    while (y < board.getHeight() && !board.getCell(x, y).isEmpty()) {
      tiles.add(board.getCell(x, y++));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  private List<Tile> getHTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<Tile>();
    while (x >= 0 && !board.getCell(x, y).isEmpty()) x--;
    x++;
    while (x < board.getWidth() && !board.getCell(x, y).isEmpty()) {
      tiles.add(board.getCell(x++, y));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  private List<List<Tile>> getWords(List<Move> moves, Board board, boolean isVert) {
    List<List<Tile>> words = new LinkedList<>();
    Move fMove = moves.get(0);
    List<Tile> word;
    if (isVert) {
      words.add(getVTiles(fMove, board));
      for (Move move : moves) {
        word = getHTiles(move, board);
        if (word != null) {
          words.add(word);
        }
      }
    } else {
      words.add(getHTiles(fMove, board));
      for (Move move : moves) {
        word = getHTiles(move, board);
        if (word != null) {
          words.add(word);
        }
      }
    }
    return words;
  }

  private int calcScore(List<List<Tile>> words) {
    int total = 0;
    int wordScore, wordMult;
    for (List<Tile> word : words) {
      wordScore = 0;
      wordMult = 1;
      for (Tile tile : word) {
        wordScore += tile.getLetterMult() * tile.getLetter().getPoints();
        wordMult *= tile.getWordMult();
      }
      total += wordScore * wordMult;
    }
    return total;
  }

  @Override
  public void execute(ConfirmPlayInputData data) {

    Play play = data.getPlay();
    Board board = data.getBoard();
    List<Move> moves = play.getMoves();
    Player player = play.getPlayer();
    boolean isVert = false;

    if (moves.isEmpty()) {
      presenter.prepareSuccessView(new ConfirmPlayOutputData(board, player));
      return;
    }
    
    if (moves.size() > 1) {
      if (isNotInline(moves)) {
        presenter.prepareFailView(INLINE_MSG);
        return;
      }
      isVert = isVertical(moves);

      if (hasGap(moves, board)) {
        presenter.prepareFailView(CONTINUOUS_MSG);
        return;
      }
    }

    if (data.isFirstPlay() && isNotCenter(moves)) {
      presenter.prepareFailView(CENTER_MSG);
      return;
    }

    player.addScore(calcScore(getWords(moves, board, isVert)));
    if (moves.size() >= 7) {
      player.addScore(50);
    }
    presenter.prepareSuccessView(new ConfirmPlayOutputData(board, player));
  }
}
