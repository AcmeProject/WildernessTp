package net.poweredbyhate.wildtp;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.originmc.fbasics.factions.api.FactionsHook;
import org.originmc.fbasics.factions.api.IFactionsHook;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    static boolean isDebug = true;

    public static WildTP instace;
    public static TooWildForEnums enums = new TooWildForEnums();
    public static int maxXY = 5000;
    public static int minXY = -5000;
    public static int retries = 10;
    public static int coolDownTeim = 30;
    public static int wamuppah = 1;
    public static int cost = 0;
    public static boolean doCommandz;
    public static Economy econ;
    public static boolean dr0p1n;
    public static ConfigurationSection randomeWorlds;
    public static boolean useRandomeWorldz;
    public static IFactionsHook fractions;
    DataStore dataaaastorege;

    public void onEnable() {
        saveDefaultConfig();
        instace = this;
        getWild();
        wildDependencies();
        wildMetrics();
        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        getCommand("wildtp").setExecutor(new AdminsGoneWild(this));
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GeeYouEye(), this);
        Bukkit.getPluginManager().registerEvents(new PostTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PreTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OuchieListener(), this);
        if (wamuppah > 0)
            Bukkit.getPluginManager().registerEvents(new TooCool2Teleport(), this);
    }

    public void getWild() {
        wildConfig(getConfig());
        new TooWildForEnums().loadConfig();
        coolDownTeim = getConfig().getInt("Cooldown");
        maxXY = getConfig().getInt("MaxXY");
        minXY = getConfig().getInt("MinXY");
        retries = getConfig().getInt("Retries");
        doCommandz = getConfig().getBoolean("DoCommands");
        cost = getConfig().getInt("Cost");
        wamuppah = getConfig().getInt("Wait");
        dr0p1n = getConfig().getBoolean("dropPlayerFromAbove");
        //Not "just" multiworld support, Qball
        useRandomeWorldz = getConfig().getBoolean("useRandomWorlds");
        randomeWorlds = getConfig().getConfigurationSection("randomWorlds");
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
        wildDefault.put("randomWorlds", randomWorlds);
        for (Map.Entry<String, Object> s : wildDefault.entrySet()) {
            if (!fc.contains(s.getKey(),false)) {
                getConfig().set(s.getKey(), s.getValue());
            }
        }
        saveConfig();
    }

    public void wildDependencies() {
        StringBuilder deps = new StringBuilder();
        if (getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
            GriefPrevention antgreif = (GriefPrevention)getServer().getPluginManager().getPlugin("GriefPrevention");
            dataaaastorege = antgreif.dataStore;
            deps.append("GriefPrevention, ");
        }
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
            deps.append("Vault, ");
        }
        if (getServer().getPluginManager().getPlugin("Factions") != null)
        {
            //Thx Sudzzy
            String version;
            Plugin plugin = getServer().getPluginManager().getPlugin("Factions");
            String[] v = plugin.getDescription().getVersion().split("\\.");
            version = v[0] + "_" + v[1];
            if (version.compareTo("1_6") < 0) {
                version = "1_6";
            } else if (version.compareTo("2_7") > 0) {
                version = "2_7";
            }
            if (version == null) {
                debug("Factions version is null");
                fractions = null;
                return;
            }
            String className = "org.originmc.fbasics.factions.v" + version + ".FactionsHook";

            try {
                fractions = (IFactionsHook) Class.forName(className).newInstance();
                deps.append("Factions " + version + ", ");
            } catch (Exception e) {
                fractions = null;
                debug("weird stuff " + version + "\n");
                e.printStackTrace();
            }
        }
        String d3p5 = deps.toString();
        if (d3p5 != null && !d3p5.isEmpty())
            getLogger().info("Hooked into " + d3p5.substring(0, d3p5.length() - 3));
    }

    public static void debug(Object o) {
        if (isDebug) System.out.println("[WildTP] "+o);
    }

    public void wildMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
        }
    }
}
