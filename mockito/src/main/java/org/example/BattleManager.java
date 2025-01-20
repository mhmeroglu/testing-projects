package org.example;

public class BattleManager {
    public void battle(Monster monster1, Monster monster2) {
        // Savaşın bitip bitmediğini kontrol etmek için bir flag
        boolean battleOver = false;

        // Savaş devam ederken her iki canavarın da sağlığı sıfırdan büyük olmalı
        while (!battleOver) {
            monster1.attack(monster2);

            // İlk canavar ikinci canavarı yenerse, savaş sona erer
            if (monster2.getHealth() <= 0) {
                System.out.println(monster2.getName() + " has been defeated!");
                battleOver = true;
                break;
            }

            monster2.attack(monster1);

            // İkinci canavar ilk canavarı yenerse, savaş sona erer
            if (monster1.getHealth() <= 0) {
                System.out.println(monster1.getName() + " has been defeated!");
                battleOver = true;
            }
        }
    }
}


