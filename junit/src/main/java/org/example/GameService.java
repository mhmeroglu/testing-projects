package org.example;

public class GameService {
    public void addGameToLibrary(User user, Game game) {
        user.getLibrary().addGame(game);
    }

    public void removeGameFromLibrary(User user, String gameName) {
        boolean removed = user.getLibrary().removeGame(gameName);
        if (!removed) {
            throw new IllegalArgumentException("Game not found in library.");
        }
    }

    public void rateGame(User user, String gameName, double rating) {
        Game game = user.getLibrary().findGameByName(gameName);
        if (game == null) {
            throw new IllegalArgumentException("Game not found in library.");
        }
        game.rate(rating);
    }

    public String getGameDetails(User user, String gameName) {
        Game game = user.getLibrary().findGameByName(gameName);
        if (game == null) {
            return "Game not found.";
        }
        return "Game{name='" + game.getName() + "', genre='" + game.getGenre() + "', rating=" + game.getRating() + "}";
    }
}
