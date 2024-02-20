package com.chrono.chronoapi.database.requests.load;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.cache.DataRank;
import com.chrono.chronoapi.database.DatabaseManager;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class LoadPlayerRequest
{

    private static final ChronoAPI api = ChronoAPI.getAPI();
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static final DatabaseManager databaseManager = ChronoAPI.getDatabaseManager();

    public static void loadPlayer(final Player player)
    {
        final UUID uuid = player.getUniqueId();

        if (api.getPlayerData().getIfPresent(uuid) != null) {
            return;
        }

        try {
            final PreparedStatement preparedStatementPlayer = databaseManager.getConnection().prepareStatement("SELECT * FROM PLAYERS WHERE uuid = ?");
            preparedStatementPlayer.setString(1, uuid.toString());
            final ResultSet rsPlayer = preparedStatementPlayer.executeQuery();

            if (rsPlayer.next()) {
                final BigDecimal money = rsPlayer.getBigDecimal("money");
                final DataRank rank = api.getRankData().getIfPresent(rsPlayer.getInt("rank"));
                final boolean isPermBan = rsPlayer.getBoolean("isPermBan");
                final boolean isTempBan = rsPlayer.getBoolean("isTempBan");
                final String banTime = rsPlayer.getString("banTime");
                final String banReason = rsPlayer.getString("banReason");
                final boolean isMute = rsPlayer.getBoolean("isMute");
                final String muteTime = rsPlayer.getString("muteTime");
                final String muteReason = rsPlayer.getString("muteReason");
                final boolean isFreeze = rsPlayer.getBoolean("isFreeze");
                final boolean isVanish = rsPlayer.getBoolean("isVanish");

                api.getPlayerData().put(uuid, new DataPlayer(uuid, money, rank, isPermBan, isTempBan, banTime, banReason, isMute, muteTime, muteReason, isFreeze, isVanish));
                rank.addPlayer(player);
            } else {
                api.getPlayerData().put(uuid, new DataPlayer(uuid));
                api.getRankData().getIfPresent(0).addPlayer(player);
            }
        } catch (SQLException exception) {
            logger.severe("Erreur durant le chargement du joueur: " + exception.getMessage());
        }
    }

}
