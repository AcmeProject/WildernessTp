package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TeleportGoneWild {

    ChecKar chacKer = new ChecKar();

    public void WildTeleport(final Player p) {
        if (chacKer.isInCooldown(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "Please wait until your cooldown is over.");
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(getRandomeLocation(p.getWorld()));
                chacKer.addKewlzDown(p.getUniqueId());
            }
        }.runTaskLater(WildTP.instace, WildTP.instace.wamuppah);
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i<10; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY, WildTP.minXY), 5, r4nd0m(WildTP.maxXY, WildTP.minXY));
            if (!loco.getBlock().getBiome().toString().toLowerCase().contains("ocean") && n0tAGreifClam(loco) ) {
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

    public boolean n0tAGreifClam(Location l0c0)
    {
        if (WildTP.instace.dataaaastorege == null)
            return true;
        if (WildTP.instace.dataaaastorege.getClaimAt(l0c0, WildTP.instace.ifurwildandunoitclapurhands, null) == null)
            return WildTP.instace.ifurwildandunoitclapurhands;
        return false;
    }
}
