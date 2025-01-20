package org.example;

public class MonsterPrototype {
    private Monster monster;

    public MonsterPrototype(Monster monster) {
        this.monster = monster;
    }

    public Monster cloneMonster() {
        try {
            return monster.clone(); // Deep copy using the Cloneable interface
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
