package net.poweredbyhate.wildtp;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    public static WildTP instace;
    public static int maxXY = 5000;
    public static int minXY = -5000;
    public static int retries = 10;
    public static int coolDownTeim = 30;
    public static int wamuppah = 10;
    GriefPrevention antgreif;
    DataStore dataaaastorege;

    public void onEnable() {
        instace = this;
        getWild();
        wildMetrics();
        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        getCommand("wildtp").setExecutor(new AdminsGoneWild(this));
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GeeYouEye(), this);
        if (getServer().getPluginManager().getPlugin("GriefPrevention") != null)
        {
            antgreif = (GriefPrevention)getServer().getPluginManager().getPlugin("GriefPrevention");
            dataaaastorege = antgreif.dataStore;
        }
    }

    public void getWild() {
        saveDefaultConfig();
        coolDownTeim = getConfig().getInt("Cooldown");
        maxXY = getConfig().getInt("MaxXY");
        minXY = getConfig().getInt("MinXY");
        retries = getConfig().getInt("Retries");
    }

    public void wildMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
        }
    }
}
