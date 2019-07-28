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
        String answer = answerBuilder.toString();
        FileConfiguration config = plugin.getConfig();
        config.createSection("uses");
        int uses;
        try {
            if (!config.contains("uses." + answer)) {
                List<String> players = new ArrayList<>();
                players.add(sender.getName());
                config.set("uses." + answer, players);
                uses = 0;
            } else {
                List<String> players = config.getStringList("uses." + answer);
                uses = players.size();
                if (!players.contains(sender.getName()))
                    players.add(sender.getName());

                config.set("uses." + answer, players);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            uses = 0;
        }
        int maxUse = (PuzzleBot.maxUseMap.get(answer) == -1) ? uses + 1 : PuzzleBot.maxUseMap.get(answer);
        if (uses > maxUse) {
            sender.sendMessage("" + ChatColor.RED + "Sorry, you were too late to make this submission");
            return true;
        }
        if (!PuzzleBot.hasAnswer(answer)) {
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

