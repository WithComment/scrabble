package com.example.scrabble.use_case.redraw_letters;

import com.example.scrabble.entity.Game;

import java.io.IOException;

public interface RedrawInputBoundary {
    Game execute(RedrawInputData redrawInputData) throws IOException, ClassNotFoundException;
}
