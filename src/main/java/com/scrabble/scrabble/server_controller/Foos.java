package com.scrabble.scrabble.server_controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrabble.scrabble.entity.Game;
import com.scrabble.scrabble.entity.Player;

@RestController
@RequestMapping("/foos")
public class Foos {
  @GetMapping
  public Game foos() {
    List<Player> players = new ArrayList<>();
    players.add(new Player(0));
    players.add(new Player(1));
    return new Game(players);
  }
}
