# ValidAttack

![NeoForge](https://img.shields.io/badge/NeoForge-1.21.1-orange)
![License](https://img.shields.io/badge/License-MIT-green)
![纯客户端](https://img.shields.io/badge/Side-Client--Only-blue)

**ValidAttack** is a lightweight Minecraft combat assist mod that features several configurations around the "valid attack" function, allowing players to more easily launch attacks adapted to Minecraft combat updates. Hopefully, this will protect your mouse and hands. Thank you.

---

## Features

### 1. Intercept Incomplete Charge Clicks

> On by Default

When the attack cooldown is not full, **instantly cancel your attack action**. An attack will only be launched when the charge bar reaches 100%.

### 2. Hold Attack

> On by Default

**Hold down** the attack button (left click), and the mod will automatically launch an attack the instant the charge bar is full. No need for repeated taps; simply hold to unleash fully charged attacks at the highest frequency.

### 3. Validity Check

> Off by Default

Used in conjunction with **Hold Attack**. When enabled, an attack will only be launched when the crosshair is pointed at a valid target.

- When used alone: ​​Checks for single entities along the crosshair path.

- When used with aim assist: Checks for valid targets within the entire cone scan area.

### 4. Simpler Attack Logic

> Off by default

Simulates larger-range normal attacks found in some games.

Scans for the nearest living target within a **120° cone area** in front of the player. No precise aiming is required; the module automatically selects the nearest target within the cone to attack. Its maximum check distance depends on the player's actual attack distance.

### 5. Aim Assist Blacklist

> Off by default

Add entity registry IDs (e.g. `minecraft:villager`, `minecraft:iron_golem`) to the config, and aim assist will **skip** these entities and not auto-attack them.
Useful for protecting villagers, your own pets, or other entities you don't want to accidentally hit.

---

## Configuration

All functions can be toggled in real-time via the in-game **Mod Configuration Interface** (Mods → ValidAttack → Config), or by directly editing `valid_attack-client.toml`:

### Intercept Incomplete Charge Click
interceptClick = true

### Auto Attack with Hold
autoAttackHold = true

### Attack Only When Target Required
requireTarget = false

### Aim Assist
aimAssist = false

### Aim Assist Blacklist (entity ID list)
aimAssistBlacklist = ["minecraft:villager", "minecraft:iron_golem"]
