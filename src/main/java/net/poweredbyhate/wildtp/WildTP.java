package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    public static WildTP instace;
    public static int[] maxXY = {5000,5000};
    public static int[] minXY = {-5000,-5000};

    public void onEnable() {
        instace = this;
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        boolean tacos = true;
        if (cmd.getName().equalsIgnoreCase("Wild"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("Oh hello there server master, would you like me to randomly teleport your serbur somewhere else?");
                return tacos;
            }

            final Player player1 = (Player)sender;
            final Player player = (Player)sender;

            if (!player1.hasPermission("Wild.wildtp"))
            {
                Player play_er = (Player)sender;
                sender.sendMessage(ChatColor.RED + "Hey u dont have permsioson to /wild! D: pls ask admin why");
                return tacos;
            }
            new TeleportGoneWild().WildTeleport(player);

        }
        return tacos;
    }

}
