package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a MonsterFactory instance (Factory Design Pattern)
        System.out.println("Factory Design Pattern: MonsterFactory is being used to create monsters.");
        MonsterFactory factory = new MonsterFactory();

        // Create a GameState instance (Singleton Design Pattern)
        System.out.println("Singleton Design Pattern: GameState is a shared instance for managing the game score.");
        GameState gameState = GameState.getInstance();
        System.out.println("\n--- Welcome to Monster Battle! ---");
        System.out.println("Initial Score: " + gameState.getScore() + "\n");

        // Create a MonsterPool to manage monster instances (Object Pool Design Pattern)
        System.out.println("Object Pool Design Pattern: MonsterPool is used to manage monster instances.\n");
        MonsterPool monsterPool = new MonsterPool();

        // Prompt user to choose monsters for battle
        System.out.println("Choose your monster:");
        System.out.println("1. Goblin");
        System.out.println("2. Dragon");
        System.out.println("3. Elf");
        System.out.println("4. GrayWolf (The strongest monster!)");
        System.out.print("Enter the number of your choice: ");
        int choice = scanner.nextInt();
        Monster playerMonster = null;

        // Create player monster based on user input using the Factory Pattern
        switch (choice) {
            case 1:
                playerMonster = factory.createMonster("Goblin");
                break;
            case 2:
                playerMonster = factory.createMonster("Dragon");
                break;
            case 3:
                playerMonster = factory.createMonster("Elf");
                break;
            case 4:
                playerMonster = factory.createMonster("GrayWolf");
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Goblin.");
                playerMonster = factory.createMonster("Goblin");
                break;
        }

        // Using MonsterPrototype to clone player's monster
        MonsterPrototype prototype = new MonsterPrototype(playerMonster);
        System.out.println("\nMonster Prototype Pattern: Cloning player's monster for the battle...");
        Monster clonedPlayerMonster = prototype.cloneMonster();

        // Get a second monster from the pool for the enemy
        System.out.println("\nPulling a monster from the pool for your opponent...");
        Monster enemyMonster = monsterPool.getMonster();
        System.out.println("Your enemy is a " + enemyMonster.getName() + "! (Pulled from the Object Pool)");

        // Display the details of the player's cloned monster
        System.out.println("\nYou have chosen a " + clonedPlayerMonster.getName() + " with " + clonedPlayerMonster.getHealth() + " health.\n");

        // Battle between player's cloned monster and enemy monster
        System.out.println("--- The Battle Begins! ---");
        BattleManager battleManager = new BattleManager();
        battleManager.battle(clonedPlayerMonster, enemyMonster);

        // Check for victory and update score
        if (enemyMonster.getHealth() <= 0) {
            System.out.println("\nYou have won the battle!");
            gameState.increaseScore(20); // Increase score on victory
        } else {
            System.out.println("\nYou have been defeated!");
        }

        // Show the updated game score
        System.out.println("Final Score: " + gameState.getScore());

        // Clean up the monster pool
        System.out.println("\nReleasing the monsters back to the pool...");
        monsterPool.releaseMonster(enemyMonster);
        monsterPool.releaseMonster(clonedPlayerMonster);

        // Close the scanner
        scanner.close();
        System.out.println("\n--- Game Over ---");
    }
}
