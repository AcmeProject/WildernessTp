package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.Random;

public class TeleportGoneWild {

    public void WildTeleport(Player p) {
        p.teleport(getRandomeLocation(p.getWorld()));
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i<10; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY[0], WildTP.minXY[0]), 5, r4nd0m(WildTP.maxXY[1], WildTP.minXY[1]));
            Biome locoBiome = loco.getBlock().getBiome();
            if (locoBiome != Biome.OCEAN || locoBiome != Biome.DEEP_OCEAN) {
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