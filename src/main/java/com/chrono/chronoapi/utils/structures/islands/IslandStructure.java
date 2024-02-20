package com.chrono.chronoapi.utils.structures.islands;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import com.chrono.chronoapi.utils.island.IslandUtils;
import com.chrono.chronoapi.utils.structures.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public final class IslandStructure extends Structure
{
    private final PluginLogger logger = ChronoAPI.getPluginLogger();
    private final YamlConfiguration islandConfig = YamlConfiguration.loadConfiguration(IslandUtils.getIslandConfigFile());

    @Override
    public void generateStructure(final Player player, final World world, final int x, final int y, final int z)
    {
        final ConfigurationSection blocksSection = islandConfig.getConfigurationSection("blocks");
        if (blocksSection != null) {
            final int nbBlock = blocksSection.getKeys(false).size();
            int counter = 0;
            for (final String key : blocksSection.getKeys(false)) {
                final int x1 = x + blocksSection.getInt(key + ".x");
                final int y1 = y + blocksSection.getInt(key + ".y");
                final int z1 = z + blocksSection.getInt(key + ".z");
                final Material material = Material.valueOf(blocksSection.getString(key + ".material"));
                final Block block = world.getBlockAt(new Location(world, x1, y1, z1));
                block.setType(material);
                counter++;
                if(counter == nbBlock/4) {
                    logger.success(player, "Génération de l'ile : 25%");
                }
                else if (counter == nbBlock/2) {
                    logger.success(player, "Génération de l'ile : 50%");
                } else if (counter == (nbBlock/4)*3) {
                    logger.success(player, "Génération de l'ile : 75%");
                }
            }

            logger.success(player, "Votre île a été générée !");
            player.teleport(new Location(world, x, y+4, z));
        }
    }

}