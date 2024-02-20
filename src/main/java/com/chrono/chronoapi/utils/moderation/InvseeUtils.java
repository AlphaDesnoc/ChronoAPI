package com.chrono.chronoapi.utils.moderation;

import com.chrono.chronoapi.ChronoAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class InvseeUtils
{
    private static final ChronoAPI api = ChronoAPI.getAPI();

    public static Inventory getInvsee(final Player target)
    {
        final String targetName = target.getName();

        Inventory targetInventory = api.getWatchedInventories().getIfPresent(targetName);
        if (targetInventory == null){
            api.getWatchedInventories().put(targetName, Bukkit.createInventory(null, 9*5, "Inventaire de " + targetName));
            targetInventory = api.getWatchedInventories().getIfPresent(targetName);
        }

        int i = 0;
        for (final ItemStack item : target.getInventory().getContents()) {
            targetInventory.setItem(i, item);
            i++;
        }

        return targetInventory;
    }

    public static Inventory getEnderchest(final Player target)
    {
        final String targetName = target.getName();

        Inventory targetInventory = api.getWatchedEnderchests().getIfPresent(targetName);
        if (targetInventory == null){
            api.getWatchedEnderchests().put(targetName, Bukkit.createInventory(null, 9*3, "Enderchest de " + targetName));
            targetInventory = api.getWatchedEnderchests().getIfPresent(targetName);
        }

        int i = 0;
        for (final ItemStack item : target.getEnderChest().getContents()) {
            targetInventory.setItem(i, item);
            i++;
        }

        return targetInventory;
    }
}
