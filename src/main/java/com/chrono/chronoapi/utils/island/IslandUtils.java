package com.chrono.chronoapi.utils.island;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import com.chrono.chronoapi.utils.structures.islands.IslandStructure;
import com.chrono.chronoapi.utils.structures.WorldGenerator;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public final class IslandUtils
{
    private static final ChronoAPI api = ChronoAPI.getAPI();
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static File islandConfigFile;
    private static FileConfiguration islandConfig;

    public static void createIslandConfig()
    {
        islandConfigFile = new File(api.getDataFolder(), "island.yml");
        if (!islandConfigFile.exists()) {
            islandConfigFile.getParentFile().mkdirs();
            api.saveResource("island.yml", false);
        }

        islandConfig = new YamlConfiguration();
        try {
            islandConfig.load(islandConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void createIsland(final Player player)
    {
        final WorldGenerator worldGenerator = new WorldGenerator();

        logger.success(player, "Creation de l'ile en cours !");

        final World world = worldGenerator.createWorld(player.getName() + "_" + player.getUniqueId().toString());
        final WorldBorder worldBorder = world.getWorldBorder();
        final Location loc = new Location(world, 0, 0, 0);

        worldBorder.setCenter(loc);
        worldBorder.setSize(56);
        api.getStructuresGenerator().generate(player, new IslandStructure(), loc);
        teleportPlayerToIsland(player);
    }

    public static boolean hasIsland(final Player player)
    {
        return Bukkit.getWorld(player.getName() + "_" + player.getUniqueId().toString()) != null;
    }

    public static void teleportPlayerToIsland(final Player player)
    {
        player.teleport(new Location(Bukkit.getWorld(player.getName() + "_" + player.getUniqueId().toString()), 0, 4, -1));
    }

    public static World loadWorld(final String name)
    {
        return Bukkit.createWorld(WorldCreator.name(name));
    }

    public static void unloadWorld(final String name)
    {
        final World world = Bukkit.getWorld(name);
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public static File getIslandConfigFile()
    {
        return islandConfigFile;
    }

    public static FileConfiguration getIslandConfig()
    {
        return islandConfig;
    }
}
