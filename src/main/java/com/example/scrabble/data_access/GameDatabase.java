package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;
import com.example.scrabble.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameDatabase implements GameDataAccess{
    @Autowired
    public GameRepository gameRepository;

    private static Logger logger = LoggerFactory.getLogger(GameDatabase.class);

    public GameDatabase(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Game create(Game game){
        logger.info("Creating game: " + game);
        gameRepository.saveAndFlush(game);
        return game;
    }

    @Override
    @Transactional
    public Game get(int gameId){
        gameRepository.flush();
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()){
            if (game.get().getCurrentPlay() != null) {
                logger.info("Getting game current play moves: " + game.get().getCurrentPlay().getMoves());
            }
            return game.get();
        }
        else {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(Game game){
        logger.info("Updating game: " + game);
        gameRepository.delete(game);
        gameRepository.flush();
        gameRepository.saveAndFlush(game);
        get(game.getId());
    }

    @Override
    @Transactional
    public void delete(int gameId){
        gameRepository.deleteById(gameId);
        gameRepository.flush();
    }

    @Override
    @Transactional
    public void deleteAll(){
        gameRepository.deleteAll();
        gameRepository.flush();
    }

    @Override
    @Transactional
    public int getGameCount(){
        gameRepository.flush();
        return (int) gameRepository.count();
    }
}
