package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by John on 5/5/2016.
 */
public class SignClickListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && ev.getClickedBlock().getState() instanceof Sign) {
            if (ChatColor.stripColor(((Sign) ev.getClickedBlock().getState()).getLine(1)).equalsIgnoreCase("[wild]")) {
                new TeleportGoneWild().WildTeleport(ev.getPlayer());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent ev) {
        if (ev.getBlock().getState() instanceof Sign) {
            if(ChatColor.stripColor(((Sign) ev.getBlock().getState()).getLine(1)).equalsIgnoreCase("[wild]") && ev.getPlayer().hasPermission("wild.wildtp.break.sign")) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + "You have broken a WildTP sign");
            } else {
                ev.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Hey! You may not break WildTP sign!"));
            }
        }
    }
}
