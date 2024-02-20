package com.chrono.chronoapi.database.requests.load;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataRank;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class LoadRequest
{

    private static final ChronoAPI api = ChronoAPI.getAPI();
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static final DatabaseManager databaseManager = ChronoAPI.getDatabaseManager();

    public static void loadRequest()
    {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        scoreboard.getTeams().forEach(Team::unregister);

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM RANKS");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String name = resultSet.getString("name");
                final String prefix = resultSet.getString("prefix");
                final String firstHexa = resultSet.getString("firstHexa");
                final String secondHexa = resultSet.getString("secondHexa");
                final BigDecimal price = resultSet.getBigDecimal("price");

                api.getRankData().put(id, new DataRank(id, name, prefix, firstHexa, secondHexa, price));
            }
        } catch (SQLException exception) {
            logger.severe("Erreur durant le chargement des ranks: " + exception.getMessage());
        }
    }

}
