package com.xuxe.puzzleBot.assets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Action {
    private String type; // tp, message, kill
    private Location location;
    private String message;

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void fulfill(Player player) {
        if (type.equalsIgnoreCase("tp")) {
            player.teleport(location);
        } else if (type.equalsIgnoreCase("message")) {
            player.sendMessage(message);
        } else if (type.equalsIgnoreCase("kill")) {
            player.setHealth(0);
        }
    }
}
