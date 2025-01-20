// src/main/java/com/example/gamelibrary/User.java
package org.example;

public class User {
    private String username;
    private GameLibrary library;

    public User(String username) {
        this.username = username;
        this.library = new GameLibrary();
    }

    public String getUsername() {
        return username;
    }

    public GameLibrary getLibrary() {
        return library;
    }
}
