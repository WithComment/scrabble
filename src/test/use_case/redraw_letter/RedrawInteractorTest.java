package test.use_case.redraw_letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Letter;
import entity.LetterBag;
import entity.Player;
import use_case.redraw_letters.RedrawInteractor;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RedrawInteractorTest {
    private LetterBag letterBag;
    private Player player;
    private RedrawInteractor redrawInteractor;

    @BeforeEach
    public void setUp() {
        letterBag = new LetterBag();
        player = new Player(0);
        redrawInteractor = new RedrawInteractor();

        // Initialize player's hand with some letters
        ArrayList<Letter> initialLetters = new ArrayList<>();
        initialLetters.add(new Letter('A', 1));
        initialLetters.add(new Letter('B', 3));
        initialLetters.add(new Letter('C', 3));
        player.addLetters(initialLetters);
    }

    @Test
    public void testRedrawLetters() {
        List<Character> lettersToRedraw = new ArrayList<>();
        lettersToRedraw.add('A');
        lettersToRedraw.add('B');

        int initialBagSize = letterBag.getLength();
        int initialPlayerHandSize = player.getHandSize();

        redrawInteractor.redrawLetters(lettersToRedraw);

        // Check that the player's hand still has the same number of letters
        assertEquals(initialPlayerHandSize, player.getHandSize());

        // Check that the letters A and B have been replaced
        boolean containsA = player.getHand().stream().anyMatch(letter -> letter.getLetter() == 'A');
        boolean containsB = player.getHand().stream().anyMatch(letter -> letter.getLetter() == 'B');
        assertTrue(!containsA && !containsB);

        // Check that the bag size is updated correctly
        assertEquals(initialBagSize, letterBag.getLength() + 2);
    }
}
