package net.poweredbyhate.wildtp;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    static boolean isDebug;

    public static WildTP instace;
    public static TooWildForEnums enums = new TooWildForEnums();
    public static PortalzGoneWild portalz = new PortalzGoneWild();
    public static ChecKar checKar = new ChecKar();
    public static int maxXY = 5000;
    public static int minXY = -5000;
    public static int retries = 10;
    public static int coolDownTeim = 30;
    public static int wamuppah = 1;
    public static int cost = 0;
    public static boolean doCommandz;
    public static boolean ifurwildandunoitclapurhands = true;
    public static Economy econ;
    public static boolean dr0p1n;
    public static ConfigurationSection randomeWorlds;
    public static boolean useRandomeWorldz;
    DataStore dataaaastorege;

    public void onEnable() {
        saveDefaultConfig();
        instace = this;
        getWild();
        wildMetrics();
        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        getCommand("wildtp").setExecutor(new AdminsGoneWild(this));
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GeeYouEye(), this);
        Bukkit.getPluginManager().registerEvents(new PostTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PreTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OuchieListener(), this);
        Bukkit.getPluginManager().registerEvents(portalz, this);
        if (wamuppah > 0)
            Bukkit.getPluginManager().registerEvents(new TooCool2Teleport(), this);
    }

    public void getWild() {
        wildConfig(getConfig());
        new TooWildForEnums().loadConfig();
        portalz.loadConfig();
        coolDownTeim = getConfig().getInt("Cooldown");
        maxXY = getConfig().getInt("MaxXY");
        minXY = getConfig().getInt("MinXY");
        retries = getConfig().getInt("Retries");
        doCommandz = getConfig().getBoolean("DoCommands");
        cost = getConfig().getInt("Cost");
        wamuppah = getConfig().getInt("Wait");
        dr0p1n = getConfig().getBoolean("dropPlayerFromAbove");
        useRandomeWorldz = getConfig().getBoolean("useRandomWorlds");
        randomeWorlds = getConfig().getConfigurationSection("randomWorlds");
        isDebug = getConfig().getBoolean("debug");
    }

    public void wildConfig(FileConfiguration fc) {
        Map<String, Object> wildDefault = new LinkedHashMap<>();
        Map<String, Integer> randomWorlds = new LinkedHashMap<>();
        randomWorlds.put("world", 1337);
        randomWorlds.put("world_nether", 42);
        String[] eh = {"title %PLAYER% times 20 100 20","title %PLAYER% title [\"\",{\"text\":\"Wilderness\",\"color\":\"green\",\"bold\":false}]","title %PLAYER% subtitle [\"\",{\"text\":\"Its too dangerous to go alone.\",\"color\":\"yellow\"}]"};
        String[] ehh = {"DEEP_OCEAN", "OCEAN","FROZEN_OCEAN"};
        wildDefault.put("MaxXY", 5000);
        wildDefault.put("MinXY", -5000);
        wildDefault.put("Retries", 5);
        wildDefault.put("Cooldown", 30);
        wildDefault.put("Cost", 0);
        wildDefault.put("Wait", 5);
        wildDefault.put("dropPlayerFromAbove", false);
        wildDefault.put("Sound", "ENTITY_ENDERMEN_TELEPORT");
        wildDefault.put("DoCommands", false);
        wildDefault.put("PostCommands", eh);
        wildDefault.put("BlockedBiomes", ehh);
        wildDefault.put("useRandomWorlds", false);
        wildDefault.put("debug", false);
        wildDefault.put("randomWorlds", randomWorlds);
        for (Map.Entry<String, Object> s : wildDefault.entrySet()) {
            if (!fc.contains(s.getKey(),false)) {
                getConfig().set(s.getKey(), s.getValue());
            }
        }
        saveConfig();
    }

    public void wildDependencies() {
        if (getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
            GriefPrevention antgreif = (GriefPrevention)getServer().getPluginManager().getPlugin("GriefPrevention");
            dataaaastorege = antgreif.dataStore;
        }
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
        }
    }

    public static void debug(Object o) {
        if (isDebug) System.out.println("[WildTP] "+o);
    }

    public PortalzGoneWild getPortalz() {
        return portalz;
    }

    public void wildMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
        }
    }
}
