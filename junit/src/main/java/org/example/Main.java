package org.example;

public class Main {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("Alice");

        // Create games
        Game game1 = new Game("The Witcher 3", "RPG");
        Game game2 = new Game("Stardew Valley", "Simulation");

        // Add games to user's library
        GameService gameService = new GameService();
        gameService.addGameToLibrary(user1, game1);
        gameService.addGameToLibrary(user1, game2);

        // Remove a game from the library
        gameService.removeGameFromLibrary(user1, "The Witcher 3");

        // Display remaining games
        user1.getLibrary().getGames().forEach(System.out::println);
    }
}
