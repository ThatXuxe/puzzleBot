package com.xuxe.puzzleBot.assets;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Action {
    private String type = ""; // tp, message, kill
    private Location location;
    private String message;

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    void setLocation(Location location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public void fulfill(Player player) {
        if (type.equalsIgnoreCase("tp")) {
            player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else if (type.equalsIgnoreCase("message")) {
            player.sendMessage(message);
        } else if (type.equalsIgnoreCase("kill")) {
            player.setHealth(0);
        }
    }
}
