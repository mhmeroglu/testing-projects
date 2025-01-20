package org.example;

public class Goblin extends Monster {
    public Goblin() {
        this.name = "Goblin";
        this.health = 30;
        this.attackPower = 5;
    }

    @Override
    public void attack(Monster target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);
    }
}
