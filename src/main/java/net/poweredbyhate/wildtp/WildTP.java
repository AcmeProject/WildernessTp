package net.poweredbyhate.wildtp;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;

/**
 * Created by John on 5/5/2016.
 */
public class WildTP extends JavaPlugin {
    private boolean wamuppahTooCool = false;
    // @formatter:off
    static boolean
        ab, enableUselessGUI, isDebug, newPlayersTeleported, noCreditJustCash, notPaper,
        useExperimentalChekar, useOtherChekar, useRandomeWorldz, wb;
    // @formatter:on
    static ConfigurationSection   randomeWorlds;
    static DataStore              dataaaastorege;
    static Economy                econ;
    static Location               cash;
    static TooWildForEnums        enums;
    static WildTP                 instace;
    String[]                      bluredLines;
    PortalzGoneWild               portalz;
    HashMap<String, String>       aliaz;
    HashMap<String, WorldConfig>  thugz;
    HashMap<UUID, BukkitRunnable> ohWait;

    public void onEnable() {
        if (Integer.valueOf(Bukkit.getBukkitVersion().split("\\.")[1]) <= 12) {
            getLogger().severe("This version of Wilderness-TP does not support your ancient server version.");
            getLogger().warning("Either update your server to 1.13, or use Wild 1.52");
            getLogger().warning("https://robomwm.com"); // URL changed because the old one no more works
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // @formatter:off
        try { Class.forName("com.destroystokyo.paper.PaperConfig"); } catch (Throwable cue) {
            getLogger().info(" = = = = = = = = = = = = = = = = = = = =");
            getLogger().info(" ");
            getLogger().warning("Btw, dis wild plugin (and server) would be a bazillion times");
            getLogger().warning("faster and wilder if u switch 2 Paper.");
            getLogger().warning("Until then, we'll just go wild at ur serburs slower/laggier pace.");
            getLogger().warning(" ");
            getLogger().warning("Learn and get Paper (it's ez) at https://papermc.io");
            getLogger().info(" ");
            getLogger().info(" = = = = = = = = = = = = = = = = = = = ="); notPaper = true;
        }
        // @formatter:on
        instace = this;
        ohWait  = new HashMap<UUID, BukkitRunnable>();

        wildDependencies();
        reloadConfig();
        wildMetrics();

        enums   = new TooWildForEnums();
        portalz = new PortalzGoneWild(this);

        getCommand("wild").setExecutor(new CommandsGoneWild(this));
        getCommand("wildtp").setExecutor(new AdminsGoneWild(this));

        Bukkit.getPluginManager().registerEvents(new WildSignListener(this), this);
        Bukkit.getPluginManager().registerEvents(new WildTPListener(), this);
        Bukkit.getPluginManager().registerEvents(new OuchieListener(), this);
        Bukkit.getPluginManager().registerEvents(portalz, this);
        Bukkit.getPluginManager().registerEvents(new CNNListener(), this);

        if (enableUselessGUI) Bukkit.getPluginManager().registerEvents(new GeeYouEye(), this);
        if (newPlayersTeleported) Bukkit.getPluginManager().registerEvents(new NoobieListener(), this);
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
        aliaz = new HashMap<String, String>();
        thugz = new HashMap<String, WorldConfig>();

        ChecKar sharedOrNot = config.getBoolean("shareCoolDown") ? new ChecKar(config.getInt("Cooldown")) : null;
        wamuppahTooCool = (config.getInt("Wait") > 0);

        for (World world : Bukkit.getWorlds()) {
            String name = world.getName(), alias = config.getString("worldsAliaz." + name, name);
            config.set("worldsAliaz." + name, alias);
            WorldConfig wc = new WorldConfig(world, config, sharedOrNot);
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
        ab                    = config.getBoolean("colldownMsgUseActionBar");
        isDebug               = config.getBoolean("debug");
        newPlayersTeleported  = config.getBoolean("teleportNewPlayers");
        randomeWorlds         = config.getConfigurationSection("randomWorlds");
        useExperimentalChekar = config.getBoolean("useGlobalClaimCheck");
        useRandomeWorldz      = config.getBoolean("useRandomWorlds");
        enableUselessGUI      = config.getBoolean("enableGUI");
        useOtherChekar        = config.getBoolean("useAlternativeGlobalClaimCheck");
        bluredLines           = new String[] {
                TooWildForEnums.translate(config.getString("signTexts.line1")),
                TooWildForEnums.translate(config.getString("signTexts.line2")),
                TooWildForEnums.translate(config.getString("signTexts.line3")),
                config.getString("signTexts.createWith"),
                TooWildForEnums.translate(config.getString("signTexts.costFree")),
                TooWildForEnums.translate(config.getString("signTexts.costMoney"))
        };
    }

    public void wildDependencies() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getServer().getPluginManager().getPlugin("Vault") != null) {
                    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
                            .getRegistration(Economy.class);
                    if (rsp != null)
                        econ = rsp.getProvider();
                }
            }
        }.runTask(instace);
        // @formatter:off
        try { dataaaastorege = ((GriefPrevention) (getServer().getPluginManager()
                .getPlugin("GriefPrevention"))).dataStore;
        } catch (Throwable rock) { dataaaastorege = null; }
        // @formatter:on
        wb = getServer().getPluginManager().isPluginEnabled("WorldBorder");
    }

    public static void debug(Object o) {
        if (isDebug) System.out.println("[WildTP] " + o);
    }

    public void wildMetrics() {
        // @formatter:off
        try { new Metricsa(this).start(); } catch (IOException e) {}
        // @formatter:on
        try {
            Metrics metrics = new Metrics(this);

            metrics.addCustomChart(new Metrics.SimplePie("bukkit_impl", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getServer().getVersion().split("-")[1];
                }
            }));

            for (final String key : getConfig().getKeys(false)) {
                if (getConfig().isBoolean(key) && !getConfig().isInt(key) && !getConfig().isString(key))
                    metrics.addCustomChart(new Metrics.SimplePie(key.toLowerCase(), new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            return getConfig().getString(key);
                        }
                    }));
            }
        }
        catch (Throwable rock) {}
    }
}
