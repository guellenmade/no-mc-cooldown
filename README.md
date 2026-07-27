# NoCooldown - Minecraft Spigot Plugin

A lightweight Spigot plugin that completely removes the attack cooldown from swords, allowing players to attack at full speed with maximum damage on every swing.

## What This Plugin Does

Since Minecraft 1.9, combat includes an **attack strength mechanic** (commonly called "sword cooldown"). When you swing a sword, a strength meter appears that must recharge before the next attack deals full damage. Attacking too quickly results in significantly reduced damage.

**NoCooldown eliminates this entirely.** Every sword swing deals full damage instantly -- no waiting, no weakened hits.

### How It Works

The plugin sets each player's `ATTACK_SPEED` attribute to a very high value (1024, compared to the default 1.6 for swords). This causes the attack strength meter to recharge instantly after every swing, so:

- Every attack deals **full damage** regardless of swing speed
- There is **no cooldown indicator** between attacks
- Works with **all sword types**: Wood, Stone, Iron, Gold, Diamond, and Netherite
- Applies to **every player** on the server automatically
- Works against **all entities**: zombies, other players, animals, anything

### Features

- No configuration required -- works out of the box
- Applies automatically to all players on join
- Reapplies every tick to prevent any edge cases from restoring cooldown
- Resets to vanilla defaults when the plugin is removed or disabled
- Pure Bukkit/Spigot API -- no NMS or version-specific code
- Extremely lightweight with minimal performance impact

## Requirements

- **Minecraft server**: Spigot, Paper, Purpur, or any Spigot-compatible fork
- **Spigot API version**: 26.2 or later (also works with older versions -- change the dependency in `pom.xml`)
- **Java**: 21 or later

## Building from Source

### Prerequisites

- [Apache Maven](https://maven.apache.org/) 3.6+
- Java 21+

### Build

Clone or download the project, then run:

```bash
mvn clean package
```

The compiled plugin JAR will be located at:

```
target/NoCooldown-1.0.0.jar
```

## Installation

1. Build the plugin (see above) or download the pre-built JAR
2. Copy `NoCooldown-1.0.0.jar` into your server's `plugins/` folder
3. Restart the server or run `reload`

### Verifying Installation

After starting the server, check the console for:

```
[NoCooldown] NoCooldown enabled! Sword cooldowns removed for all players.
```

You can also confirm it is loaded by running `/plugins` in-game -- `NoCooldown` should appear in the list.

## Usage

The plugin requires no commands or configuration. Once installed:

1. Join the server
2. Equip any sword
3. Swing as fast as you want -- every hit deals full damage

There are no permissions, no commands, and no config file. It applies to **all players** on the server.

## Uninstalling

1. Remove `NoCooldown-1.0.0.jar` from the `plugins/` folder
2. Restart the server

The plugin automatically restores default attack speed values for all online players when it is disabled, so the server returns to vanilla combat behavior cleanly.

## Compatibility

| Server Platform   | Supported |
|-------------------|-----------|
| Spigot            | Yes       |
| Paper             | Yes       |
| Purpur            | Yes       |
| Folia             | Yes       |
| Mohist            | Likely    |

Any server platform that implements the Bukkit/Spigot API should work.

## License

This project is free to use and modify.
