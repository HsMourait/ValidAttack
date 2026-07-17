# ValidAttack

![NeoForge](https://img.shields.io/badge/NeoForge-1.21.1-orange)
![License](https://img.shields.io/badge/License-MIT-green)
![纯客户端](https://img.shields.io/badge/Side-Client--Only-blue)

**ValidAttack** 是一个轻量的 Minecraft 战斗辅助模组，围绕"有效攻击"这个功能作出了数个配置，使玩家可以更省心的发起适配MC战斗更新后的攻击。希望这可以保护您的鼠标和手。感谢您。

---

## 功能

### 1. 拦截未满蓄力点击
> 默认开启

当攻击冷却未满时，**直接取消攻击操作**。只有蓄力条达到 100% 才会发出攻击。

### 2. 按住攻击
> 默认开启

**长按**攻击键（左键），模组会自动在每次蓄力条满的瞬间发起攻击。无需反复连点，只要按住就能以最高频率打出满蓄力攻击。

### 3. 有效性检查
> 默认关闭

与**按住攻击**配合使用。开启后，只有当准星指向一个有效的目标时才会发动攻击。

- 单独使用时：检查准星路径上的单个实体。
- 配合瞄准辅助时：检查整个锥形扫描范围内是否有有效目标。

### 4. 更简单的攻击逻辑
> 默认关闭

模拟了一些游戏中更大范围的普通攻击。
在玩家面前 **120°的锥形区域**内扫描最近的活体目标。无需精确对准，模组会自动选择锥形范围内距离最近的目标发起攻击。其最大检查距离与玩家实际的攻击距离有关。

---

## 配置

所有功能均可通过游戏内 **Mod 配置界面**（Mods → ValidAttack → Config）实时开关，也可以直接编辑 `valid_attack-client.toml`：

### 拦截未满蓄力点击
interceptClick = true

### 按住自动攻击
autoAttackHold = true

### 需要目标才攻击
requireTarget = false

### 更简单的逻辑
aimAssist = false
