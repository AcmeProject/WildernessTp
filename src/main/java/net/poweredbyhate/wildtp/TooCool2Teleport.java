package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by RoboMWM on 5/28/2016.
 */
public class TooCool2Teleport implements Listener {
    static Map<Player, Location> coldPlayers = new HashMap<>();

    @EventHandler
    void onPlayerThinkTheyAre2Hot(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!isCold(player))
            return;
        if (coldPlayers.get(player).distanceSquared(event.getTo()) < 0.3D)
            return;
        player.sendMessage(TooWildForEnums.translate(TooWildForEnums.DIDNT_WAIT));
        makeHot(player);
    }

    public static void addPlayer(Player player) {
        coldPlayers.put(player, player.getLocation());
    }

    public static boolean isCold(Player player) {
        return coldPlayers.containsKey(player);
    }

    public static void makeHot(Player player) {
        coldPlayers.remove(player);
    }
}
