package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    String world;

    @EventHandler
    public void onSignChange(SignChangeEvent ev) {
        if (ev.getLine(0).equalsIgnoreCase("[wildtp]") || ev.getLine(1).equalsIgnoreCase("[wildtp]")) {
            if (ev.getLine(2) != null) {
                world = ev.getLine(2);
            }
            if (ev.getPlayer().getLocation().getBlock().getBiome() == Biome.HELL || ev.getPlayer().getLocation().getBlock().getBiome() == Biome.SKY) {
                ev.getPlayer().sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_BIOME.replace("%BIOME%", ev.getPlayer().getLocation().getBlock().getBiome().toString())));
                ev.setCancelled(true);
                ev.getBlock().breakNaturally();
                return;
            }
            if (ev.getPlayer().hasPermission("wild.wildtp.create.sign")) {
                ev.setLine(0, ChatColor.translateAlternateColorCodes('&', "&4===================="));
                ev.setLine(1, ChatColor.translateAlternateColorCodes('&', "[&1Wild&0]"));
                ev.setLine(2, ChatColor.translateAlternateColorCodes('&', "&4===================="));
                ev.setLine(3, world);
                ev.getPlayer().sendMessage(TooWildForEnums.translate(TooWildForEnums.YES_SIGN));
            } else {
                ev.getPlayer().sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_SIGN_PERMS));
                ev.setCancelled(true);
            }
        }
    }
}