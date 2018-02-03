package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static net.poweredbyhate.wildtp.WildTP.instace;

/**
 * Created on 7/9/2017.
 *
 * @author RoboMWM
 */
public class NoobieListener implements Listener
{
    @EventHandler
    void onNewbJoin(final PlayerJoinEvent event)
    {
        if (event.getPlayer().hasPlayedBefore())
            return;
        if (WildTP.newPlayersTeleported)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    event.getPlayer().performCommand("wild");
                }
            }.runTaskLater(instace, 1L);
        }
    }

    @EventHandler(ignoreCancelled = true)
    void atm(ChunkUnloadEvent event)
    {
        if (instace.cash == null)
            return;
        Location location = event.getChunk().getBlock(7, 64, 7).getLocation();
        int distance = instace.getServer().getViewDistance() * 17;
        if ((Math.abs(instace.cash.getBlockX() - location.getBlockX()) <= (distance + 8)) && (Math.abs(instace.cash.getBlockZ() - location.getBlockZ()) <= (distance + 8))) //ergh
        {
            event.setCancelled(true);
        }
    }
}
