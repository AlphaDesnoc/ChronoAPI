package com.chrono.chronoapi.database.requests.save;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.cache.DataRank;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.logger.PluginLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SaveRequest
{
    private static final ChronoAPI api = ChronoAPI.getAPI();
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static final DatabaseManager databaseManager = ChronoAPI.getDatabaseManager();

    public static void save()
    {
        final Connection connection = databaseManager.getConnection();

        if (api.getRankData().asMap().isEmpty()) {
            return;
        }

        try {
            for (final DataRank rank : api.getRankData().asMap().values()) {
                final PreparedStatement rankStatement = connection.prepareStatement("SELECT * FROM RANKS WHERE id = ?");
                rankStatement.setInt(1, rank.getId());
                rankStatement.executeQuery();

                final ResultSet resultSet = rankStatement.executeQuery();

                if (resultSet.next()) {
                    final PreparedStatement updateStatement = connection.prepareStatement(
                            "UPDATE RANKS SET name = ?, prefix = ?, firstHexa = ?, secondHexa = ?, price = ?" +
                                    " WHERE id = ?"
                    );

                    updateStatement.setString(1, rank.getName());
                    updateStatement.setString(2, rank.getPrefix());
                    updateStatement.setString(3, rank.getFirstHexa());
                    updateStatement.setString(4, rank.getSecondHexa());
                    updateStatement.setBigDecimal(5, rank.getPrice());
                    updateStatement.setInt(6, rank.getId());

                    updateStatement.executeUpdate();
                } else {
                    final PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO RANKS (id, name, prefix, firstHexa, secondHexa, price) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)"
                    );

                    preparedStatement.setInt(1, rank.getId());
                    preparedStatement.setString(2, rank.getName());
                    preparedStatement.setString(3, rank.getPrefix());
                    preparedStatement.setString(4, rank.getFirstHexa());
                    preparedStatement.setString(5, rank.getSecondHexa());
                    preparedStatement.setBigDecimal(6, rank.getPrice());

                    preparedStatement.execute();
                }
            }

            for (final DataPlayer player : api.getPlayerData().asMap().values()) {
                final String uuid = player.getUuid().toString();
                final PreparedStatement selectPlayerStatement = connection.prepareStatement(
                        "SELECT * FROM PLAYERS WHERE uuid = ?"
                );

                selectPlayerStatement.setString(1, uuid);

                final ResultSet rsPlayers = selectPlayerStatement.executeQuery();

                if (rsPlayers.next()) {
                    final PreparedStatement updateStatement = connection.prepareStatement(
                            "UPDATE PLAYERS SET money = ?, rank = ?, isPermBan = ?," +
                                    "isTempBan = ?, banTime = ?, banReason = ?, isMute = ?, muteTime = ?," +
                                    "muteReason = ?, isFreeze = ?, isVanish = ?" +
                                    " WHERE uuid = ?"
                    );

                    updateStatement.setBigDecimal(1, player.getMoney());
                    updateStatement.setInt(2, player.getRank().getId());
                    updateStatement.setBoolean(3, player.isPermBan());
                    updateStatement.setBoolean(4, player.isTempBan());
                    updateStatement.setString(5, player.getBanTime());
                    updateStatement.setString(6, player.getBanReason());
                    updateStatement.setBoolean(7, player.isMute());
                    updateStatement.setString(8, player.getMuteTime());
                    updateStatement.setString(9, player.getMuteReason());
                    updateStatement.setBoolean(10, player.isFreeze());
                    updateStatement.setBoolean(11, player.isVanish());
                    updateStatement.setString(12, uuid);

                    updateStatement.executeUpdate();
                } else {
                    final PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO PLAYERS (uuid, money, rank, isPermBan, isTempBan, banTime, banReason, isMute, muteTime, muteReason, isFreeze, isVanish) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );

                    preparedStatement.setString(1, uuid);
                    preparedStatement.setBigDecimal(2, player.getMoney());
                    preparedStatement.setInt(3, player.getRank().getId());
                    preparedStatement.setBoolean(4, player.isPermBan());
                    preparedStatement.setBoolean(5, player.isTempBan());
                    preparedStatement.setString(6, player.getBanTime());
                    preparedStatement.setString(7, player.getBanReason());
                    preparedStatement.setBoolean(8, player.isMute());
                    preparedStatement.setString(9, player.getMuteTime());
                    preparedStatement.setString(10, player.getMuteReason());
                    preparedStatement.setBoolean(11, player.isFreeze());
                    preparedStatement.setBoolean(12, player.isVanish());

                    preparedStatement.execute();
                }
            }
        } catch (SQLException exception) {
            logger.severe("Erreur durant la sauvegarde des ranks: " + exception.getMessage());
        }
    }
}
