package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
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
                if (Bukkit.getWorld(((Sign) ev.getClickedBlock().getState()).getLine(3)) != null) {
                    new TeleportGoneWild().WildTeleport(ev.getPlayer(), ((Sign) ev.getClickedBlock().getState()).getLine(3));
                }
                new TeleportGoneWild().WildTeleport(ev.getPlayer());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent ev) {
        if (ev.getBlock().getState() instanceof Sign) {
            if(ChatColor.stripColor(((Sign) ev.getBlock().getState()).getLine(1)).equalsIgnoreCase("[wild]")) {
                if (ev.getPlayer().hasPermission("wild.wildtp.break.sign")) {
                    ev.getPlayer().sendMessage(TooWildForEnums.translate(TooWildForEnums.BREAK_SIGN));
                } else {
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_BREAK));
                }
            }
        }
    }
}
