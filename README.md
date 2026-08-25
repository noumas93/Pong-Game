# Pong Game

A classic Pong clone built with Java Swing. Control your paddle with the mouse, rally against an AI opponent, and try to beat your local highscore.

## Features

- Mouse-controlled player paddle, with an AI-controlled enemy paddle that tracks the ball
- Easy and Hard difficulty modes — Hard adds two fixed obstacle blocks on the center line and a faster enemy paddle
- Ball speed ramps up (with a cap) as your score climbs, for an escalating challenge
- Highscore is saved to disk and persists between sessions
- Pause menu (`Esc`) with Resume / Main Menu / Exit, navigable by keyboard or mouse
- Main menu navigable by keyboard (arrow keys + Enter) or mouse

## Running the game

Requires a Java Development Kit (JDK) on your `PATH`.

```powershell
javac -d out (Get-ChildItem Game\src\*.java).FullName
java -cp out Main
```

(On macOS/Linux, use `javac -d out Game/src/*.java` instead of the `Get-ChildItem` call.)

## Controls

- **Mouse** — move your paddle up and down
- **Esc** — pause / resume
- **Arrow keys + Enter** — navigate menus without a mouse

## Project structure

```
Game/src/
  Main.java        entry point
  Board.java        top-level window, switches between menu and game panels
  MenuPanel.java     main menu UI and input handling
  MenuButton.java    styled JButton used in the menus
  GamePanel.java     game loop, rendering, and the pause overlay
  Player.java        player paddle
  Enemy.java         AI-controlled paddle
  Ball.java          ball physics and collision
  Highscore.java     highscore persistence
  GameConfig.java    shared field/paddle/ball/obstacle dimensions
```