package org.example;

public class Game {
    private String name;
    private String genre;
    private double rating;

    public Game(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.rating = 0.0;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public void rate(double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Game{name='" + name + "', genre='" + genre + "', rating=" + rating + '}';
    }
}
