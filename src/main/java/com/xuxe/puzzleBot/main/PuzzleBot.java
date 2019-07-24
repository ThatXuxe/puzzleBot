package com.xuxe.puzzleBot.main;

import com.xuxe.puzzleBot.commands.SubmitCommand;
import com.xuxe.puzzleBot.config.ConfigHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PuzzleBot extends JavaPlugin {

    public static HashMap<String, String> answersMap = new HashMap<>();
    private FileConfiguration config;

    public PuzzleBot() {
        config = getConfig();
    }

    public static boolean hasAnswer(String answer) {
        return answersMap.containsKey(answer);
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("PuzzleBot has started.");
        List<String> answers = new ArrayList<>();
        if (config.contains("answers")) {
            answers = config.getStringList("answers");
            logger.info("Answers have been initialized");
        } else
            logger.warning("No answers found in config.");
        getCommand("submit").setExecutor(new SubmitCommand(config, logger));


        for (String s : answers) {
            ConfigHandler.createCounter(s.split("->")[0]);
            answersMap.put(s.split("->")[0], s);
        }
        config.options().copyDefaults(true);
        saveConfig();
    }
    //Action sequence
    /*
        answers:
            -foo->kill->tp(2003,123,423)->message(heyhey)->kill->firework
            -bar->tp(1233,-121,422)->message(hiya)
        #foo is the answer, and every -> represents an action to be taken
    */
}
