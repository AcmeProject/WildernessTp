package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TeleportGoneWild {

    ChecKar chacKer = new ChecKar();

    public void WildTeleport(final Player p) {
        if (chacKer.isInCooldown(p.getUniqueId())) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME", String.valueOf(chacKer.getTimeLeft(p)))));
            return;
        }
        Location locNotFinal = getRandomeLocation(p.getWorld());
        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        if (preWildTeleportEvent.isCancelled()) return;
        if (locNotFinal == null) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
        }
        //feetare: dr0p dat playar in!1!
        if (WildTP.instace.dr0p1n)
        { //eet mah newline braces
            locNotFinal.setY(256);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;

        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(loc);
                chacKer.addKewlzDown(p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
            }
        }.runTaskLater(WildTP.instace, WildTP.instace.wamuppah);
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i<WildTP.retries; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY, WildTP.minXY), 5, r4nd0m(WildTP.maxXY, WildTP.minXY));
            if (!WildTP.instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString()) && n0tAGreifClam(loco)) {
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
