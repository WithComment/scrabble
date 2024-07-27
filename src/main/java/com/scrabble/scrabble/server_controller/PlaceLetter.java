package com.scrabble.scrabble.server_controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scrabble.scrabble.use_case.place_letter.PlaceLetterOutputData;

@RestController
@RequestMapping("/games/{gameId}/place-letter")
public class PlaceLetter {

  @PostMapping
  public PlaceLetterOutputData placeLetter(
    @PathVariable(value = "gameId") int gameId,
    @RequestParam(value = "x") int x,
    @RequestParam(value = "y") int y,
    @RequestParam(value = "letter") char letter
  ) {
    // TODO
    throw new UnsupportedOperationException();
  }

  private PlaceLetterOutputData execute(int gameId, int x, int y, char letter) {
    PlaceLetterControllerFaco
  }
}
