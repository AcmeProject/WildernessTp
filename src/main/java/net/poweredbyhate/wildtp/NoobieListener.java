package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.instace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created on 7/9/2017.
 *
 * @author RoboMWM
 */
public class NoobieListener implements Listener {
    @EventHandler
    private void onNewbJoin(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        if (p.hasPlayedBefore()) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                new TeleportGoneWild(Trigger.JOIN, p).WildTeleport();
            }
        }.runTaskLater(instace, 1L);
    }
}
