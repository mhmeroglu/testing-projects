package org.example;

public class GameState {
    private static GameState instance;
    private int score;

    private GameState() {
        score = 0; // Initial score set to 0
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int amount) {
        if (amount < 0) {
            score = Math.max(0, score + amount); // Prevent score from going below zero
        } else {
            score += amount;
        }
    }

    public void resetScore() {
        score = 0; // Method to reset score
    }
}
