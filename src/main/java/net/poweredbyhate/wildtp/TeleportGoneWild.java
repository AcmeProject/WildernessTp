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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static net.poweredbyhate.wildtp.WildTP.instace;

public class TeleportGoneWild {

    ChecKar chacKer = new ChecKar();
    boolean needWait = instace.wamuppah > 0;

    public void WildTeleport(final Player p, String world) {
        if (Bukkit.getWorld(world) == null) {
            realTeleportt(p, p.getWorld());
            return;
        }
        realTeleportt(p, Bukkit.getWorld(world));
    }

    public void WildTeleport(Player p) {
        realTeleportt(p, p.getWorld());
    }

    public void realTeleportt(final Player p, World leWorld) {
        WildTP.debug("Wild teleport called for " + p.getName());
        if (chacKer.isInCooldown(p.getUniqueId())) {
            WildTP.debug("In cooldown: yes");
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME%", "Soon")));
            return;
        }
        World world;
        if (instace.randomeWorlds != null) {
            world = getRandomeWorld(instace.randomeWorlds);
        } else {
            world = leWorld;
        }
        Location locNotFinal = getRandomeLocation(world);
        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        WildTP.debug("Calling preTeleportEvent");
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");
        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return;
        }
        if (locNotFinal == null) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
            WildTP.debug("No suitable locations found");
            return;
        }
        if (instace.dr0p1n && locNotFinal.getWorld().getEnvironment() != World.Environment.NETHER) {
            WildTP.debug("Drop in feature enabled: Setting y=256");
            locNotFinal.setY(256);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        if (needWait) {
            WildTP.debug("Player needs to wait more");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TooWildForEnums.WAIT_MSG.replace("{wait}",String.valueOf(instace.wamuppah))));
            TooCool2Teleport.addPlayer(p);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                WildTP.debug("Starting Random Teleport");
                if (needWait && !TooCool2Teleport.isCold(p))
                    return;
                TooCool2Teleport.makeHot(p);
                WildTP.debug("Teleporting " + p.getName());
                p.teleport(loc);
                WildTP.debug(p.getName()+ " Teleported");
                WildTP.debug(p.getName() + " Adding to cooldown");
                chacKer.addKewlzDown(p.getUniqueId());
                WildTP.debug("Added to cooldown " + p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
            }
        }.runTaskLater(instace, instace.wamuppah*20);
    }

    public Location getRandomeLocation(World world) {
        for (int i = 0; i<WildTP.retries; i++) {
            Location loco = new Location(world, r4nd0m(WildTP.maxXY, WildTP.minXY), 5, r4nd0m(WildTP.maxXY, WildTP.minXY));
            if (world.getEnvironment() == World.Environment.NETHER)
                loco = netherLocation(loco);
            if (loco == null)
                continue;
            if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString()) && n0tAGreifClam(loco)) {
                if (world.getEnvironment() != World.Environment.NETHER)
                    loco.setY(world.getHighestBlockYAt(loco));
                loco.setX(loco.getX() + 0.5D);
                loco.setZ(loco.getZ() + 0.5D);
                if (n0tAB4dB10ck(loco))
                {
                    loco.setY(loco.getY() + 2);
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
                instace.getLogger().warning("World \"" + worldString + "\" does not exist. We recommend removing the world from enabledWorlds in config.yml");
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
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public boolean n0tAGreifClam(Location l0c0) {
        if (instace.dataaaastorege == null)
            return true;
        if (instace.dataaaastorege.getClaimAt(l0c0, instace.ifurwildandunoitclapurhands, null) == null)
            return instace.ifurwildandunoitclapurhands;
        return false;
    }

    public boolean n0tAB4dB10ck(Location l0c0) {
        Material blockType = l0c0.getBlock().getType();
        return blockType != Material.LAVA &&
                blockType != Material.STATIONARY_LAVA &&
                blockType != Material.CACTUS &&
                blockType != Material.FIRE;
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
}
