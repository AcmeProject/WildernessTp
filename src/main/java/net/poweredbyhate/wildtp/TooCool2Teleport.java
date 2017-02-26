package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RoboMWM on 5/28/2016.
 */
public class TooCool2Teleport implements Listener {
    static Map<Player, BukkitTask> coldTaxs = new HashMap<>();
    static Map<Player, Location> coldPlayers = new HashMap<>();

    @EventHandler
    void onPlayerThinkTheyAre2Hot(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!isCold(player))
            return;
        try
        {
            if (coldPlayers.get(player).distanceSquared(event.getTo()) < 0.3D)
                return;
        }
        catch (IllegalArgumentException e) {} //Teleported to another world via another plugin
        player.sendMessage(TooWildForEnums.translate(TooWildForEnums.DIDNT_WAIT));
        microwave(player);
    }

    public static void addPlayer(Player player, BukkitTask taxs) {
        if (coldPlayers.containsKey(player))
            microwave(player);
        coldPlayers.put(player, player.getLocation());
        coldTaxs.put(player, taxs);
    }

    public static boolean isCold(Player player) {
        return coldPlayers.containsKey(player);
    }

    public static void microwave(Player player) {
        coldPlayers.remove(player);
        coldTaxs.get(player).cancel();
        coldTaxs.remove(player);
    }

    public static void makeHot(Player player) {
        coldPlayers.remove(player);
        coldTaxs.remove(player);
    }
}
