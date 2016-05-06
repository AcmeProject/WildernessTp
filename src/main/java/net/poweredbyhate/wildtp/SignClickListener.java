package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by John on 5/5/2016.
 */
public class SignClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK && ev.getClickedBlock().getState() instanceof Sign ) {
            if (ChatColor.stripColor(((Sign) ev.getClickedBlock().getState()).getLine(1)).equalsIgnoreCase("[wildtp]")) {
                new TeleportGoneWild().WildTeleport(ev.getPlayer());
            }
        }
    }
}
