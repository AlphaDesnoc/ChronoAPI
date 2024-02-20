package com.chrono.chronoapi.utils.island;

import com.chrono.chronoapi.ChronoAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class CameraIslandCreated extends BukkitRunnable
{
    private final ChronoAPI api = ChronoAPI.getAPI();
    private final Player player;
    private final Location center;
    private double t;
    private int counter;

    public CameraIslandCreated(Player player, Location center)
    {
        this.player = player;
        this.center = center;
        this.t = 0.0;
        this.counter = 0;

        this.player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void run()
    {
        if (counter >= 128) {
            this.cancel();

            new BukkitRunnable() {
                double progress = 0.0;
                final World world = player.getWorld();
                final Location finalLoc = new Location(world, 0, 4, -1);

                @Override
                public void run() {
                    if (progress >= 1.0) {
                        this.cancel();
                        player.setGameMode(GameMode.SURVIVAL);

                        finalLoc.setPitch(0);
                        player.teleport(finalLoc);
                        return;
                    }

                    final Location loc = player.getLocation().clone().add(finalLoc.clone().subtract(player.getLocation()).toVector().multiply(progress));

                    player.teleport(loc);
                    progress += 0.1;
                }
            }.runTaskTimer(api, 0L, 2L);

            return;
        }

        double x = center.getX() + Math.sin(t) * 10;
        double z = center.getZ() + Math.cos(t) * 10;
        double y = center.getY();

        Location loc = new Location(player.getWorld(), x, y, z);

        double dx = center.getX() - x;
        double dz = center.getZ() - z;
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;

        loc.setPitch(70);
        loc.setYaw(yaw);

        player.teleport(loc);
        t += Math.PI / 64;
        counter++;
    }
}
