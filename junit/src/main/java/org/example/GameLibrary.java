package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameLibrary {
    private List<Game> games;

    public GameLibrary() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public boolean removeGame(String name) {
        return games.removeIf(game -> game.getName().equalsIgnoreCase(name));
    }

    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public Game findGameByName(String name) {
        return games.stream()
                .filter(game -> game.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
