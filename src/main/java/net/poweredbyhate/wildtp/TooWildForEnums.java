package net.poweredbyhate.wildtp;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by John on 5/12/2016.
 */
public class TooWildForEnums {

    public static String NO_PERMS;
    public static String NO_SIGN_PERMS;
    public static String NO_BREAK;
    public static String NO_LOCATION;
    public static String COOLDOWN;

    File langFile;
    FileConfiguration langConf;

    public void loadConfig() {
        langFile = new File(WildTP.instace.getDataFolder(), "Messages.yml");
        if (!langFile.exists()) {
            try {
                langFile.createNewFile();
                langConf.load(langFile);
                langConf.set("NO_PERMS", "&4You do not have permission!");
                langConf.set("NO_SIGN_PERMS", "&4You do not have permission to make a wild sign");
                langConf.set("NO_BREAK", "&4Hey! You can not break WildTp sign!");
                langConf.set("NO_LOCATION", "&4No suitable locations found.")
		langCong.set("COOLDOWN", "You must way %TIME% seconds until you can use the command/sign again ")
		langConf.save(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
}
