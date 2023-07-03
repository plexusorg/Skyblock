package dev.plex.skyblock.hook;

import dev.plex.skyblock.Skyblock;

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
