// Ali Barkın Öztürk & Mehmet Eroğlu

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {

    // Testlerin başlangıcında kullanılacak canavarlar, battle manager, game state ve monster pool tanımları
    private Monster goblin, dragon, elf, grayWolf;
    private BattleManager battleManager;
    private GameState gameState;
    private MonsterPool monsterPool;

    @BeforeEach
    public void setup() {
        // Her testten önce çağrılan metot; canavarları, battle manager'ı, game state'i ve monster pool'u başlatır
        goblin = new Goblin();
        dragon = new Dragon();
        elf = new Elf();
        grayWolf = new GrayWolf();
        battleManager = new BattleManager();
        gameState = GameState.getInstance();
        monsterPool = new MonsterPool();
        gameState.resetScore(); // Reset score before each test
    }

    // Test Suite 1: Canavar Davranışları
    @ParameterizedTest
    @CsvSource({
            "Goblin, 30, 5",
            "Dragon, 50, 10",
            "Elf, 40, 7",
            "GrayWolf, 100, 20"
    })
    public void testParameterizedMonsterAttributes(String monsterType, int expectedHealth, int expectedAttackPower) {
        Monster monster = MonsterFactory.createMonster(monsterType);

        assertEquals(expectedHealth, monster.getHealth(), monsterType + "'s health should match the expected value.");
        assertEquals(expectedAttackPower, monster.getAttackPower(), monsterType + "'s attack power should match the expected value.");
    }

    @Test
    public void testBattleVictory() {
        // Bir canavarın savaş sonucunda yenilip yenilmediğini kontrol eder.
        battleManager.battle(goblin, dragon);
        assertTrue(dragon.getHealth() <= 0 || goblin.getHealth() <= 0, "One monster should be defeated after battle.");
    }

    @Test
    public void testMonsterAttackReducesHealth() {
        // Canavarların birbirine saldırdığında sağlıklarının nasıl etkilendiğini test eder.
        Monster goblin = new Goblin();
        Monster dragon = new Dragon();

        assertEquals(50, dragon.getHealth(), "Dragon's initial health should be 50.");

        goblin.attack(dragon);

        // Goblin'in saldırı gücü 5 olduğuna göre, Dragon'un sağlığı 45 olmalı
        assertEquals(45, dragon.getHealth(), "Dragon's health should be reduced by Goblin's attack.");
    }

    @Test
    public void testHealthNotBelowZero() {
        Monster goblin = new Goblin();
        goblin.takeDamage(50); // Goblin'in sağlığı 30, 50 hasar alırsa sağlık 0'dan az olamaz
        assertEquals(0, goblin.getHealth(), "Goblin's health should not go below zero.");
    }

    // Test Suite 2: Factory, Singleton ve Object Pool Desenleri
    @Test
    public void testMonsterFactory() {
        // Canavar fabrikasının doğru şekilde canavar oluşturup oluşturmadığını test eder.
        Monster goblin2 = MonsterFactory.createMonster("Goblin");
        assertNotNull(goblin2, "Goblin should be created by the factory.");
        assertEquals(goblin.getName(), goblin2.getName(), "Factory should create a Goblin with the correct name.");

        Monster dragon = MonsterFactory.createMonster("Dragon");
        assertNotEquals(goblin.getName(), dragon.getName(), "Factory should create different types of monsters.");
    }

    @Test
    public void testMonsterFactoryInvalidType() {
        // Geçersiz bir canavar türü için exception fırlatıldığını kontrol eder.
        assertThrows(IllegalArgumentException.class, () -> {
            MonsterFactory.createMonster("UnknownMonster");
        });
    }

    @Test
    public void testSingletonGameState() {
        // GameState'in singleton olduğu ve her iki instance'ın aynı olduğunu kontrol eder.
        GameState gameState1 = GameState.getInstance();
        GameState gameState2 = GameState.getInstance();
        assertSame(gameState1, gameState2, "GameState should be a singleton, both instances should be the same.");
    }

    @Test
    public void testGameScoreBeforeBattle() {
        // Savaş öncesinde oyunun puanının 0 olduğunu kontrol eder.
        assertEquals(0, gameState.getScore(), "Game score should be 0 before the battle starts.");
    }



    @Test
    public void testMonsterPoolRetrieval() {
        // Monster pool'dan canavar alındığında geçerli bir canavar alındığını kontrol eder.
        Monster pooledMonster = monsterPool.getMonster();
        assertNotNull(pooledMonster, "Monster should be retrieved from the pool.");
        assertTrue(pooledMonster instanceof Monster, "Retrieved object should be a Monster.");
    }

    @Test
    public void testMonsterPoolRelease() {
        // Canavarların pool'a serbest bırakılması ve geri alınmasını test eder.
        Monster testMonster = new Goblin();
        monsterPool.releaseMonster(testMonster);
        Monster retrievedMonster = monsterPool.getMonster();
        assertSame(testMonster, retrievedMonster, "Released monster should be retrieved from the pool.");
    }



    @Test
    public void testMonsterCloning() throws CloneNotSupportedException {
        // Canavarların klonlanmasını test eder.
        MonsterPrototype prototype = new MonsterPrototype(goblin);
        Monster clonedGoblin = prototype.cloneMonster();
        assertNotSame(goblin, clonedGoblin, "Cloned monster should not be the same instance as the original.");
        assertEquals(goblin.getName(), clonedGoblin.getName(), "Cloned monster should have the same name.");
    }

    @Test
    public void testClonedMonsterIsValid() throws CloneNotSupportedException {
        // Klonlanan canavarın geçerli bir canavar olup olmadığını kontrol eder.
        MonsterPrototype prototype = new MonsterPrototype(goblin);
        Monster clonedGoblin = prototype.cloneMonster();

        assertNotNull(clonedGoblin, "Cloned monster should not be null.");
        assertTrue(isValidMonster(clonedGoblin), "Cloned monster should be a valid monster.");
    }

    @Test
    public void testValidMonsterFromPool() {
        // Monster pool'dan alınan canavarın geçerli olduğunu kontrol eder.
        Monster pooledMonster = monsterPool.getMonster();
        assertNotNull(pooledMonster, "Retrieved monster should not be null.");
        assertTrue(isValidMonster(pooledMonster), "Pooled monster should be a valid monster.");
    }

    // Geçerli bir canavar olup olmadığını kontrol eden yardımcı metod
    private boolean isValidMonster(Monster monster) {
        // Geçerli canavarları kontrol etmek için basit bir kontrol (örn. sınıf adı)
        return monster instanceof Goblin || monster instanceof Dragon || monster instanceof Elf || monster instanceof GrayWolf;
    }



    @Test
    public void testCloneIsNotAffectedByOriginal() throws CloneNotSupportedException {
        // Orijinal canavara yapılan hasarın klona etki etmediğini test eder.
        MonsterPrototype prototype = new MonsterPrototype(goblin);
        Monster clonedGoblin = prototype.cloneMonster();

        goblin.takeDamage(10);

        // Klon Goblin'in sağlığı etkilenmemeli
        assertNotEquals(goblin.getHealth(), clonedGoblin.getHealth(), "Cloned monster's health should not be affected by original.");
    }

    @Test
    public void testGameStateScoreIncrease() {
        // Oyunun puanının savaştan sonra artıp artmadığını kontrol eder.
        gameState.increaseScore(15);
        assertEquals(15, gameState.getScore(), "Game score should increase by the specified amount.");
    }

    @Test
    public void testMonsterFactoryCreatesCorrectHealth() {
        // Monster factory tarafından oluşturulan canavarın başlangıç sağlığının doğru olduğunu test eder.
        Monster dragon = MonsterFactory.createMonster("Dragon");
        assertEquals(50, dragon.getHealth(), "Dragon created by factory should have correct initial health.");
    }



    @Test
    public void testGameStatePreventsNegativeScore() {
        gameState.increaseScore(-10);
        assertEquals(0, gameState.getScore());
    }




    @AfterEach
    public void tearDown() {
        // Her testten sonra yapılacak işlemler (temizlik, kaynakları serbest bırakma vb.)
        battleManager = null;
        gameState = null;
        monsterPool = null;
    }


}
