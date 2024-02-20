package com.chrono.chronoapi.utils.rank;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataRank;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class CheckUtils
{
    private static final ChronoAPI api = ChronoAPI.getAPI();

    public static boolean isValidBigDecimal(final BigDecimal bigDecimal)
    {
        return !Objects.equals(bigDecimal, BigDecimal.ZERO);
    }

    public static boolean isValidInt(final String str)
    {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static boolean canAfford(final BigDecimal money, final BigDecimal amount)
    {
        return money.compareTo(amount) < 0;
    }

    public static boolean hasPermission(final Player player, final int id)
    {
        return api.getPlayerData().getIfPresent(player.getUniqueId()).getRank().getId() >= id || player.isOp();
    }

    public static boolean isCorrectDate(final String time)
    {
        return time.endsWith("w") || time.endsWith("d") || time.endsWith("h") || time.endsWith("m") || time.endsWith("s");
    }

    public static boolean isStartTimePassed(final String startTime, final String endTime)
    {
        if (startTime.isEmpty() || endTime.isEmpty()) {
            return false;
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        final LocalDateTime end = LocalDateTime.parse(endTime, formatter);

        return start.isAfter(end);
    }

    public static boolean hasAlreadyRank(final DataRank playerRank, final DataRank rank)
    {
        return playerRank.getId() >= rank.getId();
    }
}