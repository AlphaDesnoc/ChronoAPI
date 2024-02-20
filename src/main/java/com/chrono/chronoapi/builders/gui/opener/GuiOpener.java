package com.chrono.chronoapi.builders.gui.opener;

import com.chrono.chronoapi.builders.gui.ChronoGui;
import com.chrono.chronoapi.builders.gui.ClickableItem;
import com.chrono.chronoapi.builders.gui.content.GuiContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface GuiOpener
{

    Inventory open(ChronoGui inv, Player player);
    boolean supports(InventoryType type);

    default void fill(Inventory handle, GuiContents contents)
    {
        ClickableItem[][] items = contents.all();

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null)
                    handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }

}