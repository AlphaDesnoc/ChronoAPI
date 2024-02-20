package com.chrono.chronoapi.utils.skins;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.utils.logger.PluginLogger;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class SkinUtils
{
    private static final PluginLogger logger = ChronoAPI.getPluginLogger();
    private static final ChronoAPI api = ChronoAPI.getAPI();

    public static void changeSkin(final Player player, final String uuid)
    {
        final String value = "ewogICJ0aW1lc3RhbXAiIDogMTY5Njc1NzQyNTUxOCwKICAicHJvZmlsZUlkIiA6ICJhNTdmZDE5MGZmM2U0YjBkYTEzMmY2OGUzOTU3ZjViMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4SGFubmFoNyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMDliYzQxZmIwZDE3NjY3OGFhNzliODE5YmJjMzA3YmRiZWIxNTYzNDRjOTZjNWEyNmQ4MTI2YmIzNGFhMmFjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        final String signature = "Gayd3NHolGUQQ+pZJnPP50AdrS29/NAnySfk3VKCZb9qhr3XsCsaTGArcQp9sdkIMN/Gw1ikBf5j1qpvzAK+rCOtmqUTpoaH7BvGob/wgNxd800ztbMNTZhRRRUeE6+kSsO8YBQtfuCL1osuS24hlxf9dmAvAV7jEu7/cPUSncwkTCphQVzkx7EmH2osIeYR7IHMnaEiwwCcewpznzWAsJ8Y6IGzMhiZKluEDYIegt7qr9UDl1pLxcv+WPTzLc9jJS3PbfOvjtuY+TIbO+hmqC7RauYiMbKZ4BvJ2FvglUz+9WchPdQviE3rPdxGkoaUeHGqlt1DSohEs8Me030nmW7vZyGCV62Q3vzfWqbGVLz0uT28d5grOFNeGs4gXO66HNh8JYTfo3fAk2FZ2W1BdUDR/r9X7nPJE9+KeCP7MxknghpZcdP/mU9dAe0B9cgElDC22+3YbULhxIt6r0LQl0wu8LMqMJKJ/THWITmBQv2oCDNGE6jjI35F1jpSUvgbOwZ+yZ0wlCBax5x7E6BhBbNPWXGspb3+BYJWqbkS0KOXWNlybQdG2ch+OxJmJa5ihOmSS9jVNvfGREoUv+fnAe8gNtPwIwTzbe1xG124HO0I+TEjHWMsaPtrPoEs7atEBSReBeA7vJ6BNaFXotYLPZ3VM4UZNlKMMcxbJreUE5Y=";

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final GameProfile gameProfile = entityPlayer.getBukkitEntity().getProfile();

        gameProfile.getProperties().removeAll("textures");
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));

        for (final Player otherPlayer : Bukkit.getOnlinePlayers()) {
            otherPlayer.hidePlayer(player);
            otherPlayer.showPlayer(player);
        }

        reloadSkinForSelf(player);
    }

    private static void reloadSkinForSelf(final Player player) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final PlayerConnection playerConnection = entityPlayer.c;

        // TODO: FIX L'UPDATE DU SKIN POUR LE JOUEUR LUI MEME

        //playerConnection.a(new PacketPlayOutRespawn();
    }
}