package com.scrabble.scrabble.server_controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/confirm_play")
public class ConfirmPlay {

  @PostMapping
  public void confirmPlay(
    @RequestParam(value = "game_id") int gameId
  ) {
    // TODO
    throw new UnsupportedOperationException();
  }
}
