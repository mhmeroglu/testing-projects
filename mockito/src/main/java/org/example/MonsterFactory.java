package org.example;

public class MonsterFactory {
    public static Monster createMonster(String type) {
        switch (type) {
            case "Goblin":
                return new Goblin();
            case "Dragon":
                return new Dragon();
            case "Elf":
                return new Elf();
            case "GrayWolf":
                return new GrayWolf();
            default:
                throw new IllegalArgumentException("Unknown monster type: " + type);
        }
    }
}
