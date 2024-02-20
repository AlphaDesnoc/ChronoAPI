package com.chrono.chronoapi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class EventDisableDefaultCommand implements Listener
{
    private final List<String> disabledCommands;

    public EventDisableDefaultCommand()
    {
        this.disabledCommands = Arrays.asList(
            "team",
            "teammsg",
            "op"
        );
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event)
    {
        final String command = event.getMessage().split(" ")[0];

        this.disabledCommands.forEach((commands) -> {
            if (command.equalsIgnoreCase("/" + commands)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("Cette commande est interdite !");
            }
        });
    }

    @EventHandler
    public void onCommandSend(final PlayerCommandSendEvent event)
    {
        final Collection<String> commands = event.getCommands();
        commands.removeAll(this.disabledCommands);
    }
}
