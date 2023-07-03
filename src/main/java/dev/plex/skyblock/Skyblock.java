package dev.plex.skyblock;

import dev.plex.skyblock.command.CommandHandler;
import dev.plex.skyblock.hook.SlimeWorldHook;
import dev.plex.skyblock.listener.PlayerListener;
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
        try {
            new CommandHandler().setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        slimeWorldHook.onEnable(this);
    }

    @Override
    public void onDisable() {
        slimeWorldHook.onDisable(this);
    }
}
