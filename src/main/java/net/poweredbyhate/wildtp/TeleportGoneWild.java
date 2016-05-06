package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TeleportGoneWild {

    public void WildTeleport(final Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(getRandomeLocation(p.getWorld()));
            }
        }.runTaskLater(WildTP.instace, 10);
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i<10; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY, WildTP.minXY), 5, r4nd0m(WildTP.maxXY, WildTP.minXY));
            if (!loco.getBlock().getBiome().toString().toLowerCase().contains("ocean")) {
                loco.setY(world.getHighestBlockYAt(loco)+2);
                return loco;
            }
        }
        return null;
    }

    public static int r4nd0m(int max, int min) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
}