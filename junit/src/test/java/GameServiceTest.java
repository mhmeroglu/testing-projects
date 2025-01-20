import org.example.Game;
import org.example.GameLibrary;
import org.example.GameService;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameService gameService;
    private User userMock;
    private GameLibrary libraryMock;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
        userMock = mock(User.class);
        libraryMock = mock(GameLibrary.class);
        when(userMock.getLibrary()).thenReturn(libraryMock);
    }

    @Test
    void testAddGameToLibrary() {
        System.out.println("Testing: Adding a game to the library.");
        Game game = new Game("Cyberpunk 2077", "RPG");

        doNothing().when(libraryMock).addGame(game);
        when(userMock.getUsername()).thenReturn("TestUser");
        when(libraryMock.findGameByName("Cyberpunk 2077")).thenReturn(null);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(libraryMock.removeGame(anyString())).thenReturn(false);

        gameService.addGameToLibrary(userMock, game);

        verify(libraryMock, times(1)).addGame(game);
        verify(userMock, times(1)).getLibrary();
        verify(libraryMock, never()).findGameByName("NonExistentGame");
    }

    @Test
    void testRemoveGameFromLibrary_Success() {
        System.out.println("Testing: Removing a game from the library successfully.");
        String gameName = "Minecraft";

        when(libraryMock.removeGame(gameName)).thenReturn(true);
        when(userMock.getUsername()).thenReturn("TestUser");
        doNothing().when(libraryMock).addGame(any(Game.class));
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(libraryMock.findGameByName(gameName)).thenReturn(new Game(gameName, "Survival"));

        gameService.removeGameFromLibrary(userMock, gameName);

        verify(libraryMock, times(1)).removeGame(gameName);
        verify(userMock, times(1)).getLibrary();
        verify(libraryMock, never()).findGameByName("NonExistentGame");
    }

    @Test
    void testRemoveGameFromLibrary_Failure() {
        System.out.println("Testing: Removing a game that does not exist in the library.");
        String gameName = "NonExistentGame";

        when(libraryMock.removeGame(gameName)).thenReturn(false);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");

        try {
            gameService.removeGameFromLibrary(userMock, gameName);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        verify(libraryMock, times(1)).removeGame(gameName);
        verify(userMock, times(1)).getLibrary();
    }

    @Test
    void testRateGame_Success() {
        System.out.println("Testing: Rating a game successfully.");
        String gameName = "Portal 2";
        double rating = 4.5;
        Game gameMock = mock(Game.class);

        when(libraryMock.findGameByName(gameName)).thenReturn(gameMock);
        doNothing().when(gameMock).rate(rating);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");

        gameService.rateGame(userMock, gameName, rating);

        verify(libraryMock, times(1)).findGameByName(gameName);
        verify(gameMock, times(1)).rate(rating);
    }

    @Test
    void testRateGame_Failure() {
        System.out.println("Testing: Rating a game that does not exist in the library.");
        String gameName = "NonExistentGame";
        double rating = 3.0;

        when(libraryMock.findGameByName(gameName)).thenReturn(null);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");

        try {
            gameService.rateGame(userMock, gameName, rating);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        verify(libraryMock, times(1)).findGameByName(gameName);
        verify(userMock, times(1)).getLibrary();
    }

    @Test
    void testGetGameDetails_Success() {
        System.out.println("Testing: Retrieving game details for an existing game.");
        String gameName = "Stardew Valley";
        Game gameMock = mock(Game.class);

        // Mocking the methods of the gameMock object
        when(libraryMock.findGameByName(gameName)).thenReturn(gameMock);
        when(gameMock.getName()).thenReturn("Stardew Valley");
        when(gameMock.getGenre()).thenReturn("Simulation");
        when(gameMock.getRating()).thenReturn(0.0);

        // Calling the service method
        String details = gameService.getGameDetails(userMock, gameName);

        // Verifying the method calls on the mock object
        verify(libraryMock, times(1)).findGameByName(gameName);
        verify(gameMock, times(1)).getName();
        verify(gameMock, times(1)).getGenre();
        verify(gameMock, times(1)).getRating();

        // Asserting the correct output
        assertEquals("Game{name='Stardew Valley', genre='Simulation', rating=0.0}", details);
    }



    @Test
    void testGetGameDetails_NotFound() {
        System.out.println("Testing: Retrieving game details for a non-existent game.");
        String gameName = "NonExistentGame";

        when(libraryMock.findGameByName(gameName)).thenReturn(null);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");

        String details = gameService.getGameDetails(userMock, gameName);

        verify(libraryMock, times(1)).findGameByName(gameName);
        verify(userMock, times(1)).getLibrary();
        assertEquals("Game not found.", details);
    }

    @Test
    void testAddMultipleGames() {
        System.out.println("Testing: Adding multiple games to the library.");
        Game game1 = new Game("Game 1", "Genre 1");
        Game game2 = new Game("Game 2", "Genre 2");

        doNothing().when(libraryMock).addGame(game1);
        doNothing().when(libraryMock).addGame(game2);
        when(userMock.getUsername()).thenReturn("TestUser");
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(libraryMock.removeGame(anyString())).thenReturn(false);

        gameService.addGameToLibrary(userMock, game1);
        gameService.addGameToLibrary(userMock, game2);

        verify(libraryMock, times(1)).addGame(game1);
        verify(libraryMock, times(1)).addGame(game2);
    }

    @Test
    void testRemoveMultipleGames() {
        System.out.println("Testing: Removing multiple games from the library.");
        String gameName1 = "Game 1";
        String gameName2 = "Game 2";

        when(libraryMock.removeGame(gameName1)).thenReturn(true);
        when(libraryMock.removeGame(gameName2)).thenReturn(false);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");

        gameService.removeGameFromLibrary(userMock, gameName1);
        try {
            gameService.removeGameFromLibrary(userMock, gameName2);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        verify(libraryMock, times(1)).removeGame(gameName1);
        verify(libraryMock, times(1)).removeGame(gameName2);
    }

    @Test
    void testComplexLibraryOperations() {
        System.out.println("Testing: Adding multiple games and rating one of them.");
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        String gameNameToRate = "Game 1";

        doNothing().when(libraryMock).addGame(game1);
        doNothing().when(libraryMock).addGame(game2);
        when(libraryMock.findGameByName(gameNameToRate)).thenReturn(game1);
        when(libraryMock.getGames()).thenReturn(new ArrayList<>());
        when(userMock.getUsername()).thenReturn("TestUser");
        doNothing().when(game1).rate(4.0);

        gameService.addGameToLibrary(userMock, game1);
        gameService.addGameToLibrary(userMock, game2);
        gameService.rateGame(userMock, gameNameToRate, 4.0);

        verify(libraryMock, times(1)).addGame(game1);
        verify(libraryMock, times(1)).addGame(game2);
        verify(game1, times(1)).rate(4.0);
    }
}
