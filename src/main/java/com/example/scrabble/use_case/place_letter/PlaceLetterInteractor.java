package com.example.scrabble.use_case.place_letter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Tile;
import com.example.scrabble.use_case.InvalidPlayException;

@Service
public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  public static final String OCCUPIED = "This grid is occupied!";
  public static final String NO_LETTER = "You don't have the letter in your inventory!";

  private final GameDataAccess gameDao;
  private final static Logger logger = LoggerFactory.getLogger(PlaceLetterInteractor.class);

  @Autowired
  public PlaceLetterInteractor(
    GameDataAccess gameDao
  ) {
    this.gameDao = gameDao;
  }

  private List<Tile> getVTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<>();
    // Go to the top of a set of continuous letters.
    while (y >= 0 && !board.getCell(x, y).isEmpty()) y--;
    y++;

    // Go to the end, add tiles to the list along the way.
    while (y < board.getHeight() && !board.getCell(x, y).isEmpty()) {
      tiles.add(board.getCell(x, y++));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  private List<Tile> getHTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<>();
    while (x >= 0 && !board.getCell(x, y).isEmpty()) x--;
    x++;
    while (x < board.getWidth() && !board.getCell(x, y).isEmpty()) {
      tiles.add(board.getCell(x++, y));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  private List<List<Tile>> getWordsOnTiles(Play play, Board board) {
    List<Move> moves = play.getMoves();
    List<List<Tile>> words = new LinkedList<>();
    Move fMove = moves.get(0);
    List<Tile> word;
    if (play.isVertical()) {
      word = getVTiles(fMove, board);
      if (word != null) {
        words.add(word);
      }
      for (Move move : moves) {
        word = getHTiles(move, board);
        if (word != null) {
          words.add(word);
        }
      }
    } else {
      word = getHTiles(fMove, board);
      if (word != null) {
        words.add(word);
      }
      for (Move move : moves) {
        word = getVTiles(move, board);
        if (word != null) {
          words.add(word);
        }
      }
    }

    // If the move standsalone, add it to the list of words.
    if (words.isEmpty()) {
      words.add(Arrays.asList(board.getCell(fMove.getX(), fMove.getY())));
    }
    return words;
  }

  private List<String> getWords(List<List<Tile>> words) {
    List<String> wordStrings = new LinkedList<>();
    StringBuilder word;
    for (List<Tile> tiles : words) {
      word = new StringBuilder();
      for (Tile tile : tiles) {
        word.append(tile.getLetter().getLetter());
      }
      wordStrings.add(word.toString());
    }
    return wordStrings;
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
  public PlaceLetterOutputData execute(PlaceLetterInputData data) {
    int x = data.getX();
    int y = data.getY();

    Game game = gameDao.get(data.getGameId());
    Play play = game.getCurrentPlay();
    Player player = game.getCurrentPlayer();
    Board board = game.getBoard();

    if (!board.getCell(x, y).isEmpty()) {
      throw new InvalidPlayException(OCCUPIED);
    }

    Letter letter = player.removeLetter(data.getLetter());
    if (letter == null) {
      throw new InvalidPlayException(NO_LETTER);
    }
    play.addMove(new Move(x, y, letter));
    board.setCell(x, y, letter);

    List<Move> moves = play.getMoves();
    List<List<Tile>> wordsOnTiles = getWordsOnTiles(play, board);
    List<String> words = getWords(wordsOnTiles);
    play.setWords(words);

    int score = calcScore(wordsOnTiles);
    if (moves.size() >= 7) {
      score += 50;
    }
    player.setTempScore(score);
    gameDao.update(game);
    return new PlaceLetterOutputData(
      board,
      player.getInventory(),
      score,
      words
    );
  }
}
