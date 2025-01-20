package org.example;

public class Elf extends Monster {
    public Elf() {
        this.name = "Elf";
        this.health = 40;
        this.attackPower = 7;
    }

    @Override
    public void attack(Monster target) {
        System.out.println(name + " shoots arrows at " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);
    }
}