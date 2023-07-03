package dev.plex.hook;

import dev.plex.skyblock.Skyblock;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 7:36 PM [02-07-2023]
 */
public interface IHook<T> {

    void onEnable(Skyblock plugin);

    void onDisable(Skyblock plugin);

    T plugin();
}
