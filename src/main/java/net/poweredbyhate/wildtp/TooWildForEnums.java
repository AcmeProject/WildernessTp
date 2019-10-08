package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Lol, Gotcha qball! Stop stealin our code!
 * 
 * <arboriginal - 2019-10-8> I've removed gS() because only used here,
 * and it reloaded langFile into langConf each time it was called...
 * As it was called several times in setString(), just after
 * loadConfig() did this loading, this had no sense.
 * To be sure setStrings() will not be used without having loaded first,
 * I've changed its visibility to private.
 */
public class TooWildForEnums {

    private FileConfiguration langConf;

    public static String NO_PERMS;
    public static String NO_SIGN_PERMS;
    public static String NO_BREAK;
    public static String NO_LOCATION;
    public static String NO_BIOME;
    public static String NO_MONEY;
    public static String YES_SIGN;
    public static String BREAK_SIGN;
    public static String COOLDOWN;
    public static String RELOADED;
    public static String WAIT_MSG;
    public static String DIDNT_WAIT;
    public static String NO_WE;
    public static String CONFIRMSG, CONFIRMOK, CONFIRMON, CONFIRMKO;

    public void loadConfig() {
        File langFile = new File(WildTP.instace.getDataFolder(), "Messages.yml");
        langConf = new YamlConfiguration();
        try {
            if (langFile.exists()) langFile.createNewFile();
            langConf.load(langFile);
            papersPlease();
            dV().forEach((k, v) -> { if (!langConf.contains(k)) langConf.set(k, v); });
            langConf.save(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        setStrings();
    }

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private HashMap<String, String> dV() {
        HashMap<String, String> dv = new HashMap<String, String>();
        dv.put("NO_PERMS",      "&4You do not have permission!");
        dv.put("NO_SIGN_PERMS", "&4You do not have permission to make a wild sign");
        dv.put("NO_BREAK",      "&4Hey! You can not break WildTp sign!");
        dv.put("NO_LOCATION",   "&4No suitable locations found.");
        dv.put("NO_BIOME",      "&4You may not put signs in %BIOME%");
        dv.put("YES_SIGN",      "&aSuccessfully made a new WildTP sign");
        dv.put("BREAK_SIGN",    "&aYou have broken a WildTP sign");
        dv.put("COOLDOWN",      "&4You must wait %TIME% seconds until you can use the command/sign again");
        dv.put("RELOADED",      "&aPlugin config has successfuly been reloaded.");
        dv.put("WAIT_MSG",      "&6Teleporting in {wait} seconds");
        dv.put("DIDNT_WAIT",    "&cYou need to hold still while you wait!");
        dv.put("NO_WE",         "&cWorldEdit must be installed for this command.");
        dv.put("NO_MONEY",      "&cYou are too poor to deserve a teleport at this time.");
        dv.put("CONFIRMSG",
            "&6---------------------------------------"
        + "\n&6This teleportation cost %COST% $."
        + "\n         %CONFIRM%"
        + "\n&7(You have %TIME% seconds to confirm.)"
        + "\n&6---------------------------------------");
        dv.put("CONFIRMOK", "&a&l[ CONFIRM ]");
        dv.put("CONFIRMON", "&bClick to confirm!");
        dv.put("CONFIRMKO", "&cNo pending confirmation...");
        return dv;
    }

    private void setStrings() { // @see note above the class
        NO_PERMS      = langConf.getString("NO_PERMS");
        NO_SIGN_PERMS = langConf.getString("NO_SIGN_PERMS");
        NO_BREAK      = langConf.getString("NO_BREAK");
        NO_LOCATION   = langConf.getString("NO_LOCATION");
        NO_BIOME      = langConf.getString("NO_BIOME");
        YES_SIGN      = langConf.getString("YES_SIGN");
        BREAK_SIGN    = langConf.getString("BREAK_SIGN");
        COOLDOWN      = langConf.getString("COOLDOWN");
        RELOADED      = langConf.getString("RELOADED");
        WAIT_MSG      = langConf.getString("WAIT_MSG");
        DIDNT_WAIT    = langConf.getString("DIDNT_WAIT");
        NO_WE         = langConf.getString("NO_WE");
        NO_MONEY      = langConf.getString("NO_MONEY");
        CONFIRMSG     = langConf.getString("CONFIRMSG");
        CONFIRMOK     = langConf.getString("CONFIRMOK");
        CONFIRMON     = langConf.getString("CONFIRMON");
        CONFIRMKO     = langConf.getString("CONFIRMKO");
    }

    private void papersPlease() {
      if (!langConf.contains("CONFIRMSG")) return;
      if (langConf.getString("CONFIRMSG").split("%CONFIRM%").length != 2) {
          langConf.set("CONFIRMSG", dV().get("CONFIRMSG"));
          Bukkit.getLogger().warning("Messages.yml: invalid CONFIRMSG... Reset to default!");
      } // @see TeleportGoneWild.oralExam() => Could cause issues.
    }
}
