package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RoboMWM on 5/15/2016.
 */
public class OuchieListener implements Listener {
    private static Map<Player, Integer> dr0pp3dPuhlayars = new HashMap<>();
    static BukkitScheduler scheduler = Bukkit.getScheduler();
    @EventHandler(ignoreCancelled = true)
    void onPlayerTryingToBreakLegs(EntityDamageEvent ouch) {
        if (!(ouch.getEntity() instanceof Player))
            return;

        if (ouch.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        Player player = (Player)ouch.getEntity();

        if (dr0pp3dPuhlayars.containsKey(player)) {
            scheduler.cancelTask(dr0pp3dPuhlayars.remove(player));
            ouch.setCancelled(true);
        }

    }

    public static void plsSaveDisDood(final Player player) {
        if (dr0pp3dPuhlayars.containsKey(player))
            scheduler.cancelTask(dr0pp3dPuhlayars.remove(player));
        Integer bleh = scheduler.scheduleSyncDelayedTask(WildTP.instace, new Runnable() {
            @Override
            public void run() {
                dr0pp3dPuhlayars.remove(player);
            }
        }, 400L);
        dr0pp3dPuhlayars.put(player, bleh);
    }
}
