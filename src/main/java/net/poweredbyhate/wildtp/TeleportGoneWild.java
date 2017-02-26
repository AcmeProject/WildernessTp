package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static net.poweredbyhate.wildtp.WildTP.instace;

public class TeleportGoneWild {

    boolean needWait = instace.wamuppah > 0;
    int retries = instace.retries;
    boolean bypass;

    public void WildTeleport(final Player p, final String world) {
        World world1 = Bukkit.getWorld(world);
        if (world1 == null) {
            world1 = p.getWorld();
        }
        if (!realTeleportt(p, world1))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    WildTeleport(p, world);
                }
            }.runTaskLater(instace, 1L);
        }
    }

    public void WildTeleport(final Player p) {
        World world = null;
        if (instace.useRandomeWorldz) {
            world = getRandomeWorld(instace.randomeWorlds);
        }
        if (world == null)
            world = p.getWorld();
        if (!realTeleportt(p, world))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    WildTeleport(p);
                }
            }.runTaskLater(instace, 1L);
        }

    }

    public void WildTeleport(Player p, boolean bypass) {
        this.bypass = bypass;
        WildTeleport(p);
    }

    public boolean realTeleportt(final Player p, World world) {
        retries--;
        WildTP.debug("Wild teleport called for " + p.getName());
        if (WildTP.checKar.isInCooldown(p.getUniqueId())) {
            WildTP.debug("In cooldown: yes");
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME%", WildTP.checKar.getTimeLeft(p))));
            return true;
        }

        Location locNotFinal = getRandomeLocation(world);
        if (locNotFinal == null) {
            if (retries >= 0)
                return false;
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
            WildTP.debug("No suitable locations found");
            return true;
        }

        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        WildTP.debug("Calling preTeleportEvent");
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");
        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return !preWildTeleportEvent.isRetry();
        }

        if (instace.dr0p1n && locNotFinal.getWorld().getEnvironment() != World.Environment.NETHER) {
            WildTP.debug("Drop in feature enabled: Setting y=256");
            locNotFinal.setY(256);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        if (needWait && !bypass) {
            WildTP.debug("Player needs to wait more");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TooWildForEnums.WAIT_MSG.replace("{wait}",String.valueOf(instace.wamuppah))));
            TooCool2Teleport.addPlayer(p, goWild(p, loc, instace.wamuppah*20L));
        }
        else
            goWild(p, loc, 0L);
        return true;
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i < 4; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY, WildTP.minXY), 5, r4nd0m(WildTP.maxXY, WildTP.minXY));
            if (world.getEnvironment() == World.Environment.NETHER)
                loco = netherLocation(loco);
            if (loco == null)
                continue;
            if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString()) && n0tAGreifClam(loco)) {
                if (world.getEnvironment() != World.Environment.NETHER)
                    loco.setY(world.getHighestBlockYAt(loco) - 1);
                loco.setX(loco.getX() + 0.5D);
                loco.setZ(loco.getZ() + 0.5D);
                if (n0tAB4dB10ck(loco, true))
                {
                    loco.setY(loco.getY() + 1);
                    if (n0tAB4dB10ck(loco, false))
                    {
                        loco.setY(loco.getY() + 2);
                        return loco;
                    }
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

    public boolean n0tAGreifClam(Location l0c0) {
        if (instace.dataaaastorege == null)
            return true;
        if (instace.dataaaastorege.getClaimAt(l0c0, instace.ifurwildandunoitclapurhands, null) == null)
            return instace.ifurwildandunoitclapurhands;
        return false;
    }

    public boolean n0tAB4dB10ck(Location l0c0, boolean checkAir) {
        Material blockType = l0c0.getBlock().getType();
        return blockType != Material.LAVA &&
                blockType != Material.STATIONARY_LAVA &&
                blockType != Material.CACTUS &&
                blockType != Material.FIRE &&
                (!checkAir || blockType != Material.AIR);
    }

    Location netherLocation(Location l0c0) {
        l0c0.setY(1);
        while (l0c0.getY() < 127)
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
                WildTP.debug("Teleporting " + p.getName());
                p.teleport(loc);
                WildTP.debug(p.getName()+ " Teleported");
                WildTP.debug(p.getName() + " Adding to cooldown");
                WildTP.checKar.addKewlzDown(p.getUniqueId());
                WildTP.debug("Added to cooldown " + p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
            }
        }.runTaskLater(instace, time);
    }
}
