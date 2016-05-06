package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    public static WildTP instace;
    public static int maxXY = 5000;
    public static int minXY = -5000;
    public static int retries = 10;

    public void onEnable() {
        instace = this;
        getWild();
        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
    }

    public void getWild() {
        saveDefaultConfig();
        maxXY = getConfig().getInt("MaxXY");
        minXY = getConfig().getInt("MinXY");
        retries = getConfig().getInt("Retries");
    }
}
