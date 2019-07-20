package net.poweredbyhate.wildtp;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

import static net.poweredbyhate.wildtp.WildTP.dataaaastorege;
import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.isDebug;
import static net.poweredbyhate.wildtp.WildTP.nonoBlocks;

public class RandomLocationSearchTask implements Callable<Location> {
    private World world;
    private final Player player;
    private final int maxX;
    private final int minX;
    private final int maxZ;
    private final int minZ;
    private int retries = instace.retries;

    private RandomLocationSearchTask(World world, Player player, int maxX, int minX, int maxZ, int minZ) {
        this.world = world;
        this.player = player;
        this.maxX = maxX;
        this.minX = minX;
        this.maxZ = maxZ;
        this.minZ = minZ;
    }

    public static FutureTask<Location> search(World world, Player player, int maxX, int minX, int maxZ, int minZ) {
        FutureTask<Location> futureTask = new FutureTask<>(new RandomLocationSearchTask(world, player, maxX, minX, maxZ, minZ));
        instace.getServer().getScheduler().runTaskAsynchronously(instace, futureTask);
        return futureTask;
    }

    @Override
    public Location call() throws Exception {
        final boolean pageRipper = world == null && instace.useRandomeWorldz;
        if (world == null) {
            if (instace.useRandomeWorldz)
                world = getRandomeWorld(instace.randomeWorlds);
            else if (player != null)
                world = player.getWorld();
            else
                return null;
        }
        World finalWorld = world;
        MinYMaX2 minmax = new MinYMaX2(world);
        if (WildTP.notPaper) {
            for (int i = 0; i < Math.min(retries, 4); i++) {
                Location loco = new Location(world, r4nd0m(minmax.maxX, minmax.minX), 5, r4nd0m(minmax.maxY, minmax.minY));
                if (bonelessIceScream(loco))
                    loco = netherLocation(loco);
                if (loco == null)
                    continue;
                Location finalLoco = loco;
                String locoBlockBiomeString = instace.getServer().getScheduler().callSyncMethod(instace, new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return finalLoco.getBlock().getBiome().toString();
                    }
                }).get();
                if (!instace.getConfig().getStringList("BlockedBiomes").contains(locoBlockBiomeString)) {
                    if (!bonelessIceScream(loco))
                        loco.setY(world.getHighestBlockYAt(loco) - 1);
                    loco.setX(loco.getX() + 0.5D);
                    loco.setZ(loco.getZ() + 0.5D);
                    if (n0tAB4dB10ck(loco, true)) {
                        if (!n0tAGreifClam(loco, player))
                            continue;
                        loco.setY(loco.getY() + 1);
                        if (n0tAB4dB10ck(loco, false)) {
                            loco.setY(loco.getY() + 2.5);
                            return loco;
                        }
                    }
                }
            }
            return null;
        }

        while (retries-- >= 0) {
            World rippedPage = finalWorld;
            if (pageRipper)
                rippedPage = getRandomeWorld(instace.randomeWorlds);
            minmax = new MinYMaX2(rippedPage);
            Location loco = new Location(rippedPage, r4nd0m(minmax.maxX, minmax.minX), 5, r4nd0m(minmax.maxY, minmax.minY));
            try {
                rippedPage.getChunkAtAsync(loco, true).get();
                Location l0c0 = loco;
                loco = instace.getServer().getScheduler().callSyncMethod(instace, new Callable<Location>() {
                    @Override
                    public Location call() throws Exception {
                        return chekar(l0c0, player);
                    }
                }).get();
                Location l0co = loco;
                if (loco != null) {
                    return loco;
                }

            } catch (InterruptedException | ExecutionException e) {
                if (isDebug)
                    e.printStackTrace();
                continue;
            }

        }
        return null;
    }

    public Location chekar(Location loco, Player player) {
        if (bonelessIceScream(loco))
            loco = netherLocation(loco);
        if (!instace.getConfig().getStringList("BlockedBiomes").contains(loco.getBlock().getBiome().toString())) {
            if (!bonelessIceScream(loco))
                loco.setY(loco.getWorld().getHighestBlockYAt(loco) - 1);
            loco.setX(loco.getX() + 0.5D);
            loco.setZ(loco.getZ() + 0.5D);

            if (n0tAB4dB10ck(loco, true)) {
                if (!n0tAGreifClam(loco, player))
                    return null;
                loco.setY(loco.getY() + 1);
                if (n0tAB4dB10ck(loco, false)) {
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
            totalChance = totalChance + (Integer) imDaMap.get(worldString);
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
        if ((instace.useExperimentalChekar || instace.useOtherChekar) && player != null) {
            try {
                BlurredBlockBreakEvent iHopePluginsDontFreakOutOverThis = new BlurredBlockBreakEvent(l0c0.getBlock(), new JohnBonifield(player));
                CodeACertainBallWillStealEvent theQueueBall = new CodeACertainBallWillStealEvent(l0c0.clone().add(0, 1, 0), player);
                player.setMetadata("nocheat.exempt", new FixedMetadataValue(instace, true));
                if (instace.useExperimentalChekar)
                    instace.getServer().getPluginManager().callEvent(iHopePluginsDontFreakOutOverThis);
                if (instace.useOtherChekar)
                    instace.getServer().getPluginManager().callEvent(theQueueBall);
                player.removeMetadata("nocheat.exempt", instace);

                return !(instace.useExperimentalChekar && iHopePluginsDontFreakOutOverThis.isExposed()) && !(instace.useOtherChekar && theQueueBall.isExposed());
            } catch (Throwable rock) {
                if (isDebug)
                    rock.printStackTrace();
                player.removeMetadata("nocheat.exempt", instace);
            }
        }

        if (dataaaastorege != null) {
            return instace.dataaaastorege.getClaimAt(l0c0, true, null) == null;
        }

        return true;
    }

    public boolean n0tAB4dB10ck(Location l0c0, boolean checkAir) {
        Material blockType = l0c0.getBlock().getType();
        return !nonoBlocks.contains(blockType.name()) &&
                (!checkAir || (blockType != Material.AIR && blockType != Material.CAVE_AIR && blockType != Material.VOID_AIR));
    }

    private boolean bonelessIceScream(Location location) {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(127);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    Location netherLocation(Location l0c0) {
        l0c0.setY(1);
        while (l0c0.getY() < 125) {
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

class MinYMaX2 {
    int minX = WildTP.minXY;
    int maxX = WildTP.maxXY;
    int minY = WildTP.minXY;
    int maxY = WildTP.maxXY;

    MinYMaX2(World w) {
        findWall(w);
    }

    private void findWall(World w) {
        if (WildTP.wb) {
            BorderData border = Config.Border(w.getName());
            if (border != null) {
                int x = border.getRadiusX();
                int y = border.getRadiusZ();
                if ((border.getShape() == null && Config.ShapeRound()) || (border.getShape() != null && border.getShape())) {
                    x = (int) (Math.sqrt(2) * x) / 2;
                    y = (int) (Math.sqrt(2) * y) / 2;
                }
                minX = Math.max((int) border.getX() - x, WildTP.minXY);
                maxX = Math.min((int) border.getX() + x, WildTP.maxXY);
                minY = Math.max((int) border.getZ() - y, WildTP.minXY);
                maxY = Math.min((int) border.getZ() + y, WildTP.maxXY);
                WildTP.debug("Bord" + minX + ";" + maxX + ":" + minY + ";" + maxY);
                return;
            }
        }

        WorldBorder b = w.getWorldBorder();
        if (b == null)
            return;
        minX = Math.max(b.getCenter().getBlockX() - (int) b.getSize(), WildTP.minXY);
        maxX = Math.min(b.getCenter().getBlockX() + (int) b.getSize(), WildTP.maxXY);
        minY = Math.max(b.getCenter().getBlockZ() - (int) b.getSize(), WildTP.minXY);
        maxY = Math.min(b.getCenter().getBlockZ() + (int) b.getSize(), WildTP.maxXY);
        WildTP.debug("Bord" + minX + ";" + maxX + ":" + minY + ";" + maxY);
    }
}
