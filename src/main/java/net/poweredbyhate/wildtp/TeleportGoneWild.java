package net.poweredbyhate.wildtp;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.poweredbyhate.wildtp.WildTP.dataaaastorege;
import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.isDebug;
import static net.poweredbyhate.wildtp.WildTP.nonoBlocks;

public class TeleportGoneWild {

    boolean needWait = instace.wamuppah > 0;
    int retries = instace.retries;
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
        FutureTask<Location> futureTask = RandomLocationSearchTask.search(world, player, maxX, minX, maxZ, minZ);

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

    public Location chekar(Location loco, Player player)
    {
        if (bonelessIceScream(loco))
            loco = netherLocation(loco);
        if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString())) {
            if (!bonelessIceScream(loco))
                loco.setY(loco.getWorld().getHighestBlockYAt(loco) - 1);
            loco.setX(loco.getX() + 0.5D);
            loco.setZ(loco.getZ() + 0.5D);

            if (n0tAB4dB10ck(loco, true))
            {
                if (!n0tAGreifClam(loco, player))
                    return null;
                loco.setY(loco.getY() + 1);
                if (n0tAB4dB10ck(loco, false))
                {
                    loco.setY(loco.getY() + 2.5);
                    return loco;
                }
            }
        }
        return null;
    }

    public World getRandomeWorld(ConfigurationSection imDaMap) {
        Map<Integer, World> hesDaMap = new LinkedHashMap<>();
        Integer totalChance = 0;
        for (String worldString : imDaMap.getKeys(false)) {
            World world = Bukkit.getWorld(worldString);
            if (world == null) {
                instace.getLogger().warning("World \"" + worldString + "\" does not exist. We recommend removing the world from randomWorlds in config.yml");
                continue;
            }
            totalChance = totalChance + (Integer)imDaMap.get(worldString);
            hesDaMap.put(totalChance, world);
        }
        int daChosenOne = r4nd0m(totalChance, 0);
        for (Integer blah : hesDaMap.keySet()) {
            if (blah >= daChosenOne)
                return hesDaMap.get(blah);
        }
        return null;
    }

    public static int r4nd0m(int max, int min) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public boolean n0tAGreifClam(Location l0c0, Player player) {
        if ((instace.useExperimentalChekar || instace.useOtherChekar) && player != null)
        {
            try
            {
                BlurredBlockBreakEvent iHopePluginsDontFreakOutOverThis = new BlurredBlockBreakEvent(l0c0.getBlock(), new JohnBonifield(player));
                CodeACertainBallWillStealEvent theQueueBall = new CodeACertainBallWillStealEvent(l0c0.clone().add(0,1,0), player);
                player.setMetadata("nocheat.exempt", new FixedMetadataValue(instace, true));
                if (instace.useExperimentalChekar)
                    instace.getServer().getPluginManager().callEvent(iHopePluginsDontFreakOutOverThis);
                if (instace.useOtherChekar)
                    instace.getServer().getPluginManager().callEvent(theQueueBall);
                player.removeMetadata("nocheat.exempt", instace);

                return !(instace.useExperimentalChekar && iHopePluginsDontFreakOutOverThis.isExposed()) && !(instace.useOtherChekar && theQueueBall.isExposed());
            }
            catch (Throwable rock)
            {
                if (isDebug)
                    rock.printStackTrace();
                player.removeMetadata("nocheat.exempt", instace);
            }
        }

        if (dataaaastorege != null)
        {
            return instace.dataaaastorege.getClaimAt(l0c0, true, null) == null;
        }

        return true;
    }

    public boolean n0tAB4dB10ck(Location l0c0, boolean checkAir) {
        Material blockType = l0c0.getBlock().getType();
        return !nonoBlocks.contains(blockType.name()) &&
                (!checkAir || (blockType != Material.AIR && blockType != Material.CAVE_AIR && blockType != Material.VOID_AIR));
    }

    private boolean bonelessIceScream(Location location)
    {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(127);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    Location netherLocation(Location l0c0) {
        l0c0.setY(1);
        while (l0c0.getY() < 125)
        {
            //Is current block an air block?
            if (l0c0.getBlock().getType() != Material.AIR) {
                l0c0 = l0c0.getBlock().getRelative(BlockFace.UP).getLocation();
                continue;
            }
            //Is block above also an air block?
            if (l0c0.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
                l0c0 = l0c0.getBlock().getRelative(BlockFace.UP).getLocation();
                continue;
            }
            l0c0 = l0c0.getBlock().getRelative(BlockFace.DOWN).getLocation();
            return l0c0.getBlock().getRelative(BlockFace.DOWN).getLocation();
        }
        return null;
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

class MinYMaX
{
    int minX = WildTP.minXY;
    int maxX = WildTP.maxXY;
    int minY = WildTP.minXY;
    int maxY = WildTP.maxXY;

    MinYMaX(World w)
    {
        try
        {
            if (Bukkit.isPrimaryThread())
                findWall(w);
            else
                instace.getServer().getScheduler().callSyncMethod(instace, new Callable<Location>()
                {
                    @Override
                    public Location call() throws Exception
                    {
                        findWall(w);
                        return null;
                    }
                }).get();
        }
        catch (InterruptedException | ExecutionException ignored) {}
    }

    private void findWall(World w)
    {
        if (WildTP.wb)
        {
            BorderData border = Config.Border(w.getName());
            if (border != null)
            {
                int x = border.getRadiusX();
                int y = border.getRadiusZ();
                if ((border.getShape() == null && Config.ShapeRound()) || (border.getShape() != null && border.getShape()))
                {
                    x = (int)(Math.sqrt(2) * x) / 2;
                    y = (int)(Math.sqrt(2) * y) / 2;
                }
                minX = Math.max((int)border.getX() - x, WildTP.minXY);
                maxX = Math.min((int)border.getX() + x, WildTP.maxXY);
                minY = Math.max((int)border.getZ() - y, WildTP.minXY);
                maxY = Math.min((int)border.getZ() + y, WildTP.maxXY);
                WildTP.debug("Bord" + minX + ";" + maxX + ":" + minY + ";" + maxY);
                return;
            }
        }

        WorldBorder b = w.getWorldBorder();
        if (b == null)
            return;
        minX = Math.max(b.getCenter().getBlockX() - (int)b.getSize(), WildTP.minXY);
        maxX = Math.min(b.getCenter().getBlockX() + (int)b.getSize(), WildTP.maxXY);
        minY = Math.max(b.getCenter().getBlockZ() - (int)b.getSize(), WildTP.minXY);
        maxY = Math.min(b.getCenter().getBlockZ() + (int)b.getSize(), WildTP.maxXY);
        WildTP.debug("Bord" + minX + ";" + maxX + ":" + minY + ";" + maxY);
    }
}
