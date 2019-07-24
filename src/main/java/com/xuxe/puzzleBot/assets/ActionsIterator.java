package com.xuxe.puzzleBot.assets;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ActionsIterator {
    private Logger logger;

    public ActionsIterator(Logger logger) {
        this.logger = logger;
    }

    public List<Action> parseActions(String actionSequence) {
        String[] actions = popArray(actionSequence.split("->"));
        List<Action> processedActions = new ArrayList<>();
        for (String s : actions) {
            Action action = parseEachAction(s);
            if (action != null)
                processedActions.add(action);
        }
        return processedActions;
    }

    private String[] popArray(String[] args) {
        if (args.length >= 2) {
            String[] newArray = new String[args.length - 1];
            System.arraycopy(args, 1, newArray, 0, newArray.length);
            return newArray;
        } else {
            return new String[0];
        }
    }

    private Action parseEachAction(String actionString) {
        Action action = new Action();
        String commandArgs = "";
        if (actionString.contains("("))
            commandArgs = actionString.substring(actionString.indexOf('(') + 1, actionString.lastIndexOf(')'));
        if (actionString.startsWith("tp")) {
            String[] coords = commandArgs.split("_");
            Location location = new Location(Bukkit.getWorld(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2]), Double.valueOf(coords[3]));
            action.setLocation(location);
            action.setType("tp");
            return action;
        } else if (actionString.startsWith("message")) {
            action.setMessage(commandArgs);
            action.setType("message");
            return action;
        } else if (actionString.startsWith("kill")) {
            action.setType("kill");
            return action;
        }
        return null;
    }
}
