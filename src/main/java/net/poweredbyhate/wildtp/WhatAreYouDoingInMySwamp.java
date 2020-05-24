package net.poweredbyhate.wildtp;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

import static net.poweredbyhate.wildtp.WildTP.dataaaastorege;
import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.isDebug;
import static net.poweredbyhate.wildtp.WildTP.useExperimentalChekar;
import static net.poweredbyhate.wildtp.WildTP.useOtherChekar;

/**
 * Created on 5/23/2020.
 *
 * @author RoboMWM
 */

public class WhatAreYouDoingInMySwamp
{
    private int maxX, maxZ, minX, minZ;
    private Player player;
    private WorldConfig wc;
    private Location randomLocation;
    private Callable<Boolean> callable;

    WhatAreYouDoingInMySwamp(Player p, WorldConfig flatEarthProofs, TeleportGoneWild.Direction disway)
    {
        this.player = p;
        this.wc = flatEarthProofs;

        maxX = wc.maxX;
        maxZ = wc.maxZ;
        minX = wc.minX;
        minZ = wc.minZ;

        if (disway != null) {
            Location l = player.getLocation();

            switch (disway) {
                case EAST:  minX = l.getBlockX(); break;
                case NORTH: maxZ = l.getBlockZ(); break;
                case SOUTH: minZ = l.getBlockZ(); break;
                case WEST:  maxX = l.getBlockX(); break;
            }
        }

        if (WildTP.notPaper)
            Math.min(wc.retries, 4);
    }

    public CompletableFuture<Location> search()
    {
        Location l0c0 = new Location(wc.world, r4nd0m(maxX, minX), 10, r4nd0m(maxZ, minZ));

        return PaperLib.getChunkAtAsync(l0c0, true).thenApply(chunk ->
        {
            randomLocation = chekar(l0c0);
            if (randomLocation != null)
                return randomLocation;
            WildTP.debug("Location unsafe: " + l0c0);
            return null;
        });
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

    private Location chekar(Location loco) {
        if (wc.bioman.contains(loco.getBlock().getBiome().toString())) return null;
        if (bonelessIceScream(loco)) loco = netherLocation(loco, 110);
        else loco.setY(loco.getWorld().getHighestBlockYAt(loco));
        WildTP.debug("am chekin");
        WildTP.debug(loco);
        if (loco != null
                && !wc.nonoBlocks.contains(loco.getBlock().getType().name())
                && n0tAGreifClam(loco))
            return loco.add(0, 1, 0);
        return null;
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
}
