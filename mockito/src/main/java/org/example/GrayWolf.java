package org.example;

public class GrayWolf extends Monster {
    public GrayWolf() {
        this.name = "GrayWolf";
        this.health = 100;
        this.attackPower = 20;
    }

    @Override
    public void attack(Monster target) {
        System.out.println(name + " fearlessly attacks " + target.getName() + " with " + attackPower + " damage!");
        target.takeDamage(attackPower);
    }
}
