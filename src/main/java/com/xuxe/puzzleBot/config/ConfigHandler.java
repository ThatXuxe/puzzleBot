package com.xuxe.puzzleBot.config;

import com.xuxe.puzzleBot.main.PuzzleBot;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    private static FileConfiguration config;
    private static PuzzleBot puzzleBot;

    public ConfigHandler() {
        puzzleBot = new PuzzleBot();
        config = puzzleBot.getConfig();
    }

    public static void addCount(String answerName, String playerName) {
        String key = "c-" + answerName;
        if (!config.contains(key))
            return;
        List<String> players = config.getStringList("p-" + answerName);
        players.add(playerName);
        int count = config.getInt(key);
        config.set(key, count + 1);
        config.set("p-", players);
        save();
    }

    public static void createCounter(String answerName) {
        try {
            config.set("c-" + answerName, 0);
            config.set("p-" + answerName, new ArrayList<String>());
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasUsedCommand(String answerName, String player) {
        try {
            if (!config.contains("p-" + answerName))
                return false;
            List<String> players = config.getStringList("p-" + answerName);
            return players.contains(player);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static void save() {
        puzzleBot.saveConfig();
    }
}
