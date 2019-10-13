package net.poweredbyhate.wildtp;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by RoboMWM on 5/15/2016.
 */
public class OuchieListener implements Listener {
    private static HashMap<UUID, Integer> dr0pp3dPuhlayars = new HashMap<UUID, Integer>();

    @EventHandler(ignoreCancelled = true)
    private void onPlayerTryingToBreakLegs(EntityDamageEvent ouch) {
        if (!(ouch.getEntity() instanceof Player) || ouch.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        UUID id = ouch.getEntity().getUniqueId();

        if (dr0pp3dPuhlayars.containsKey(id)) {
            Bukkit.getScheduler().cancelTask(dr0pp3dPuhlayars.remove(id));
            ouch.setCancelled(true);
        }
    }

    static void plsSaveDisDood(final Player player) {
        UUID id = player.getUniqueId();
        
        TooCool2Teleport.microwave(player);
        
        dr0pp3dPuhlayars.put(id, Bukkit.getScheduler().scheduleSyncDelayedTask(WildTP.instace, new Runnable() {
            @Override
            public void run() {
                dr0pp3dPuhlayars.remove(id);
            }
        }, 400L));
    }

    static void doodWhrsMyCar(UUID sweet) {
        if (dr0pp3dPuhlayars.containsKey(sweet)) Bukkit.getScheduler().cancelTask(dr0pp3dPuhlayars.remove(sweet));
    }
}
