package com.example.scrabble.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.scrabble.entity.Game;

class GameModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {

  @Override
  public EntityModel<Game> toModel(Game game) {

    return EntityModel.of(
      game,
      linkTo(methodOn(GameController.class).getGame(game.getId())).withSelfRel()
    );
  }
}