package com.chrono.chronoapi.utils.structures;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class StructuresGenerator
{
    public void generate(final Player player, final Structure structure, final Location loc)
    {
        structure.generateStructure(player, loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    public void generate(final Player player, final Structure structure, final World world, final int x, final int y, final int z)
    {
        structure.generateStructure(player, world, x, y, z);
    }

}
