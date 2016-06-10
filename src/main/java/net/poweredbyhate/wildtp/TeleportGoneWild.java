package net.poweredbyhate.wildtp;

import org.bukkit.*;
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

    public void WildTeleport(final Player p) {
        if (chacKer.isInCooldown(p.getUniqueId())) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME", String.valueOf(chacKer.getTimeLeft(p)))));
            return;
        }
        World world;
        if (instace.randomeWorlds != null)
            world = getRandomeWorld(instace.randomeWorlds);
        else
            world = p.getWorld();
        Location locNotFinal = getRandomeLocation(world);
        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        if (preWildTeleportEvent.isCancelled()) return;
        if (locNotFinal == null) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
            return;
        }
        //feetare: dr0p dat playar in!1!
        if (instace.dr0p1n && locNotFinal.getWorld().getEnvironment() != World.Environment.NETHER)
        { //eet mah newline braces
            locNotFinal.setY(256);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        if (needWait)
        {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TooWildForEnums.WAIT_MSG.replace("{wait}",String.valueOf(instace.wamuppah))));
            TooHot2Teleport.addPlayer(p);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (needWait && !TooHot2Teleport.isCold(p))
                    return;
                TooHot2Teleport.makeHot(p);
                p.teleport(loc);
                chacKer.addKewlzDown(p.getUniqueId());
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
            if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString()) && n0tAGreifClam(loco) && n0tAB4dB10ck(loco)) {
                if (world.getEnvironment() != World.Environment.NETHER)
                    loco.setY(world.getHighestBlockYAt(loco)+2);
                return loco;
            }
        }
        return null;
    }

    public World getRandomeWorld(ConfigurationSection imDaMap)
    {
        Map<Integer, World> hesDaMap = new LinkedHashMap<>();
        Integer totalChance = 0;
        //[20:11:12] @RoboMWM: Haven't eaten yet but am thinking:
        //iterate through each entry, and add its chance to a total int value, as well as
        //adding the key/value pair in an ordered Map (but key will be int, and int will be int + total int)
        for (String worldString : imDaMap.getKeys(false))
        {
            World world = Bukkit.getWorld(worldString);
            if (world == null)
            {
                instace.getLogger().warning("World \"" + worldString + "\" does not exist. We recommend removing the world from enabledWorlds in config.yml");
                continue;
            }
            totalChance = totalChance + (Integer)imDaMap.get(worldString);
            hesDaMap.put(totalChance, world);
        }
        //then call nextInt(total int) and then iterate through list until the key is or greater than the nextInt
        int daChosenOne = new Random().nextInt(++totalChance);
        for (Integer blah : hesDaMap.keySet())
        {
            if (blah >= daChosenOne)
                return hesDaMap.get(blah);
        }
        return null; //shouldn't get here
    }

    public static int r4nd0m(int max, int min) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public boolean n0tAGreifClam(Location l0c0)
    {
        if (instace.dataaaastorege == null)
            return true;
        if (instace.dataaaastorege.getClaimAt(l0c0, instace.ifurwildandunoitclapurhands, null) == null)
            return instace.ifurwildandunoitclapurhands;
        return false;
    }

    public boolean n0tAB4dB10ck(Location l0c0)
    {
        Material blockType = l0c0.getBlock().getType();
        return blockType != Material.LAVA &&
                blockType != Material.STATIONARY_LAVA &&
                blockType != Material.CACTUS &&
                blockType != Material.FIRE;
    }
    Location netherLocation(Location l0c0) //much testing resulted in this, plz no qball
    {
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
            return l0c0.getBlock().getRelative(BlockFace.DOWN).getLocation();
        }
        return null;
    }
}
