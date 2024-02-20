package com.chrono.chronoapi.utils.structures;

import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class Structure
{
    protected abstract void generateStructure(final Player player, final World world, final int x, final int y, final int z);
}
