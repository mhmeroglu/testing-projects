package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterPool {
    private Random rand = new Random();
    private String[] monsters = {"Goblin", "Dragon", "GrayWolf", "Elf"};
    private List<Monster> availableMonsters = new ArrayList<>();

    public Monster getMonster() {
        if (availableMonsters.isEmpty()) {
            int choice = rand.nextInt(monsters.length);
            return MonsterFactory.createMonster(monsters[choice]);
        }
        return availableMonsters.remove(availableMonsters.size() - 1);
    }

    public void releaseMonster(Monster monster) {
        availableMonsters.add(monster);
    }
}
