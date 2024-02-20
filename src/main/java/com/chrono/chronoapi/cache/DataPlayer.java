package com.chrono.chronoapi.cache;

import com.chrono.chronoapi.ChronoAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.UUID;

public final class DataPlayer
{
    private final ChronoAPI api = ChronoAPI.getAPI();
    private final UUID uuid;
    private BigDecimal money;
    private DataRank rank;
    private boolean isPermBan;
    private boolean isTempBan;
    private String banTime;
    private String banReason;
    private boolean isMute;
    private String muteTime;
    private String muteReason;
    private boolean isFreeze;
    private boolean isVanish;

    public DataPlayer(final UUID uuid)
    {
        this.uuid = uuid;
        this.money = BigDecimal.valueOf(1000);
        this.rank = api.getRankData().getIfPresent(0);
        this.isPermBan = false;
        this.isTempBan = false;
        this.banTime = "";
        this.banReason = "";
        this.isMute = false;
        this.muteTime = "";
        this.muteReason = "";
        this.isFreeze = false;
        this.isVanish = false;
    }

    public DataPlayer(
            final UUID uuid,
            final BigDecimal money,
            final DataRank rank,
            final boolean isPermBan,
            final boolean isTempBan,
            final String banTime,
            final String banReason,
            final boolean isMute,
            final String muteTime,
            final String muteReason,
            final boolean isFreeze,
            final boolean isVanish
    ) {
        this.uuid = uuid;
        this.money = money;
        this.rank = rank;
        this.isPermBan = isPermBan;
        this.isTempBan = isTempBan;
        this.banTime = banTime;
        this.banReason = banReason;
        this.isMute = isMute;
        this.muteTime = muteTime;
        this.muteReason = muteReason;
        this.isFreeze = isFreeze;
        this.isVanish = isVanish;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void addMoney(final BigDecimal amount)
    {
        this.money = this.money.add(amount);
    }

    public void substractMoney(final BigDecimal amount)
    {
        this.money = this.money.subtract(amount);
    }

    public void resetMoney()
    {
        this.money = BigDecimal.ZERO;
    }

    public void setMoney(final BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }

    public void setRank(final DataRank rank)
    {
        this.rank = rank;
    }

    public DataRank getRank()
    {
        return rank;
    }

    public void setPermBan(final Player player, final String banReason)
    {
        this.isPermBan = true;
        this.banReason = banReason;

        Bukkit.getScheduler().runTask(api, () -> {
            player.kickPlayer(this.banReason);
        });
    }

    public boolean isPermBan()
    {
        return isPermBan;
    }

    public void setTempBan(final Player player, final String banReason, final String banTime)
    {
        this.isTempBan = true;
        this.banReason = banReason;
        this.banTime = banTime;

        Bukkit.getScheduler().runTask(api, () -> {
            player.kickPlayer(this.banReason + " jusqu'à " + this.banTime);
        });
    }

    public void unban()
    {
        this.isPermBan = false;
        this.isTempBan = false;
        this.banReason = "";
        this.banTime = "";
    }

    public boolean isTempBan()
    {
        return isTempBan;
    }

    public String getBanTime()
    {
        return banTime;
    }

    public String getBanReason()
    {
        return banReason;
    }

    public void setMute(final String muteReason, final String muteTime)
    {
        this.isMute = true;
        this.muteReason = muteReason;
        this.muteTime = muteTime;
    }

    public void unmute()
    {
        this.isMute = false;
        this.muteReason = "";
        this.muteTime = "";
    }

    public boolean isMute()
    {
        return isMute;
    }

    public String getMuteTime()
    {
        return muteTime;
    }

    public String getMuteReason()
    {
        return muteReason;
    }

    public void setFreeze(final boolean freeze) {
        isFreeze = freeze;
    }

    public boolean isFreeze()
    {
        return isFreeze;
    }

    public void setVanish(final boolean vanish) {
        isVanish = vanish;
    }

    public boolean isVanish() {
        return isVanish;
    }
}
