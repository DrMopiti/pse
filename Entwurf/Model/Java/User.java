package com.example.user.schachapp;

public class User {
    private String name;
    private int playedGames;
    private int wonGames;
    private int drawedGames;
    private int lostGames;
    private int elo;

    public User(String name) {
        this.name = name;
        this.playedGames = 0;
        this.wonGames = 0;
        this.drawedGames = 0;
        this.lostGames = 0;
        this.elo = 1000;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void addWonGame() {
        this.wonGames++;
        this.playedGames++;
    }

    public int getDrawedGames() {
        return drawedGames;
    }

    public void addDrawedGame() {
        this.drawedGames++;
        this.playedGames++;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void addLostGame() {
        this.lostGames++;
        this.playedGames++;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
