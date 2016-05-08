package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 5/6/2016.
 */
public class CommandsGoneWild implements CommandExecutor {

    WildTP olivia;

    public CommandsGoneWild(WildTP wilde) {
        this.olivia = wilde;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.MAGIC + "One does not simply go wild.");
            return false;
        }
        if (sender.hasPermission("wild.wildtp") && args.length == 0) {
            Player p = (Player) sender;
            new TeleportGoneWild().WildTeleport(p);
        }
        if (args.length >= 1) {
            if (sender.getServer().getPlayer(args[0]) != null && sender.hasPermission("wild.wildtp.others")) {
                new TeleportGoneWild().WildTeleport(sender.getServer().getPlayer(args[0]));
            }
        }
        return false;
    }
}
