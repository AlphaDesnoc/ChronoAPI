package com.chrono.chronoapi.utils.logger;

import com.chrono.chronoapi.utils.gradient.GradientTextBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.logging.Level;

public final class PluginLogger
{
    private final String pluginName;

    public PluginLogger(final String pluginName)
    {
        this.pluginName = pluginName;
        AnsiConsole.systemInstall();
    }

    public void log(final Level level, final String message, final Ansi.Color color)
    {
        Bukkit.getLogger().log(level, formatMessage(message, color));
    }

    private String formatMessage(final String message, final Ansi.Color color) {
        return Ansi.ansi().fg(color).a( "[" + pluginName + "] " + message).reset().toString();
    }

    private String getPrefix()
    {
        final String prefix = new GradientTextBuilder()
            .text(pluginName)
            .blur(0.1)
            .addColor("#a18cd1")
            .addColor("#fbc2eb")
            .build().getRenderedText();

        return ChatColor.GRAY + "[" + prefix + ChatColor.GRAY + "] ";
    }

    public void success(final Player player, final String successMessage)
    {
        player.sendMessage(getPrefix() +
                new GradientTextBuilder()
                    .text(successMessage)
                    .blur(0.3)
                    .addColor("#00FF00")
                    .addColor("#cacae2")
                    .build().getRenderedText()
        );
    }

    public void warning(final Player player, final String errorMessage)
    {
        player.sendMessage(getPrefix() +
                new GradientTextBuilder()
                    .text(errorMessage)
                    .blur(0.3)
                    .addColor("#FF0000")
                    .addColor("#FFFF00")
                    .build().getRenderedText()
        );
    }

    public void info(final String message)
    {
        log(Level.INFO, message, Ansi.Color.GREEN);
    }

    public void warning(final String message)
    {
        log(Level.WARNING, message, Ansi.Color.YELLOW);
    }

    public void severe(final String message)
    {
        log(Level.SEVERE, message, Ansi.Color.RED);
    }
}
