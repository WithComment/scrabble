package com.example.scrabble;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(GameDataAccess gameDao) {

    return args -> {
      List<Player> players = new LinkedList<>();
      players.add(new Player(0));
      players.add(new Player(1));
      log.info("Preloading " + gameDao.create(new Game(players)));
    };
  }
}