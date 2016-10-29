package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Lax on 5/6/2016.
 */
public class AdminsGoneWild implements CommandExecutor {

    String[] halpMessage = {
            "&6-------------------Help-------------------------",
            "&6* Command:          Description:                *",
            "&6* /Wild - Teleports player to random location  *",
            "&6* /Wild [player] - Random teleport a player    *",
            "&6* /WildTP reload - Reloads the plugin's config *",
            "&6* /WildTP - shows this help message            *",
            "&6------------------------------------------------"
    };

    WildTP tpWild;

    public AdminsGoneWild(WildTP wildTP) {
        this.tpWild = wildTP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            for (String aHalpMessage : halpMessage) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', aHalpMessage));
            }
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("wild.wildtp.reload")) {
                tpWild.reloadConfig();
                tpWild.getWild();
                sender.sendMessage(TooWildForEnums.translate(TooWildForEnums.RELOADED));
            }
            if (args[0].equalsIgnoreCase("gui") && sender.hasPermission("wild.wildtp.set") && sender instanceof Player) {
                new GeeYouEye().openMenu((Player) sender);
            }
            if (args[0].equalsIgnoreCase("create") && sender.hasPermission("wild.wildtp.create.portal")) {
                if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
                    TooWildForEnums.translate(TooWildForEnums.NO_WE);
                    WildTP.debug(sender.getName() + " Tried to make a portal without WE");
                    return true;
                }
                tpWild.getPortalz().createPortal((Player) sender, args[1]);
            }
        }
        return false;
    }
}
