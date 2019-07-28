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

    public static HashMap<String, List<Action>> answersMap = new HashMap<>();
    private FileConfiguration config;
    private static List<String> answers = new ArrayList<>();
    public static HashMap<String, Integer> maxUseMap = new HashMap<>();
    private Logger logger;

    public PuzzleBot() {
        config = getConfig();
        logger = getLogger();

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

        getCommand("submit").setExecutor(new SubmitCommand(this, logger));
        getCommand("puzzle").setExecutor(new ReloadCommand(this));
        reloadHashConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void reloadHashConfig() {
        reloadConfig();
        if (config.contains("answers")) {
            answers = config.getStringList("answers");
            logger.info("Answers have been initialized");
        } else
            logger.warning("No answers found in config.");

        for (String s : answers) {
            String[] answer = {s.split("->")[0]};
            if (answer[0].contains("~")) {
                answer = answer[0].split("~");
                maxUseMap.put(answer[0], Integer.parseInt(answer[1]));
            } else {
                answer = answer[0].split("~");
                maxUseMap.put(answer[0], -1);
            }
            answersMap.put(answer[0], ActionsIterator.parseActions(s));
        }
    }
}
    //Action sequence
    /*
        answers:
            -foo~5->kill->tp(2003,123,423)->message(heyhey)->kill->firework
            -bar->tp(1233,-121,422)->message(hiya)
        #foo is the answer, and every -> represents an action to be taken
    */