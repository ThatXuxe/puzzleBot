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

import java.util.List;
import java.util.logging.Logger;

public class SubmitCommand implements CommandExecutor {
    private FileConfiguration config;
    private Logger logger;

    public SubmitCommand(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
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
        if (ConfigHandler.hasUsedCommand(answer, sender.getName())) {
            sender.sendMessage("" + ChatColor.RED + "You have used this command already!");
            return true;
        }
        if (!PuzzleBot.hasAnswer(answer)) {
            sender.sendMessage("" + ChatColor.RED + "Sorry, that is not a correct answer :(");
            return true;
        }
        ActionsIterator iterator = new ActionsIterator(logger);
        List<Action> actions = iterator.parseActions(PuzzleBot.answersMap.get(answer));
        for (Action a : actions) {
            a.fulfill((Player) sender);
        }
        ConfigHandler.addCount(answer, sender.getName());
        return true;
    }
}

