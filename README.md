# AlphaCore

A core plugin framework for Bukkit/Spigot Minecraft servers — the shared
library my other plugins hook into, so commands, menus, player data, and
effects only had to be written once.

AlphaCore is not a gameplay plugin. Installed on its own it adds almost
nothing a player would notice, apart from a few utility commands. Its job
is everything underneath: a plugin/module lifecycle, a command system,
persistent per-player stats, inventory GUIs, rich JSON chat, status
effects, regions, and the utility code (SQL, YAML, JSON, reflection/NMS)
that Bukkit plugins of the era kept re-implementing by hand.

Written in 2015 against Bukkit 1.7.10 / Spigot (Java 8, Maven).
196 Java files, ~10,800 lines, 64 commits (January–August 2015).

Known use: my [Splegg](https://github.com/FilipDev/Splegg) plugin
declares AlphaCore as a dependency in its `plugin.yml`, depends on it
in its `pom.xml`, and builds on its `CorePlayer`, inventory-menu, and
`ItemBuilder` classes. Other projects from the same period reused
individual AlphaCore classes by copying them in, rather than depending
on the framework — adoption was real, but partial.

> **Status: archived.** This is a historical project and is not
> maintained. It targets Minecraft 1.7.10-era Bukkit/Spigot APIs and
> version-specific NMS internals, so it will not compile or run against
> modern Paper/Spigot without porting. It is kept public as a record of
> the framework, not as a drop-in download for current servers.

## Why it exists

Every Bukkit plugin in 2015 re-solved the same problems: parsing
commands, checking permissions and cooldowns, building inventory menus
out of raw clicks, storing per-player numbers, making clickable chat
messages, and reaching into server internals the API didn't expose.

AlphaCore centralises all of that. A gameplay plugin depends on
AlphaCore, registers its pieces as *modules* under AlphaCore's
*managers*, and gets the rest for free — including clean-up: when a
plugin is disabled, its modules are unloaded with it.

## How it works

```
Core (JavaPlugin)
  └─ ManagerHandler          scans AlphaCore's own JAR at startup,
                             finds every Manager class, and loads them
                             in @Priority (LoadPriority) order
       ├─ CommandManager     commands and subcommands
       ├─ PlayerManager      CorePlayer wrappers
       ├─ InventoryManager   menus
       ├─ WorldManager       worlds and their properties
       └─ …                  one manager per subsystem below

  Module / ManagerModule     anything registerable — a command, a menu,
                             an effect, a player wrapper… Each module
                             knows its owning plugin (via
                             Core.getOwner()), so managers can unload
                             exactly one plugin's modules when it stops.
```

On enable, `Core` creates the `ManagerHandler`, which reflectively
discovers managers rather than hard-coding a list, registers
AlphaCore's listeners, and starts the lag `Watcher`.

## What's inside

### Commands (`commands/`)
- Declare a command as a class annotated with `@CommandMeta`
  (name, aliases, description) and extending `Command`, or build one
  fluently with `CommandBuilder`.
- Subcommands (`addSubCommands(...)`), `-key value` argument
  modifiers, tab-completion of subcommands and player names.
- Permissions and cooldowns checked before a listener runs, with
  failures surfaced as standard error messages instead of stack traces;
  console-vs-player restrictions per listener; cancellable
  `CommandRunEvent`.
- Built-in commands: `/alphacore` (alias `/ac`) with `help`,
  `modules`, `modules reload` and `modules unload` subcommands,
  `/active` (active abilities/effects), and `/trackers`.

### Players and data (`players/`)
- `CorePlayer` wraps Bukkit's `Player`: arbitrary attributes, the
  player's current `Place`, active effects, client version, and direct
  access to the underlying NMS player/connection.
- **Trackers** — named integer stats per player. Changing a value fires
  a cancellable `TrackerValueChangeEvent`; reaching a configured value
  fires its `MileStone`s. Trackers persist through YAML player data
  (`PlayerData` + `AutoSaver`), and `SQLTracker` provides a
  SQL-backed variant.
- Pre-made `/trackers` command displays a player's tracker values.

### Inventory GUIs (`inventory/`)
- `Menu` / `InventoryMenu` (plus hopper and dispenser variants) turn
  raw inventory clicks into placed `Element`s at grid `Coordinate`s.
- Element types: plain, `ClickableElement`, `ToggleableElement`,
  `AnimatedElement`, with click/toggle/update listeners and ready-made
  back, exit, and transfer buttons.
- Menus are identified by an invisible ID encoded in the inventory
  title (`InvisibleID`), so clicks can be routed to the right menu
  without a visible marker. Create one with
  `Menu.createInventoryMenu(name, rows, menu -> { … })`.

### Messages (`messages/`)
- A JSON chat system (`JSONMessage`, `JSONMessageBuilder`,
  `MessagePart`) for messages with click and hover events — run a
  command, open a link, show a tooltip — assembled in code and sent via
  `CorePlayer.sendJSON(...)`. Templates live in
  `src/main/resources/json/`.
- Standard `ChatMessage` / `ErrorMessage` / `WarningMessage` enums for
  consistent server-wide wording.

### Effects and combat (`effects/`, `combat/`, `emitters/`)
- `Effect` is an abstract status effect with a duration, levels, and
  properties: subclass it, implement `onApply` / `onUpdate` /
  `onRemove`, and application/removal fire cancellable events. Effects
  tick through the central `UpdateEvent` system.
- Combat scaffolding (`Fight`, `AttackEvent`, `AttackMethod`) and
  particle/sound `Emitter`s attachable to entities or locations.

### Places, worlds, and interaction (`places/`, `worlds/`, `border/`,
`blocks/`, `entities/`, `tools/`, `group/`)
- **Places** — named regions (physical or abstract) that track who is
  inside and fire join/leave/move events; trackers can be displayed
  per-place.
- **Worlds** — `CoreWorld` / `WorldManager` with per-world properties;
  circular and square world `Border`s.
- Clickable blocks, interactable entities, custom `Tool`s with
  registration events, and player `Group`s.

### Engine and utilities (`core/`, `scheduler/`, `updater/`,
`listeners/`, `lagwatcher/`, `utils/`)
- A tick scheduler and `UpdateManager` emitting typed `UpdateEvent`s;
  optimised player-move and damage listeners; a TPS/lag `Watcher`.
- Utilities the rest of the framework is built on: reflection helpers
  and NMS/packet access (`utils/reflection/`, `utils/commonnms/`,
  `UnsafeBukkitClasses`), SQL connections and tables, YAML
  reader/writer, JSON builders, file builders, date/time types, and
  string helpers — including `InvisibleEncoder`/`InvisibleID`, which
  hide machine-readable IDs inside colour-coded strings.

## Using it in another plugin (2015-era)

1. Put the AlphaCore jar on the server alongside your plugin, and
   declare the dependency in your `plugin.yml`:

   ```yaml
   depend: [AlphaCore]
   ```

2. Add AlphaCore as a Maven dependency (it was never published to a
   public repository, so build it from this repo and install it
   locally first — see *Building*):

   ```xml
   <dependency>
       <groupId>me.pauzen.alphacore</groupId>
       <artifactId>AlphaCore</artifactId>
       <version>1.0-SNAPSHOT</version>
       <scope>provided</scope>
   </dependency>
   ```

3. Register your pieces as modules instead of wiring them by hand.
   A command, for example, is just a class:

   ```java
   @CommandMeta(value = "hello", aliases = {"hi"},
                description = "Says hello.")
   public class HelloCommand extends Command {

       @Override
       public CommandListener getDefaultListener() {
           return new CommandListener((sender, modifiers, args) -> {
               sender.sendMessage("Hello!");
           }, true); // true = console may run it too
       }
   }
   ```

   ```java
   // Register it with AlphaCore's command manager:
   CommandManager.getManager().registerModule(new HelloCommand());
   ```

   Menus, effects, tools, and player wrappers follow the same pattern:
   implement the base type, register the instance with its manager,
   and AlphaCore tracks which plugin owns it.

## Building

Requires a JDK 8 and Maven, plus the legacy dependencies declared in
`pom.xml` in your local Maven repository:

- `org.bukkit:bukkit:1.7.10-R0.1`
- `org.spigotmc:spigot:1649_Patch20141001a` — a patched Spigot server
  build that was distributed as a jar, not via a public Maven repo;
  install it locally with `mvn install:install-file` before building.

```bash
mvn package
```

The resulting jar is a normal Bukkit plugin
(`main: me.pauzen.alphacore.Core`, see `src/main/resources/plugin.yml`)
and goes in a 1.7.10-era server's `plugins/` folder.

### Build verification (November 2026)

This was re-checked in 2026, because neither dependency above is
published anywhere anymore — Maven Central, the Spigot Nexus, and
current BuildTools all start at 1.8. Reconstructing them from official
sources (the Bukkit API rebuilt from the official source repo's final
1.7.10 commit; the one server-jar type AlphaCore references supplied
from Mojang's official 1.7.10 server jar):

- **195 of the 196 source files in the 2015 code compile clean on JDK 8.**
- **As left in 2015, the final revision did not compile.**
  `places/actions/PhysicalAction.java`, added in the last commit
  (August 2015), references
  `org.bukkit.event.entity.EntitySpawnEvent` — a class that was only
  added to the Bukkit API in December 2018.
- Changing that one symbol to the 1.7.10-era `CreatureSpawnEvent`
  (two lines: the import and the enum constant) makes the whole
  project build: `BUILD SUCCESS`, producing
  `AlphaCore-1.0-SNAPSHOT.jar` (263 KB, 215 class files, `plugin.yml`
  included). Note the substitution is narrower than the original
  intent — `CreatureSpawnEvent` covers creature spawns, where the
  2018 `EntitySpawnEvent` covers all entities.

Running the plugin on a server has not been tested.

## Project layout

```
src/main/java/me/pauzen/alphacore/
  Core.java            plugin entry point
  core/                ManagerHandler, Manager / Module system
  commands/            command framework + built-in commands
  players/             CorePlayer, player data, trackers
  inventory/           inventory GUI menus and elements
  messages/            chat and JSON message system
  effects/  combat/    status effects, abilities, fights
  places/  worlds/     regions, worlds, borders
  blocks/  entities/   clickable blocks, interactable entities
  tools/  emitters/    custom tools, particle/sound emitters
  scheduler/  updater/ tick scheduling and update events
  listeners/           Bukkit listener plumbing
  lagwatcher/          server performance watcher
  utils/               SQL, YAML, JSON, reflection/NMS, misc helpers
src/main/resources/
  plugin.yml
  json/                message templates (help, events, notifications)
```

## Author

Filip Pauzenberger — written in 2015, while building and running
Minecraft servers and developing Bukkit plugins.
