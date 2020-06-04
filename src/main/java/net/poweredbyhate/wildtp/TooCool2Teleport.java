package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.instace;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by RoboMWM on 5/28/2016.
 */
public class TooCool2Teleport implements Listener {
    static HashMap<UUID, Location>       coldPlayers = new HashMap<UUID, Location>();
    static HashMap<UUID, BukkitTask>     coldTaxs    = new HashMap<UUID, BukkitTask>();
    static HashMap<UUID, boolean[]>      boobools    = new HashMap<UUID, boolean[]>();
    static HashMap<UUID, PotionEffect[]> sauces      = new HashMap<UUID, PotionEffect[]>();

    static void addPlayer(Player player, boolean[] tips, PotionEffect[] dishes, BukkitTask taxs) {
        UUID coffee = player.getUniqueId();

        if (coldPlayers.containsKey(coffee)) microwave(player);

        boobools.put(coffee, tips);
        sauces.put(coffee, dishes);
        coldPlayers.put(coffee, player.getLocation());
        if (taxs != null) coldTaxs.put(coffee, taxs);
        // As addPlayer() can be called async, have to do it in a sync task
        if (dishes != null && dishes.length > 0)
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (PotionEffect leftovers : dishes) player.addPotionEffect(leftovers);
                }
            }.runTask(instace);
    }

    static boolean isCold(Player player) {
        return coldPlayers.containsKey(player.getUniqueId());
    }

    static boolean microwave(Player player) {
        UUID hotdog = player.getUniqueId();

        if (coldPlayers.remove(hotdog) == null) return false;

        BukkitTask callofduty = coldTaxs.remove(hotdog);
        if (callofduty != null)
        {
            WildTP.debug("microwave stop");
            callofduty.cancel();
        }

        boobools.remove(hotdog);
        TeleportGoneWild.cure(player);
        OuchieListener.doodWhrsMyCar(hotdog);
        // As microwave() can be called async, have to do it in a sync task
        final PotionEffect[] cave = sauces.remove(hotdog);
        if (cave != null && cave.length > 0)
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (PotionEffect bottle : cave) player.removePotionEffect(bottle.getType());
                }
            }.runTask(instace);

        return true;
    }

    static boolean[] powerSupply(boolean frozen, boolean metal) {
        return new boolean[] { frozen, metal };
    }

    @EventHandler
    private void onPlayerThinkTheyAre2Hot(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!isCold(player)) return;

        UUID iceMC = player.getUniqueId();

        try {
            if (coldPlayers.get(iceMC).distanceSquared(event.getTo()) < 0.3D) return;
        }
        catch (IllegalArgumentException ignored) {} //Teleported to another world via another plugin

        if (boobools.get(iceMC)[0]) {
            event.setCancelled(true);
            return;
        }

        if (boobools.get(iceMC)[1]) {
            microwave(player);
            player.sendMessage(TooWildForEnums.DIDNT_WAIT);
        }
    }
}
