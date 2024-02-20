package com.chrono.chronoapi.events;

import com.chrono.chronoapi.utils.island.IslandUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.util.UUID;

public final class EventWorldCancelLoad implements Listener
{
    @EventHandler
    public void onWorldInit(final WorldInitEvent event)
    {
        final String worldName = event.getWorld().getName();
        final String[] playerUUID = worldName.split("_");

        if (!worldName.equals("lobby")) {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                final UUID uuid = UUID.fromString(playerUUID[1]);

                if (player.getUniqueId().equals(uuid)) {
                    return;
                }
            }

            IslandUtils.unloadWorld(worldName);
        }
    }
}
