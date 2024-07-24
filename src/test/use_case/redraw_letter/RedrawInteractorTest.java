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

import static org.junit.jupiter.api.Assertions.*;

public class RedrawInteractorTest {
    private LetterBag letterBag;
    private Player player;
    private RedrawInteractor redrawInteractor;

    @Test
    public void testInteractorFail() {
        RedrawOutputBoundary failurePresenter = new RedrawOutputBoundary() {
            @Override
            public void prepareSuccessView(RedrawOutputData redrawOutputData) {
                fail("Should not have successed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("There are less than 7 letters in the bag", error);
            }
        };

        LetterBag letterBag = new LetterBag();
        Player player = new Player(0);

        player.addLetter(letterBag.drawLetters(7));

        RedrawInteractor redrawInteractor = new RedrawInteractor(failurePresenter);

        assertEquals(91, letterBag.getLength());

        letterBag.drawLetters(90);
        assertEquals(1, letterBag.getLength());

        RedrawInputData redrawInputData = new RedrawInputData(new ArrayList<>(), player, letterBag);
        redrawInteractor.execute(redrawInputData);
    }

    @Test
    public void testInteractorSuccess() {
        RedrawOutputBoundary successPresenter = new RedrawOutputBoundary() {
            @Override
            public void prepareSuccessView(RedrawOutputData redrawOutputData) {
                assertEquals(true, redrawOutputData.isDrawSuccessful());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not have failed");
            }
        };

        LetterBag letterBag = new LetterBag();
        Player player = new Player(0);

        player.addLetter(letterBag.drawLetters(7));

        RedrawInteractor redrawInteractor = new RedrawInteractor(successPresenter);

        assertEquals(91, letterBag.getLength());

        ArrayList<Letter> inventoryBefore = (ArrayList<Letter>) player.getInventory().clone();

        RedrawInputData redrawInputData = new RedrawInputData(player.getInventory(), player, letterBag);
        redrawInteractor.execute(redrawInputData);

        assertNotEquals(inventoryBefore, player.getInventory());
    }
}
