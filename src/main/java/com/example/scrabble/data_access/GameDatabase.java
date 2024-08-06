package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;
import com.example.scrabble.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameDatabase implements GameDataAccess{
    @Autowired
    public GameRepository gameRepository;

    public GameDatabase(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public Game create(Game game){
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game get(int gameId){
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()){
            return game.get();
        }
        else {
            return null;
        }
    }

    @Override
    public void update(Game game){
        gameRepository.save(game);
    }

    @Override
    public void delete(int gameId){
        gameRepository.deleteById(gameId);
    }
}
