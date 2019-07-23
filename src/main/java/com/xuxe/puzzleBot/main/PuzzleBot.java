package com.xuxe.puzzleBot.main;

import com.xuxe.puzzleBot.commands.SubmitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PuzzleBot extends JavaPlugin {

    public static HashMap<String, String> answersMap;
    private FileConfiguration config;
    private Logger logger;

    public static boolean hasAnswer(String answer) {
        return answersMap.containsKey(answer); //NullPointer here for some reason
    }

    @Override
    public void onEnable() {
        config = getConfig();
        logger = getLogger();
        config.addDefault("answers", "");
        saveConfig();
        logger.info("PuzzleBot has started.");
        List<String> answers = new ArrayList<>();
        if (config.contains("answers")) {
            answers = config.getStringList("answers");
            logger.info("answers have been initialized");
        } else
            logger.warning("No answers found in config.");
        getCommand("submit").setExecutor(new SubmitCommand(config));

        for (String s : answers) {
            answersMap.put(s.substring(0, s.indexOf("->")), s);
        }
    }
    //Action sequence
    /*
        answers:
            -foo->kill->tp(2003,123,423)->message(heyhey)->kill->firework
            -bar->tp(1233,-121,422)->message(hiya)
        #foo is the answer, and every -> represents an action to be taken
    */
}
