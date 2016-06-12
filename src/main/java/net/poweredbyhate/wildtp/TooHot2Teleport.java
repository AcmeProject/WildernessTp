package net.poweredbyhate.wildtp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by RoboMWM on 5/28/2016.
 */
public class TooHot2Teleport implements Listener {
    static Set<Player> coldPlayers = new HashSet<>();

    @EventHandler
    void onPlayerThinkTheyAre2Hot(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!coldPlayers.contains(player))
            return;
        if (event.getFrom().distance(event.getTo()) == 0)
            return;
        player.sendMessage(TooWildForEnums.translate(TooWildForEnums.DIDNT_WAIT));
        coldPlayers.remove(player);
    }

    public static void addPlayer(Player player) {
        coldPlayers.add(player);
    }

    public static boolean isCold(Player player) {
        return coldPlayers.contains(player);
    }

    public static void makeHot(Player player) {
        coldPlayers.remove(player);
    }
}
