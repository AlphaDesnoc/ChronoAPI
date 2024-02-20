package com.chrono.chronoapi;

import com.chrono.chronoapi.builders.item.ItemBuilder;
import com.chrono.chronoapi.cache.*;
import com.chrono.chronoapi.database.DatabaseCredits;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.handlers.EventHandler;
import com.chrono.chronoapi.builders.gui.GuiManager;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import com.chrono.chronoapi.utils.structures.StructuresGenerator;
import com.chrono.chronoapi.utils.island.IslandUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ChronoAPI extends JavaPlugin
{
    private static ChronoAPI plugin;
    private static PluginLogger logger;
    private static DatabaseManager databaseManager;
    private Cache<Integer, DataRank> rankData;
    private Cache<UUID, DataPlayer> playerData;
    private Cache<UUID, DataCooldown> chatCooldown;
    private Cache<UUID, DataStaff> staffData;
    private Cache<String, Inventory> watchedInventories;
    private Cache<String, Inventory> watchedEnderchests;
    private Cache<UUID, DataGuiChat> requestedChatPlayers;
    private Map<Integer, ItemStack> items;
    private StructuresGenerator structuresGenerator;
    private static GuiManager guiManager;

    @Override
    public void onEnable()
    {
        plugin = this;
        logger = new PluginLogger(this.getDescription().getName());

        rankData = Caffeine.newBuilder().build();
        playerData = Caffeine.newBuilder().build();
        chatCooldown = Caffeine.newBuilder().build();
        staffData = Caffeine.newBuilder().build();
        watchedInventories = Caffeine.newBuilder().build();
        watchedEnderchests = Caffeine.newBuilder().build();
        requestedChatPlayers = Caffeine.newBuilder().build();

        databaseManager = new DatabaseManager(new DatabaseCredits("localhost", "root", "", "chronosky", 25565));
        databaseManager.connect();

        IslandUtils.createIslandConfig();

        items = new HashMap<>();

        guiManager = new GuiManager(this);
        guiManager.init();

        structuresGenerator = new StructuresGenerator();

        for (final Player player : Bukkit.getOnlinePlayers()) { databaseManager.loadPlayer(player); }

        new EventHandler().register(plugin, "com.chrono.chronoapi.events");
    }

    @Override
    public void onDisable()
    {
        databaseManager.save();
        databaseManager.close();
    }

    public static ChronoAPI getAPI()
    {
        return plugin;
    }
    public static PluginLogger getPluginLogger()
    {
        return logger;
    }
    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public StructuresGenerator getStructuresGenerator()
    {
        return structuresGenerator;
    }

    public Cache<Integer, DataRank> getRankData()
    {
        return rankData;
    }
    public Cache<UUID, DataPlayer> getPlayerData()
    {
        return playerData;
    }
    public Cache<UUID, DataStaff> getStaffData()
    {
        return staffData;
    }
    public Cache<UUID, DataCooldown> getChatCooldown()
    {
        return chatCooldown;
    }
    public Cache<String, Inventory> getWatchedInventories()
    {
        return watchedInventories;
    }
    public Cache<String, Inventory> getWatchedEnderchests()
    {
        return watchedEnderchests;
    }
    public Cache<UUID, DataGuiChat> getRequestedChatPlayers()
    {
        return requestedChatPlayers;
    }
    public Map<Integer, ItemStack> getItems()
    {
        return items;
    }
    public GuiManager getGuiManager()
    {
        return guiManager;
    }
}