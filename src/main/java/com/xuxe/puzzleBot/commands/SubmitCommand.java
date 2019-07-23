package com.xuxe.puzzleBot.commands;

import com.xuxe.puzzleBot.assets.Action;
import com.xuxe.puzzleBot.assets.ActionsIterator;
import com.xuxe.puzzleBot.main.PuzzleBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class SubmitCommand implements CommandExecutor {
    FileConfiguration config;

    public SubmitCommand(FileConfiguration config) {
        this.config = config;
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
        if (!PuzzleBot.hasAnswer(answer)) {
            sender.sendMessage("" + ChatColor.RED + "Sorry, that is not the correct answer :(");
            return true;
        }
        List<Action> actions = ActionsIterator.parseActions(PuzzleBot.answersMap.get(answer));
        for (Action a : actions) {
            a.fulfill((Player) sender);
        }
        return false;
    }
}

