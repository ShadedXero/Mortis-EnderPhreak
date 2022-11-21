package me.none030.mortisenderphreak.utils;

import java.util.UUID;

public class User {

    private UUID player;
    private String password;

    public User(UUID player, String password) {
        this.player = player;
        this.password = password;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
