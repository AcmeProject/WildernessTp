package net.poweredbyhate.wildtp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import sun.awt.CausedFocusEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by RoboMWM on 5/15/2016.
 */
public class OuchieListener implements Listener
{
    private static Set<Player> dr0pp3dPuhlayars = new HashSet<Player>();
    @EventHandler(ignoreCancelled = true)
    void onPlayerTryingToBreakLegs(EntityDamageEvent ouch)
    {
        //time2microoptimize
        if (!(ouch.getEntity() instanceof Player))
            return;

        if (ouch.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        Player player = (Player)ouch.getEntity();

        if (dr0pp3dPuhlayars.remove(player))
            ouch.setCancelled(true);
    }

    public static void plsSaveDisDood(final Player player)
    {
        //2 lazy 2 check if runnable exists 4 dis dood already, 2 bad so sad
        if (!dr0pp3dPuhlayars.contains(player))
        {
            dr0pp3dPuhlayars.add(player);
            new BukkitRunnable()
            {
                public void run()
                {
                    dr0pp3dPuhlayars.remove(player);
                }
            }.runTaskLater(WildTP.instace, 400L);
        }
    }
}
