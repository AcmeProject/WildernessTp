package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    public static int[] maxXY = {5000,5000};
    public static int[] minXY = {-5000,-5000};

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
    }
}
