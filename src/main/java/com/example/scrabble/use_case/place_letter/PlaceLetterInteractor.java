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

/**
 * Handles the logic for placing a letter on the board.
 * Implements the PlaceLetterInputBoundary interface.
 */
@Service
public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  public static final String OCCUPIED = "This grid is occupied!";
  public static final String NO_LETTER = "You don't have the letter in your inventory!";

  private final GameDataAccess gameDao;
//  private final static Logger logger = LoggerFactory.getLogger(PlaceLetterInteractor.class);

  /**
   * Constructs a PlaceLetterInteractor with the specified GameDataAccess.
   *
   * @param gameDao the data access object for game entities
   */
  @Autowired
  public PlaceLetterInteractor(
    GameDataAccess gameDao
  ) {
    this.gameDao = gameDao;
  }

  /**
   * Retrieves the vertical tiles forming a word from the given move.
   *
   * @param move the move to start from
   * @param board the game board
   * @return a list of tiles forming the vertical word, or null if no word is formed
   */
  private List<Tile> getVTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<>();
    // Go to the top of a set of continuous letters.
    while (y >= 0 && !board.getTile(x, y).isEmpty()) y--;
    y++;

    // Go to the end, add tiles to the list along the way.
    while (y < board.getHeight() && !board.getTile(x, y).isEmpty()) {
      tiles.add(board.getTile(x, y++));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  /**
   * Retrieves the horizontal tiles forming a word from the given move.
   *
   * @param move the move to start from
   * @param board the game board
   * @return a list of tiles forming the horizontal word, or null if no word is formed
   */
  private List<Tile> getHTiles(Move move, Board board) {
    int x = move.getX();
    int y = move.getY();
    List<Tile> tiles = new LinkedList<>();
    while (x >= 0 && !board.getTile(x, y).isEmpty()) x--;
    x++;
    while (x < board.getWidth() && !board.getTile(x, y).isEmpty()) {
      tiles.add(board.getTile(x++, y));
    }
    if (tiles.size() <= 1) return null;
    return tiles;
  }

  /**
   * Retrieves all words formed on the board by the given play.
   *
   * @param play the play containing the moves
   * @param board the game board
   * @return a list of lists of tiles, each representing a word
   */
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

    // If the move stands alone, add it to the list of words.
    if (words.isEmpty()) {
      words.add(Arrays.asList(board.getTile(fMove.getX(), fMove.getY())));
    }
    return words;
  }

  /**
   * Converts a list of lists of tiles into a list of words as strings.
   *
   * @param words the list of lists of tiles
   * @return a list of words as strings
   */
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

  /**
   * Calculates the total score for a list of words formed on the board.
   *
   * @param words the list of lists of tiles representing words
   * @return the total score
   */
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

  /**
   * Executes the use case to place a letter on the board.
   * Validates the move, updates the game state, and returns the output data.
   *
   * @param data the input data containing the game ID, coordinates, and letter to place
   * @return the output data containing the updated board, player's inventory, score, and words formed
   */
  @Override
  public PlaceLetterOutputData execute(PlaceLetterInputData data) {
    int x = data.getX();
    int y = data.getY();

    Game game = gameDao.get(data.getGameId());
    Play play = game.getCurrentPlay();
    Player player = game.getCurrentPlayer();
    Board board = game.getBoard();

    if (!board.getTile(x, y).isEmpty()) {
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