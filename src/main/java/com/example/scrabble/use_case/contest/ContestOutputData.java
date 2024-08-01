package com.example.scrabble.use_case.contest;

import com.example.scrabble.entity.Game;

import java.util.List;

public class ContestOutputData {
    private final List<String> invalidWords;

    public ContestOutputData(List<String> invalidWords) {
        this.invalidWords = invalidWords;
    }

    public List<String> getInvalidWords() {
        return this.invalidWords;
    }
}