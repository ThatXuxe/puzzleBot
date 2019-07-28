# PuzzleBot
A simple plugin for hosting quizzes, treasure hunts and puzzles.
Made with Bukkit.

[![Open Source Love svg2](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)

## Installing

Build the project using Maven or use this download link to the jar: [Not yet created]

## Config

create a config.yml with the following syntax:
```yml
answers:
- answer1~maxUniqueUses->action1->Action2...
- answer2->action1->Action2
```
## Actions
As of Version 1.1.1, the plugin supports 3 types of actions:
- tp(worldname,x,y,z)
- message(contents)
- kill

Refer to the provided exampleConfig.yml

