package test.use_case.redraw_letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Letter;
import entity.LetterBag;
import entity.Player;
import use_case.redraw_letters.RedrawInputData;
import use_case.redraw_letters.RedrawInteractor;
import use_case.redraw_letters.RedrawOutputBoundary;
import use_case.redraw_letters.RedrawOutputData;


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
        LetterBag letterBag = new LetterBag();
        Player player = new Player(0);

        player.addLetter(letterBag.drawLetters(7));
    }

    @Test
    public void testInteractorFail() {
        RedrawOutputBoundary failurePresenter = new RedrawOutputBoundary() {
            @Override
            public void prepareSuccessView(RedrawOutputData redrawOutputData) {
                fail("Should not have successed");
            }

            @Override
            public void prepareFailedView(String error) {
                assertEquals("There are less than 7 letters in the bag", error);
            }
        };
        RedrawInteractor redrawInteractor = new RedrawInteractor(failurePresenter);
        assertEquals(91, letterBag.getLength());

        ArrayList<Letter> removedLetters = letterBag.drawLetters(90);
        assertEquals(91, letterBag.getLength());

        RedrawInputData redrawInputData = new RedrawInputData(new ArrayList<>(), player, letterBag);
        redrawInteractor.execute(redrawInputData);
    }
}
