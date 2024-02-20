package com.chrono.chronoapi.cache;

import com.chrono.chronoapi.utils.gradient.GradientTextBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.math.BigDecimal;

public final class DataRank
{
    private final int id;
    private final String name;
    private final String prefix;
    private final String firstHexa;
    private final String secondHexa;
    private final String coloredPrefix;
    private final BigDecimal price;
    private final Team team;

    public DataRank(final int id, final String name, final String prefix, final String firstHexa, final String secondHexa, final BigDecimal price)
    {
        this.id = id;
        this.name = name;
        this.firstHexa = firstHexa;
        this.secondHexa = secondHexa;
        this.prefix = prefix;
        this.price = price;
        this.coloredPrefix = new GradientTextBuilder().text(prefix).blur(0.2).addColor(firstHexa).addColor(secondHexa).build().getRenderedText();

        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        final String teamName = "\u2063".repeat(Math.max(0, 100 - this.id)) + this.id + "_" + this.name;
        final Team tempTeam = scoreboard.getTeam(teamName);

        if (tempTeam != null) {
            this.team = tempTeam;
        } else {
            this.team = scoreboard.registerNewTeam(teamName);
            this.team.setPrefix(this.coloredPrefix + " ");
        }
    }

    public Team getTeam()
    {
        return team;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getFirstHexa()
    {
        return firstHexa;
    }

    public String getSecondHexa()
    {
        return secondHexa;
    }

    public String getColoredPrefix()
    {
        return coloredPrefix;
    }

    public void addPlayer(final Player player)
    {
        this.team.addEntry(player.getName());
    }
}