package com.chrono.chronoapi.builders.gui.opener;

import com.chrono.chronoapi.builders.gui.ChronoGui;
import com.chrono.chronoapi.builders.gui.GuiManager;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public final class ChestInventoryOpener implements GuiOpener
{

    @Override
    public Inventory open(ChronoGui inv, Player player)
    {
        Preconditions.checkArgument(inv.getColumns() == 9,
                "The column count for the chest inventory must be 9, found: %s.", inv.getColumns());
        Preconditions.checkArgument(inv.getRows() >= 1 && inv.getRows() <= 6,
                "The row count for the chest inventory must be between 1 and 6, found: %s", inv.getRows());

        final GuiManager manager = inv.getManager();
        final Inventory handle = Bukkit.createInventory(player, inv.getRows() * inv.getColumns(), inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type)
    {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }

}