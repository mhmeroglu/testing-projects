import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MockitoTest {

    private BattleManager battleManager;
    private Monster mockMonster1;
    private Monster mockMonster2;
    private GameState mockGameState;

    @BeforeEach
    void setUp() {
        battleManager = new BattleManager();
        mockMonster1 = mock(Monster.class);
        mockMonster2 = mock(Monster.class);
        mockGameState = mock(GameState.class);
    }

    @Test
    void testBattle_Monster1Wins() {
        // Arrange
        when(mockMonster1.getHealth()).thenReturn(10).thenReturn(10).thenReturn(10).thenReturn(0); // Monster1 attacks until Monster2 dies
        when(mockMonster2.getHealth()).thenReturn(10).thenReturn(5).thenReturn(0);
        when(mockMonster2.getName()).thenReturn("MockMonster2");

        // Act
        battleManager.battle(mockMonster1, mockMonster2);

        // Assert
        verify(mockMonster1, atLeastOnce()).attack(mockMonster2);
        verify(mockMonster2, atLeastOnce()).attack(mockMonster1);
    }

    @Test
    void testBattle_Monster2Wins() {
        // Arrange
        when(mockMonster1.getHealth()).thenReturn(10).thenReturn(5).thenReturn(0);
        when(mockMonster2.getHealth()).thenReturn(10).thenReturn(10).thenReturn(10).thenReturn(0); // Monster2 attacks until Monster1 dies
        when(mockMonster1.getName()).thenReturn("MockMonster1");

        // Act
        battleManager.battle(mockMonster1, mockMonster2);

        // Assert
        verify(mockMonster1, atLeastOnce()).attack(mockMonster2);
        verify(mockMonster2, atLeastOnce()).attack(mockMonster1);
    }

    @Test
    void testGameState_IncreaseScoreOnVictory() {
        // Arrange
        GameState gameState = GameState.getInstance();
        gameState.resetScore();

        when(mockMonster1.getHealth()).thenReturn(10).thenReturn(10).thenReturn(0);
        when(mockMonster2.getHealth()).thenReturn(10).thenReturn(5).thenReturn(0);
        when(mockMonster2.getName()).thenReturn("MockMonster2");

        // Act
        battleManager.battle(mockMonster1, mockMonster2);
        gameState.increaseScore(20);

        // Assert
        assert gameState.getScore() == 20;
    }

    @Test
    void testGameState_ScoreDoesNotGoBelowZero() {
        // Arrange
        when(mockGameState.getScore()).thenReturn(0);
        doNothing().when(mockGameState).resetScore();

        // Act
        mockGameState.resetScore();

        // Assert
        verify(mockGameState, times(1)).resetScore();
    }

    @Test
    void testMonsterFactory_CreateDifferentMonsters() {
        // Arrange
        Monster goblin = MonsterFactory.createMonster("Goblin");
        Monster dragon = MonsterFactory.createMonster("Dragon");

        // Assert
        assert goblin instanceof Goblin;
        assert dragon instanceof Dragon;
    }
}
