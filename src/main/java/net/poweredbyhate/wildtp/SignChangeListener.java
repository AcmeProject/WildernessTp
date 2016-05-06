package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent ev) {
        if (ev.getLine(0).equalsIgnoreCase("[wildtp]") || ev.getLine(1).equalsIgnoreCase("[wildtp]")) {
            if (ev.getPlayer().getLocation().getBlock().getBiome() == Biome.HELL || ev.getPlayer().getLocation().getBlock().getBiome() == Biome.SKY) {
                ev.getPlayer().sendMessage(ChatColor.RED + "You may not put signs in " + ev.getPlayer().getLocation().getBlock().getBiome());
                ev.setCancelled(true);
                ev.getBlock().breakNaturally();
            }
            if (ev.getPlayer().hasPermission("wild.wildtp.create.sign")) {
                ev.setLine(0, ChatColor.translateAlternateColorCodes('&', "&4===================="));
                ev.setLine(1, ChatColor.translateAlternateColorCodes('&', "[&1Wild&0]"));
                ev.setLine(2, ChatColor.translateAlternateColorCodes('&', "&4===================="));
                ev.getPlayer().sendMessage(ChatColor.GREEN + "Successfully made a new WildTP sign");
            }
        }
    }
}