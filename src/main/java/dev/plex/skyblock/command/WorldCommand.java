package dev.plex.skyblock.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.bukkit.parsers.WorldArgument;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 9:29 PM [02-07-2023]
 */

public class WorldCommand {

    public static void register(PaperCommandManager<CommandSender> commandManager) {
        commandManager.command(
                commandManager.commandBuilder("goto", ArgumentDescription.of("Teleports you to a world"), "gotoworld", "world", "tpworld")
                        .argument(WorldArgument.of("world"))
                        .senderType(Player.class)
                        .permission("skyblock.command.goto")
                        .handler(commandContext -> {
                            final World world = commandContext.get("world");
                            ((Player) commandContext.getSender()).teleportAsync(world.getSpawnLocation());
                        })
        );

        commandManager.command(
                commandManager.commandBuilder("goto", ArgumentDescription.of("Teleports a player to a world"), "gotoworld", "world", "tpworld")
                        .argument(WorldArgument.of("world"))
                        .argument(PlayerArgument.of("player"))
                        .permission("skyblock.command.goto.other")
                        .handler(commandContext -> {
                            final World world = commandContext.get("world");
                            ((Player) commandContext.get("player")).teleportAsync(world.getSpawnLocation());
                        })
        );
    }

}
