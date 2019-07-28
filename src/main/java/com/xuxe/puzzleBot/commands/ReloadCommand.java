package com.xuxe.puzzleBot.commands;

import com.xuxe.puzzleBot.main.PuzzleBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private PuzzleBot plugin;

    public ReloadCommand(PuzzleBot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("puzzle.admin")) {
            commandSender.sendMessage("" + ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (args[0].equals("reload"))
            plugin.reloadHashConfig();
        else
            commandSender.sendMessage("Usage: /puzzle reload");
        return true;
    }
}
