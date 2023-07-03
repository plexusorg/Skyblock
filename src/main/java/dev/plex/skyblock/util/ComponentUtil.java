package dev.plex.skyblock.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 7:42 PM [02-07-2023]
 */
public class ComponentUtil {
    private static final MiniMessage DEFAULT_MINI = MiniMessage.miniMessage();

    public static Component mini(String input) {
        return DEFAULT_MINI.deserialize(input);
    }
}
