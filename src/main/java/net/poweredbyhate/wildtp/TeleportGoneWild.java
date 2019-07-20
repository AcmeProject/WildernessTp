package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.poweredbyhate.wildtp.WildTP.instace;

public class TeleportGoneWild {

    boolean needWait = instace.wamuppah > 0;
    boolean bypass;

    public void WildTeleport(final Player p, final String world, final boolean bypass) {
        this.bypass = bypass;
        World world1 = Bukkit.getWorld(world);
        realTeleportt(p, world1, WildTP.maxXY, WildTP.minXY, WildTP.maxXY, WildTP.minXY);
    }

    public void WildTeleport(final Player p, final boolean bypass) {
        this.bypass = bypass;
        realTeleportt(p, null, WildTP.maxXY, WildTP.minXY, WildTP.maxXY, WildTP.minXY);
    }

    public void WildTeleport(final Player p, final int maxX, final int minX, final int maxZ, final int minZ, final boolean bypass)
    {
        this.bypass = bypass;
        realTeleportt(p, null, maxX, minX, maxZ, minZ);
    }

    public boolean realTeleportt(final Player p, World world, int maxX, int minX, int maxZ, int minZ) {
        WildTP.debug("Wild teleport called for " + p.getName());
        if (WildTP.checKar.isInCooldown(p.getUniqueId())) {
            WildTP.debug("In cooldown: yes");
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME%", WildTP.checKar.getTimeLeft(p))));
            return true;
        }

//        Location locNotFinal;
//        if (instace.cash != null && ((world == null && ((useRandomeWorldz && randomeWorlds.contains(instace.cash.getWorld().getName())) || (!useRandomeWorldz && p.getWorld() == instace.cash.getWorld()))) || world == instace.cash.getWorld()) && n0tAGreifClam(instace.cash, p))
//        {
//            locNotFinal = instace.cash;
//            instace.cash = null;
//            return realTeleportt2(p, world, maxX, minX, maxZ, minZ, locNotFinal);
//        }
//        else
            getRandomeLocation(world, p, maxX, minX, maxZ, minZ);
        return true;
    }

    public boolean realTeleportt2(final Player p, Location locNotFinal)
    {
        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        WildTP.debug("Calling preTeleportEvent");
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");
        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return !preWildTeleportEvent.isRetry();
        }

        if (instace.dr0p1n && !bonelessIceScream(locNotFinal)) {
            WildTP.debug("Drop in feature enabled: Setting y=256");
            locNotFinal.setY(300);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        WildTP.debug("preping 2 port 2 " + loc);
        if (needWait && !bypass) {
            WildTP.debug("Player needs to wait more");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TooWildForEnums.WAIT_MSG.replace("{wait}",String.valueOf(instace.wamuppah))));
            TooCool2Teleport.addPlayer(p, goWild(p, loc, instace.wamuppah*20L));
        }
        else
            goWild(p, loc, 0L);
        return true;
    }

    public void getRandomeLocation(World world, Player player, int maxX, int minX, int maxZ, int minZ)
    {
        // Start searching random task
        FutureTask<Location> futureTask = RandomLocationSearchTask.search(world, player);

        instace.getServer().getScheduler().runTaskAsynchronously(instace, new Runnable() {
            @Override
            public void run() {
                try {
                    // Wait and get the random location
                    Location loco = futureTask.get(5, TimeUnit.SECONDS);
                    if (loco == null) {
                        player.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
                        WildTP.debug("No suitable locations found");
                        return;
                    }
                    // Teleport player in main thread
                    if (instace.getServer().getScheduler().callSyncMethod(instace, new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return realTeleportt2(player, loco);
                        }
                    }).get()) {
                        return;
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                }
                player.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
                WildTP.debug("Random location searching timeout");
            }
        });
    }

    private boolean bonelessIceScream(Location location)
    {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(127);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    public BukkitTask goWild(final Player p, final Location loc, final Long time)
    {
        return new BukkitRunnable() {
            @Override
            public void run() {
                WildTP.debug("Starting Random Teleport");
                if (!bypass) {
                    if (needWait && !TooCool2Teleport.isCold(p))
                        return;
                    TooCool2Teleport.makeHot(p);
                }
                WildTP.debug("Teleporting " + p.getName() + loc);
                if (!p.teleport(loc))
                {
                    WildTP.debug("teleport was canceled.");
                    return;
                }
                WildTP.debug(p.getName()+ " Teleported" + p.getLocation());
                WildTP.debug(p.getName() + " Adding to cooldown");
                WildTP.checKar.addKewlzDown(p.getUniqueId());
                WildTP.debug("Added to cooldown " + p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
            }
        }.runTaskLater(instace, time);
    }
}
