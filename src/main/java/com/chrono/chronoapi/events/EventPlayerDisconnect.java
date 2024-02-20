package com.chrono.chronoapi.events;

import com.chrono.chronoapi.utils.island.IslandUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class EventPlayerDisconnect implements Listener
{
    @EventHandler
    public void onDisconnect(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        final String worldName = player.getName() + "_" + player.getUniqueId().toString();
        final World world = Bukkit.getWorld(worldName);
        if (world != null) {
            IslandUtils.unloadWorld(worldName);
        }
    }

}
