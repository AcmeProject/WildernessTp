package net.poweredbyhate.wildtp;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {

    private boolean wamuppahTooCool = false;

    static boolean isDebug;

    public static WildTP instace;
    public static TooWildForEnums enums = new TooWildForEnums();
    public static PortalzGoneWild portalz = new PortalzGoneWild();
    
    public static Economy econ;
    public static ConfigurationSection randomeWorlds;
    public static boolean useRandomeWorldz;
    public static boolean newPlayersTeleported;
    public static boolean useExperimentalChekar;
    public static boolean useOtherChekar;
    public static boolean noCreditJustCash;
    public static DataStore dataaaastorege;
    public static boolean wb;
    public static boolean notPaper;
    public static Location cash;
    String[] bluredLines;
    HashMap<String, String> aliaz;
    HashMap<String, WorldConfig> thugz;
    HashMap<UUID, BukkitRunnable> ohWait;

    public void onEnable() {
        if (Integer.valueOf(Bukkit.getBukkitVersion().split("\\.")[1]) <= 12) {
            getLogger().severe("This version of Wilderness-TP does not support your ancient server version.");
            getLogger().warning("Either update your server to 1.13, or use Wild 1.52");
            getLogger().warning("https://robomwm.com"); // URL changed because the old one no more works
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instace = this;
        ohWait  = new HashMap<UUID, BukkitRunnable>();

        try { Class.forName("com.destroystokyo.paper.PaperConfig"); } catch (Throwable cue) {
            notPaper = true;
            getLogger().info(" = = = = = = = = = = = = = = = = = = = =");
            getLogger().info(" ");
            getLogger().warning("Btw, dis wild plugin (and server) would be a bazillion times");
            getLogger().warning("faster and wilder if u switch 2 Paper.");
            getLogger().warning("Until then, we'll just go wild at ur serburs slower/laggier pace.");
            getLogger().warning(" ");
            getLogger().warning("Learn and get Paper (it's ez) at https://papermc.io");
            getLogger().info(" ");
            getLogger().info(" = = = = = = = = = = = = = = = = = = = =");
        }

        wildDependencies();
        reloadConfig();
        wildMetrics();

        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        getCommand("wildtp").setExecutor(new AdminsGoneWild(this));

        Bukkit.getPluginManager().registerEvents(new SignChangeListener(instace), this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(instace), this);
        Bukkit.getPluginManager().registerEvents(new GeeYouEye(), this);
        Bukkit.getPluginManager().registerEvents(new PostTeleportEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PreTeleportEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new OuchieListener(), this);
        Bukkit.getPluginManager().registerEvents(portalz, this);
        Bukkit.getPluginManager().registerEvents(new CNNListener(), this);
        Bukkit.getPluginManager().registerEvents(new NoobieListener(), this);
        if (wamuppahTooCool) Bukkit.getPluginManager().registerEvents(new TooCool2Teleport(), this);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        new TooWildForEnums().loadConfig();
        // Copy the default config file from jar/resources/config.yml to plugins/wild/config.yml if not exists
        saveDefaultConfig();
        // Read the config (existing config or new one)
        FileConfiguration config = getConfig();
        // Merge default values
        config.addDefault("useGlobalClaimCheck", dataaaastorege == null);
        config.options().copyDefaults(true);
        // Do things with worlds
        aliaz = new HashMap<String,String>();
        thugz = new HashMap<String,WorldConfig>();
        ChecKar sharedOrNot = config.getBoolean("shareCoolDown")? new ChecKar(config.getInt("Cooldown")): null;
        wamuppahTooCool = (config.getInt("Wait") > 0);
        for (World world : Bukkit.getWorlds()) {
          String name = world.getName(), alias = config.getString("worldsAliaz." + name, name);
          config.set("worldsAliaz." + name, alias);
          WorldConfig wc = new WorldConfig(name, config, sharedOrNot);
          wamuppahTooCool |= (wc.wamuppah > 0);
          aliaz.put(name, alias);
          thugz.put(name, wc);
        }
        // Re-save (so omitted and new parameters are added in the file)
        saveConfig();
        // Take off your clothes
        getWild(config);
    }

    public void getWild(FileConfiguration config) {
        portalz.loadConfig();
        useRandomeWorldz = config.getBoolean("useRandomWorlds");
        randomeWorlds = config.getConfigurationSection("randomWorlds");
        isDebug = config.getBoolean("debug");
        newPlayersTeleported = config.getBoolean("teleportNewPlayers");
        useExperimentalChekar = config.getBoolean("useGlobalClaimCheck");
        useOtherChekar = config.getBoolean("useAlternativeGlobalClaimCheck");
        bluredLines = new String[] {
            TooWildForEnums.translate(config.getString("signTexts.line1")),
            TooWildForEnums.translate(config.getString("signTexts.line2")),
            TooWildForEnums.translate(config.getString("signTexts.line3")),
            config.getString("signTexts.createWith"),
            TooWildForEnums.translate(config.getString("signTexts.costFree")),
            TooWildForEnums.translate(config.getString("signTexts.costMoney"))
        };
        //noCreditJustCash = config.getBoolean("preloadChunks");
    }

    public void wildDependencies() {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (getServer().getPluginManager().getPlugin("Vault") != null) {
                    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
                    if (rsp != null)
                        econ = rsp.getProvider();
                }
            }
        }.runTask(instace);
        try
        {
            dataaaastorege = null;
            if (getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
                GriefPrevention antgreif = (GriefPrevention)getServer().getPluginManager().getPlugin("GriefPrevention");
                dataaaastorege = antgreif.dataStore;
            }
        }
        catch (Throwable rock){}
        wb = getServer().getPluginManager().isPluginEnabled("WorldBorder");
    }

    public static void debug(Object o) {
        if (isDebug) System.out.println("[WildTP] "+o);
    }

    public PortalzGoneWild getPortalz() {
        return portalz; // The cake is a lie!
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
                if (!getConfig().isBoolean(key) && !getConfig().isInt(key) && !getConfig().isString(key))
                    continue;
                metrics.addCustomChart(new Metrics.SimplePie(key.toLowerCase(), new Callable<String>()
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

    // @see SignClickListener & SignChangeListener

    boolean isBorn2bWild(String[] bluredLines) {
        for (String l : bluredLines) if (l.equalsIgnoreCase(bluredLines[3])) return true;
        return false;
    }

    boolean isThisRealLife(String[] teeth, String kid) {
        String thug = seekAsylum(teeth, false);
        if (thug == null) thug = kid;
        for (int i = 0; i < 3; i++) if (!teeth[i].equals(bluredLines[i].replace("%COST%", moneyOrNuttin(thug)))) return false;
        return true;
    }

    String moneyOrNuttin(String direStr8) {
      int moneypenny = thugz.get(direStr8).cost;
      return (moneypenny == 0)? bluredLines[4]: bluredLines[5].replace("%COST%", "" + moneypenny);
    }

    String preferItSmall(String littleBig) {
      String skibidi = aliaz.get(littleBig);
      return (skibidi == null)? littleBig: skibidi;
    }

    String seekAsylum(String[] bluredLines, boolean baby) {
        if (bluredLines.length != 4) return null;
        int l = baby? 1: 3;
        if (Bukkit.getWorld(bluredLines[l]) != null) return bluredLines[l];
        for (String w : aliaz.keySet()) if (aliaz.get(w).equals(bluredLines[l])) return w;
        return null;
    }
}
