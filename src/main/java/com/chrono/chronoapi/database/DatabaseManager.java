package com.chrono.chronoapi.database;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.database.requests.load.LoadDiscordPlayer;
import com.chrono.chronoapi.database.requests.load.LoadPlayerRequest;
import com.chrono.chronoapi.database.requests.load.LoadRequest;
import com.chrono.chronoapi.database.requests.save.SaveRequest;
import com.chrono.chronoapi.database.requests.table.TablesRequest;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import org.bukkit.entity.Player;

import java.sql.*;

public final class DatabaseManager
{
    private final PluginLogger logger = ChronoAPI.getPluginLogger();
    private Connection connection;
    private final DatabaseCredits credits;

    public DatabaseManager(final DatabaseCredits credits)
    {
        this.credits = credits;
    }

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + credits.getHostname() + "/" + credits.getBDD(), credits.getUser(), credits.getPassword());
            logger.info("Connexion a la base de donnee etablie.");
            this.createTables();
        } catch (ClassNotFoundException | SQLException exception) {
            logger.severe("Erreur durant la connexion a la base de donnee: " + exception.getMessage());
        }
    }

    private void createTables()
    {
        TablesRequest.createTables();
        this.load();
    }

    private void load()
    {
        LoadRequest.loadRequest();
    }

    public void loadPlayer(final Player player)
    {
        LoadPlayerRequest.loadPlayer(player);
    }

    public DataPlayer loadDiscordPlayer(final String name)
    {
        return LoadDiscordPlayer.loadDiscordPlayer(name);
    }

    public void save()
    {
        SaveRequest.save();
    }

    public void close()
    {
        try {
            logger.info("Deconnexion de la base de donnee.");
            connection.close();
        } catch (SQLException exception) {
            logger.severe("Erreur durant la déconnexion a la base de donnee: " + exception.getMessage());
        }
    }

    public Connection getConnection()
    {
        return connection;
    }
}