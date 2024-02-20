package com.chrono.chronoapi.utils.structures;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public final class WorldGenerator extends ChunkGenerator {

    public World createWorld(final String worldName)
    {
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            final WorldCreator worldCreator = new WorldCreator(worldName);
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.generator(this);
            world = Bukkit.createWorld(worldCreator);
        }
        return world;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        return createChunkData(world);
    }
}
