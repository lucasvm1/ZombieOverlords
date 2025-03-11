# Zombie Overlords

A Minecraft Forge mod that transforms the zombie apocalypse experience. This mod makes zombies a constant threat by allowing them to survive in daylight and converting all monster spawners to zombie spawners.

## Features

- **Sunlight Immunity**: Zombies no longer burn in daylight
- **Spawner Conversion**: All discovered spawners are automatically converted to zombie spawners
- **Persistent Threat**: Creates a challenging survival experience where zombies are always a danger

## Technical Details

- Built for Minecraft 1.20.1
- Requires Forge 47+
- Includes custom game rule `debugSpawner` for notification control

## Installation

1. Install Minecraft Forge 1.20.1-47.x.x
2. Download the latest release from [CurseForge/Modrinth]
3. Place the .jar file in your Minecraft mods folder
4. Launch Minecraft with the Forge profile

## Development

This mod was developed using Forge MDK for Minecraft 1.20.1. To set up a development environment:

```bash
./gradlew setupDecompWorkspace
./gradlew eclipse    # For Eclipse
./gradlew genIntellijRuns    # For IntelliJ IDEA
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Credits

Created by Lucas VM
