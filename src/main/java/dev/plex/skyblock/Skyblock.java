package dev.plex.skyblock;

import dev.plex.hook.SlimeWorldHook;
import dev.plex.listener.PlayerListener;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;

@Accessors(fluent = true)
public final class Skyblock extends JavaPlugin {

    @Getter
    private static Skyblock plugin;

    @Getter
    private final SlimeWorldHook slimeWorldHook = new SlimeWorldHook();

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        slimeWorldHook.onEnable(this);
    }

    @Override
    public void onDisable() {
        slimeWorldHook.onDisable(this);
    }
}
