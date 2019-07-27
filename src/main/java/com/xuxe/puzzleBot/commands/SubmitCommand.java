package com.xuxe.puzzleBot.commands;

import com.xuxe.puzzleBot.assets.Action;
import com.xuxe.puzzleBot.assets.ActionsIterator;
import com.xuxe.puzzleBot.config.ConfigHandler;
import com.xuxe.puzzleBot.main.PuzzleBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SubmitCommand implements CommandExecutor {
    private FileConfiguration config;
    private Logger logger;

    public SubmitCommand(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        if (args.length == 0)
            return false;
        StringBuilder answerBuilder = new StringBuilder();
        for (String s : args) {
            answerBuilder.append(s);
        }
        boolean hasUsedCommandBefore;
        String answer = answerBuilder.toString();
        FileConfiguration config = plugin.getConfig();
        config.createSection("uses");
        try {
            if (!config.contains("uses." + answer)) {
                List<String> players = new ArrayList<>();
                players.add(sender.getName());
                config.set("uses." + answer, players);
                hasUsedCommandBefore = false;
            } else {
                List<String> players = config.getStringList("uses." + answer);
                if (players.contains(sender.getName()))
                    hasUsedCommandBefore = true;
                else {
                    players.add(sender.getName());
                    hasUsedCommandBefore = false;
                }
                config.set("uses." + answer, players);
            }
        } catch (NullPointerException npe) {
            hasUsedCommandBefore = false;
        }
        if (!PuzzleBot.hasAnswer(answer) && !hasUsedCommandBefore) {
            sender.sendMessage("" + ChatColor.RED + "Sorry, that is not a correct answer :(");
            return true;
        }
        logger.info("Player " + sender.getName() + " submitted answer: " + answer + " in " + Helpers.displayLocation(((Player) sender).getLocation()));
        List<Action> actions = PuzzleBot.answersMap.get(answer);
        for (Action a : actions) {
            a.fulfill((Player) sender);
        }
        ConfigHandler.addCount(answer, sender.getName());
        return true;
    }
}

