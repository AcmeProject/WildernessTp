package net.poweredbyhate.wildtp;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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
    public static Economy econ;
    public static boolean dr0p1n;
    public static ConfigurationSection randomeWorlds;
    public static boolean useRandomeWorldz;
    public static boolean newPlayersTeleported;
    public static boolean useExperimentalChekar;
    public static boolean noCreditJustCash;
    public static DataStore dataaaastorege;
    public static boolean outdatedServer = false;
    public static Location cash;

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
        Bukkit.getPluginManager().registerEvents(new CNNListener(), this);
        Bukkit.getPluginManager().registerEvents(new NoobieListener(), this);
        if (wamuppah > 0)
            Bukkit.getPluginManager().registerEvents(new TooCool2Teleport(), this);
        try
        {
            if (Integer.valueOf(Bukkit.getBukkitVersion().split("\\.")[1]) < 12)
                outdatedServer = true;
        }
        catch (Throwable ball){}
    }

    public void getWild() {
        wildDependencies();
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
        newPlayersTeleported = getConfig().getBoolean("teleportNewPlayers");
        useExperimentalChekar = getConfig().getBoolean("useExperimentalClaimCheck");
        noCreditJustCash = getConfig().getBoolean("preloadChunks");
    }

    public void wildConfig(FileConfiguration fc) {
        Map<String, Object> wildDefault = new LinkedHashMap<>();
        Map<String, Integer> randomWorlds = new LinkedHashMap<>();
        randomWorlds.put("world", 1337);
        randomWorlds.put("world_nether", 42);
        String[] eh = {"title %PLAYER% times 20 100 20","title %PLAYER% title [\"\",{\"text\":\"Wilderness\",\"color\":\"green\",\"bold\":false}]","title %PLAYER% subtitle [\"\",{\"text\":\"Its too dangerous to go alone.\",\"color\":\"yellow\"}]"};
        String[] ehh = {"DEEP_OCEAN", "OCEAN","FROZEN_OCEAN","DEEP_COLD_OCEAN","DEEP_FROZEN_OCEAN","DEEP_LUKEWARM_OCEAN", "DEEP_WARM_OCEAN","COLD_OCEAN","FROZEN_OCEAN","WARM_OCEAN","LUKEWARM_OCEAN","RIVER"};
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
        wildDefault.put("teleportNewPlayers", false);
        wildDefault.put("useExperimentalClaimCheck", dataaaastorege == null);
        wildDefault.put("preloadChunks", true);
        try
        {
            for (Map.Entry<String, Object> s : wildDefault.entrySet()) {
                if (!fc.contains(s.getKey(),false)) {
                    getConfig().set(s.getKey(), s.getValue());
                }
            }
        }
        catch (NoSuchMethodError updateUrSerburz)
        {
            fc.addDefaults(wildDefault);
            fc.options().copyDefaults(true);
        }

        saveConfig();
    }

    public void wildDependencies() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
        }
        try
        {
            if (getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
                GriefPrevention antgreif = (GriefPrevention)getServer().getPluginManager().getPlugin("GriefPrevention");
                dataaaastorege = antgreif.dataStore;
            }
        }
        catch (Throwable rock){}
    }

    public static void debug(Object o) {
        if (isDebug) System.out.println("[WildTP] "+o);
    }

    public PortalzGoneWild getPortalz() {
        return portalz;
    }

    public void wildMetrics() {
        try {
            Metricsa metrics = new Metricsa(this);
            metrics.start();
        } catch (IOException e) {
        }
        try
        {
            Metrics metrics = new Metrics(this);
            metrics.addCustomChart(new Metrics.SimplePie("bukkit_impl", new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {
                    return getServer().getVersion().split("-")[1];
                }
            }));
            for (final String key : getConfig().getKeys(false))
            {
                if (getConfig().isConfigurationSection(key) || getConfig().isList(key) || getConfig().isSet(key))
                    continue;
                metrics.addCustomChart(new Metrics.SimplePie(key, new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return getConfig().getString(key);
                    }
                }));
            }
        }
        catch (Throwable rock){}
    }
}
