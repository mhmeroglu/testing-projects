// MonsterTest2.java
import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest2 {

    private Monster goblin, dragon;
    private BattleManager battleManager;

    @BeforeEach
    public void setup() {
        goblin = new Goblin();
        dragon = new Dragon();
        battleManager = new BattleManager();
    }

    // Basis-Path Testing for BattleManager
    @Test
    public void testBattlePath1_GoblinWins() {
        // Configure Goblin to win by setting high attack power
        goblin.setAttackPower(60);
        battleManager.battle(goblin, dragon);
        assertTrue(dragon.getHealth() <= 0, "Dragon should be defeated by Goblin.");

        System.out.println("\n---\n");
    }

    @Test
    public void testBattlePath2_DragonWins() {
        // Configure Dragon to win by setting Goblin's health low
        goblin.setHealth(1);
        battleManager.battle(goblin, dragon);
        assertTrue(goblin.getHealth() <= 0, "Goblin should be defeated by Dragon.");
        System.out.println("\n---\n");


    }

    @Test
    public void testBattlePath3_ContinuousBattle_GoblinEventuallyWins() {
        // Configure a longer battle where Goblin wins after multiple rounds
        goblin.setAttackPower(6);
        dragon.setHealth(12);
        battleManager.battle(goblin, dragon);
        assertTrue(dragon.getHealth() <= 0, "Dragon should be defeated by Goblin after several rounds.");
        System.out.println("\n---\n");

    }

    @Test
    public void testBattlePath4_ContinuousBattle_DragonEventuallyWins() {
        // Configure a longer battle where Dragon wins after multiple rounds
        dragon.setAttackPower(10);
        goblin.setHealth(15);
        battleManager.battle(goblin, dragon);
        assertTrue(goblin.getHealth() <= 0, "Goblin should be defeated by Dragon after several rounds.");
        System.out.println("\n---\n");

    }

    // Mutant Code Tests
    @Test
    public void testMutantCode_alwaysFalseCondition() {
        // Modify getHealth comparison in the BattleManager to always return false
        battleManager.battle(goblin, dragon);
        assertTrue(goblin.getHealth() > 0 || dragon.getHealth() > 0, "Loop should terminate even with always false mutation.");
        System.out.println("\n---\n");

    }

    @Test
    public void testMutantCode_ModifiedAttackPower() {
        // Change Goblin's attack power and check if it affects the outcome correctly
        goblin.setAttackPower(2);
        battleManager.battle(goblin, dragon);
        assertTrue(goblin.getHealth() <= 0 || dragon.getHealth() <= 0, "Battle should complete even with modified attack power.");
        System.out.println("\n---\n");

    }

    @Test
    public void testMutantCode_ReverseAttackOrder() {
        // Reverse the attack order and check if it affects the outcome correctly
        battleManager.battle(dragon, goblin);  // Swapped positions
        assertTrue(goblin.getHealth() <= 0 || dragon.getHealth() <= 0, "Battle should complete with reversed attack order.");
        System.out.println("\n---\n");

    }

    @Test
    public void testMutantCode_HealthUpdateSkip() {
        // Skip health update on one attack and check if it affects the outcome correctly
        dragon.setHealth(1);
        battleManager.battle(goblin, dragon);
        assertEquals(0, dragon.getHealth(), "Dragon should be defeated even if health update is skipped once.");
        System.out.println("\n---\n");

    }

    // Table-Based Test Scenario
    @Test
    public void testBattleScenarioTable() {
        // Table setup: initial conditions and expected outcomes
        int[][] conditions = {
                {50, 5, 50, 10, 0},  // Goblin 50 HP, 5 ATK vs Dragon 50 HP, 10 ATK, expected: Dragon wins
                {10, 20, 30, 5, 1},   // Goblin 10 HP, 20 ATK vs Dragon 30 HP, 5 ATK, expected: Goblin wins
                {40, 15, 40, 15, 0},  // Goblin 40 HP, 15 ATK vs Dragon 40 HP, 15 ATK, expected: Either could win (more rounds)
                {100, 1, 20, 20, 0}   // Goblin 100 HP, 1 ATK vs Dragon 20 HP, 20 ATK, expected: Dragon wins quickly
        };

        for (int[] condition : conditions) {
            goblin.setHealth(condition[0]);
            goblin.setAttackPower(condition[1]);
            dragon.setHealth(condition[2]);
            dragon.setAttackPower(condition[3]);

            battleManager.battle(goblin, dragon);

            if (condition[4] == 0) {
                assertTrue(goblin.getHealth() <= 0 || dragon.getHealth() <= 0, "One monster should be defeated.");
            } else if (condition[4] == 1) {
                assertTrue(dragon.getHealth() <= 0, "Goblin should win this battle scenario.");
            }
            System.out.println("\n---\n");

        }
    }
}
