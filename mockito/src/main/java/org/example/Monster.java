package org.example;

public abstract class Monster implements Cloneable {
    protected String name;
    protected int health;
    protected int attackPower;

    public abstract void attack(Monster target);

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower(){return attackPower;}

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0; // Prevent health from going below zero
        }
    }


    @Override
    public Monster clone() throws CloneNotSupportedException {
        return (Monster) super.clone(); // Shallow copy for now
    }
}
