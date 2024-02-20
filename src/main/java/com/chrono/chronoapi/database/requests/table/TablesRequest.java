package com.chrono.chronoapi.database.requests.table;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.logger.PluginLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class TablesRequest
{
    private static final ChronoAPI api = ChronoAPI.getAPI();
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static final DatabaseManager databaseManager = ChronoAPI.getDatabaseManager();

    public static void createTables()
    {
        final Connection connection = databaseManager.getConnection();

        try {
            final PreparedStatement createTablePlayers = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS PLAYERS" +
                            "(uuid VARCHAR(255), " +
                            " money BIGINT, " +
                            " rank INTEGER, " +
                            " isPermBan TINYINT, " +
                            " isTempBan TINYINT, " +
                            " banTime VARCHAR(255), " +
                            " banReason VARCHAR(255), " +
                            " isMute TINYINT, " +
                            " muteTime VARCHAR(255), " +
                            " muteReason VARCHAR(255), " +
                            " isFreeze TINYINT, " +
                            " isVanish TINYINT) "
            );

            final PreparedStatement createTableRanks = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS RANKS" +
                            "(id INTEGER, " +
                            " name VARCHAR(255), " +
                            " price BIGINT, " +
                            " prefix VARCHAR(255), " +
                            " firstHexa VARCHAR(255), " +
                            " secondHexa VARCHAR(255)) "
            );

            createTablePlayers.executeUpdate();
            createTableRanks.executeUpdate();
            logger.info("Les tables ont bien ete chargees");
        } catch (SQLException exception) {
            logger.severe("Erreur durant la creation des tables: " + exception.getMessage());
        }
    }
}
