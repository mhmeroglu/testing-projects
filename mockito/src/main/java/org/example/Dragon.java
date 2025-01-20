package org.example;

// Dragon.java
public class Dragon extends Monster {
    public Dragon() {
        this.name = "Dragon";
        this.health = 50;
        this.attackPower = 10;
    }

    @Override
    public void attack(Monster target) {
        System.out.println(name + " breathes fire on " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);
    }
}