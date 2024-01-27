# MelonBTA Command Utilities

Just a small mod I (Prophet/Master) started to accomplish a few things:

1. Give back to a community I enjoy
2. Get better with the Fabric / Babric Modding APIs

## Main Folder Hierarchy
```
└── prophetsama/testing/
    ├── commands/
    │   ├── Compass.java      Gives player Compass
    │   ├── Kit.java     Expansive Kit Creation
    │   ├── Kitten.java     50/50 Kitten Spawner lol
    │   ├── Spawn.java     Hardcoded Spawn Command to tp to MelonSurvival Spawn (DO NOT RELEASE)
    │   ├── Vanish.java     Vanishes Opped Players from sight and mind
    │   └── WhereAmI.java     Shows players coords in chat
    └── config/
    │   ├── ConfigManager.java     Creates Config Files
    │   └── KitData.java     File Layout for Kit Files
    └── mixin/
    │   └── gamerules/
    │   │   └── FireTickMixin.java
    │   │   └── NetLoginHandlerMixin.java
    │   │   └── WorldClientMixin.java
    │   │   └── WorldDaylightCycleMixin.java
    │   │   └── WorldServerDaylightCycle.java
    │   └── vanish/
    │       └── EntityPlayerMixin.java
    │       └── EntityTrackerMixin.java
    │       └── OtherPlayerMixin.java
    │       └── PlayerListMixin.java
    └── mixininterfaces/
    │   └── IVanish.java
    └── MelonBTACommands.java     Injects the new commands
```

Shoutout to these communities and people 
- BetterThanAdventure Modding Discord
- ModdingStation Discord
- MelonBTA
