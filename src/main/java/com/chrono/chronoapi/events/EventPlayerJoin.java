
package com.chrono.chronoapi.events;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.island.IslandUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class EventPlayerJoin implements Listener
{
    private final DatabaseManager databaseManager = ChronoAPI.getDatabaseManager();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final String worldName = player.getName() + "_" + player.getUniqueId();
        databaseManager.loadPlayer(player);

        if (Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))) {
            IslandUtils.loadWorld(worldName);
        }
    }
}