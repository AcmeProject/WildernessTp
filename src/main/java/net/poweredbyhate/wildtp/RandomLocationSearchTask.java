package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.dataaaastorege;
import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.isDebug;
import static net.poweredbyhate.wildtp.WildTP.useExperimentalChekar;
import static net.poweredbyhate.wildtp.WildTP.useOtherChekar;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import net.poweredbyhate.wildtp.TeleportGoneWild.Direction;

@Deprecated
@Warning(reason = "Use WhatAreYouDoingInMySwamp") //TODO: refactor name
class RandomLocationSearchTask implements Callable<Location> {
    private int maxX, maxZ, minX, minZ;

    private Player      player;
    private WorldConfig wc;

    RandomLocationSearchTask(Player p, WorldConfig flatEarthProofs) {
        player = p;
        wc     = flatEarthProofs;
    }

    FutureTask<Location> search(Direction disway) {
        iknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeople();

        if (disway != null) {
            Location l = player.getLocation();

            switch (disway) {
              case EAST:  minX = l.getBlockX(); break;
              case NORTH: maxZ = l.getBlockZ(); break;
              case SOUTH: minZ = l.getBlockZ(); break;
              case WEST:  maxX = l.getBlockX(); break;
          }
        }

        FutureTask<Location> futureTask = new FutureTask<Location>((Callable<Location>) this);
        Bukkit.getScheduler().runTaskAsynchronously(instace, futureTask);

        return futureTask;
    }

    static boolean bonelessIceScream(Location location) {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(127);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    static int r4nd0m(int max, int min) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Override
    public Location call() throws Exception {
        int retries = WildTP.notPaper ? Math.min(wc.retries, 4) : wc.retries;
        // There is no tries... DO or DON'T
        while (retries-- >= 0) {
            if (!TooCool2Teleport.isCold(player)) throw new Exception("event was cancelled!");

            TeleportGoneWild.focus(player, wc, retries);
            // if movingBorder, then check worldborder (again)
            if (wc.harlemShake) rickRoll();

            final Location[] loco = {null};
            Location l0c0 = new Location(wc.world, r4nd0m(maxX, minX), 10, r4nd0m(maxZ, minZ));

            if (WildTP.notPaper)
                wc.world.getChunkAt(l0c0);
            else
                wc.world.getChunkAtAsync(l0c0, true).thenAccept(chunk -> loco[0] = chekar(l0c0)).get();

            if (loco[0] != null) return loco[0];
        }

        TeleportGoneWild.cure(player);
        return null;
    }

    private Location chekar(Location loco) {
        if (wc.bioman.contains(loco.getBlock().getBiome().toString())) return null;
        if (bonelessIceScream(loco)) loco = netherLocation(loco, 110);
        else loco.setY(loco.getWorld().getHighestBlockYAt(loco));
        WildTP.debug("am chekin");
        WildTP.debug(loco);
        return (loco != null && !wc.nonoBlocks.contains(loco.getBlock().getType().name())
                && n0tAGreifClam(loco)) ? loco.add(0, 1, 0): null;
    }

    public boolean n0tAB4dB10ck(Location l0c0, boolean checkAir) {
        Material blockType = l0c0.getBlock().getType();
        return !wc.nonoBlocks.contains(blockType.name()) &&
                (!checkAir || (blockType != Material.AIR && blockType != Material.CAVE_AIR && blockType != Material.VOID_AIR));
    }

    private Location netherLocation(Location l0c0, int max) {
        Block b = l0c0.getBlock(); for (int d = 0; d < max;) if (
            b.getRelative(BlockFace.UP, d++).getType() == Material.AIR
        &&  b.getRelative(BlockFace.UP, d++).getType() == Material.AIR
        && !b.getRelative(BlockFace.UP, d-3).isPassable()) return b.getLocation().add(0, d-3, 0); return null;
    }

    private boolean n0tAGreifClam(Location l0c0) {
        if ((useExperimentalChekar || useOtherChekar) && player != null) {
            try {
                BlurredBlockBreakEvent iHopePluginsDontFreakOutOverThis
                    = new BlurredBlockBreakEvent(l0c0.getBlock(), new JohnBonifield(player));
                CodeACertainBallWillStealEvent theQueueBall
                    = new CodeACertainBallWillStealEvent(l0c0.clone().add(0, 1, 0), player);
                player.setMetadata("nocheat.exempt", new FixedMetadataValue(instace, true));

                if (useExperimentalChekar)
                    Bukkit.getPluginManager().callEvent(iHopePluginsDontFreakOutOverThis);
                if (useOtherChekar)
                    Bukkit.getPluginManager().callEvent(theQueueBall);

                player.removeMetadata("nocheat.exempt", instace);

                return !(useExperimentalChekar && iHopePluginsDontFreakOutOverThis.isExposed())
                        && !(useOtherChekar && theQueueBall.isExposed());
            }
            catch (Throwable rock) {
                if (isDebug) rock.printStackTrace();
                player.removeMetadata("nocheat.exempt", instace);
            }
        }

        return (dataaaastorege == null) || (dataaaastorege.getClaimAt(l0c0, true, null) == null);
    }

    private void rickRoll() {
        wc.weNeedToBuildaWallTrumpSaidItAndObviouslyEverybodyLikeHim();
        iknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeople();
    }

    private void iknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeopleiknowamethodnamewhichenervespeople() {
        maxX = wc.maxX;
        maxZ = wc.maxZ;
        minX = wc.minX;
        minZ = wc.minZ;
    }

    static void jk(Player p) {
        if (p.getName().equalsIgnoreCase("LaxWasHere") || p.getName().equalsIgnoreCase("RoboMWM"))
            p.sendMessage("§7" + new StringBuilder("ulk§d§ lulk§e§ lulk§d§ ?ydnac emos "
                    + "tnaw yeH3§ lulk§d§ lulk§e§ lulk§d§ >7§raebodepl§6§<").reverse());
    }
}
