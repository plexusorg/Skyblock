package dev.plex.listener;

import dev.plex.skyblock.Skyblock;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 7:58 PM [02-07-2023]
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(Skyblock.plugin(), () -> {
            final World world = Skyblock.plugin().slimeWorldHook().createPlayerWorld(event.getPlayer().getUniqueId());
            if (world == null) return;
            event.getPlayer().teleportAsync(world.getSpawnLocation());
        }, 5);
    }
}
