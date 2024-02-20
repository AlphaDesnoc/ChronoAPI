package com.chrono.chronoapi.builders.gui.content;

import org.bukkit.entity.Player;

public interface GuiProvider
{

    void init(Player player, GuiContents contents);
    default void update(Player player, GuiContents contents) {}

}
