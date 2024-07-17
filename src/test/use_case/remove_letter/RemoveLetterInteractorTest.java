package test.use_case.remove_letter;

import entity.Board;
import entity.Play;
import entity.Player;
import org.junit.jupiter.api.BeforeEach;

class RemoveLetterInteractorTest {
    public Player player;
    public Board board;
    public Play play;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        board = new Board();
        play = new Play(player);
    }

    @org.junit.jupiter.api.Test
    void execute() {
    }
}
