package net.poweredbyhate.wildtp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Lax on 5/6/2016.
 */
public class AdminsGoneWild implements CommandExecutor {

    WildTP tpWild;

    public AdminsGoneWild(WildTP wildTP) {
        this.tpWild = wildTP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("wild.wildtp.reload")) {
                tpWild.getWild();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&0[&aWildnernessTP&0]&aPlugin config has successfuly been reload."));
            } else {
                sender.sendMessage(ChatColor.RED + "Sorry you do not have permission to reload the plugin");
            }
        }
        return false;
    }
}
