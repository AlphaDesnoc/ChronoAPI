package com.chrono.chronoapi.utils.moderation;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.utils.rank.CheckUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class VisibilityUtils
{
    /*
    TODO: FIX LE BUG SUR LE VANISH
     */

    private static final ChronoAPI api = ChronoAPI.getAPI();
    public static void setVisible(final Player player)
    {
        final UUID uuid = player.getUniqueId();
        final DataPlayer dataPlayer = api.getPlayerData().getIfPresent(uuid);

        if (dataPlayer.isVanish()) {
            dataPlayer.setVanish(false);
            for (final Player plr : Bukkit.getOnlinePlayers()) {
                if (plr.equals(player)) {
                    continue;
                }

                plr.showPlayer(api, player);
                player.showPlayer(api, plr);
            }
        } else {
            dataPlayer.setVanish(true);
            for (final Player plr : Bukkit.getOnlinePlayers()) {
                if (!plr.equals(player)) {
                    if (CheckUtils.hasPermission(plr, 100)) {
                        player.showPlayer(api, plr);
                    } else {
                        plr.hidePlayer(api, player);
                    }
                }
            }
        }
    }
}