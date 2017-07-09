package net.poweredbyhate.wildtp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
            }.runTaskLater(WildTP.instace, 1L);
        }
    }
}
