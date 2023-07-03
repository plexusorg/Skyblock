package dev.plex.skyblock.hook;

import com.google.common.collect.Sets;
import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import dev.plex.skyblock.Skyblock;
import dev.plex.skyblock.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 7:36 PM [02-07-2023]
 */
public class SlimeWorldHook implements IHook<SlimePlugin> {

    private static final Component WORLD_NOT_FOUND = ComponentUtil.mini("<red>This world could not be found!");
    private static final Component STORAGE_FAILURE = ComponentUtil.mini("<red>This world cannot be stored!");

    private final Set<String> LOADED_WORLDS = Sets.newHashSet();

    private SlimeLoader loader;


    @Override
    public void onEnable(Skyblock plugin) {
        if (plugin() == null) {
            plugin.getComponentLogger().error(Component.text("Cannot find SlimeWorldManager plugin").color(NamedTextColor.RED));
            return;
        }

        plugin.getComponentLogger().info(ComponentUtil.mini("<green>Enabling SWM Hook"));

        this.loader = plugin().getLoader("mysql");
    }

    @Override
    public void onDisable(Skyblock plugin) {
        plugin.getComponentLogger().info(ComponentUtil.mini("<green>Disabling SWM Hook"));
        AtomicInteger i = new AtomicInteger();
        LOADED_WORLDS.forEach(s -> {
            final World world = Bukkit.getWorld(s);
            if (world != null) {
                world.save();
                i.getAndIncrement();
            }
        });
        plugin.getComponentLogger().info(ComponentUtil.mini("<green>SWM Hook saved " + i.get() + " worlds"));
    }

    public World createPlayerWorld(UUID uuid) {
        final SlimePropertyMap slimePropertyMap = new SlimePropertyMap();
        slimePropertyMap.setValue(SlimeProperties.PVP, false);

        boolean newWorld = false;
        try {
            slimePropertyMap.setValue(SlimeProperties.SPAWN_X, 0);
            slimePropertyMap.setValue(SlimeProperties.SPAWN_Y, 130);
            slimePropertyMap.setValue(SlimeProperties.SPAWN_Z, 0);
            final SlimeWorld slimeWorld = this.plugin().createEmptyWorld(this.loader, uuid.toString(), false, slimePropertyMap);
            this.plugin().loadWorld(slimeWorld);
            newWorld = true;
        } catch (WorldAlreadyExistsException e) {

            try {
                SlimeWorld world = this.plugin().loadWorld(this.loader, uuid.toString(), false, slimePropertyMap);
                this.plugin().loadWorld(world);
                this.loader.unlockWorld(uuid.toString());
            } catch (WorldLockedException | CorruptedWorldException | NewerFormatException | UnknownWorldException |
                     IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException e) {
            Skyblock.plugin().getComponentLogger().error(STORAGE_FAILURE);
        }

        final World world = Bukkit.getWorld(uuid.toString());
        if (world == null) {
            Skyblock.plugin().getComponentLogger().error(WORLD_NOT_FOUND);
            return null;
        }
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setSpawnLocation(0, 130, 0);
        world.setAutoSave(true);

        if (newWorld) {
            world.getBlockAt(0, 128, 0).setType(Material.STONE);
        }

        LOADED_WORLDS.add(uuid.toString());

        return world;
    }


    @Override
    public SlimePlugin plugin() {
        return (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    }
}
