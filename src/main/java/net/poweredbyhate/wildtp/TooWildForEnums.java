package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class TooWildForEnums {
    private FileConfiguration langConf;

    // @formatter:off
    static String
        ATTEMPT,
        BREAK_SIGN,
        CARD_EAST,
        CARD_NORTH,
        CARD_SOUTH,
        CARD_WEST,
        CONFIRMKO,
        CONFIRMOK,
        CONFIRMON,
        CONFIRMSG,
        COOLDOWN,
        DIDNT_WAIT,
        GENERROR,
        NO_BIOME,
        NO_BREAK,
        NO_LOCATION,
        NO_MONEY,
        NO_PERMS,
        NO_SIGN_PERMS,
        PENDING_RTP,
        PORTAL404,
        PORTALADD,
        PORTALBEGIN,
        PORTALBIG,
        PORTALCANCEL,
        PORTALCONTAIN,
        PORTALDEL,
        PORTALEXIST,
        PORTALGOHAN,
        PORTALHERE,
        PORTALHOVER,
        PORTALINKED,
        PORTALINKOK,
        PORTALSTOP,
        PROCEED,
        RELOADED,
        TIME_D,
        TIME_Ds,
        TIME_H,
        TIME_Hs,
        TIME_M,
        TIME_Ms,
        TIME_S,
        TIME_SOON,
        TIME_Ss,
        WAIT_MSG,
        YES_SIGN;
    // @formatter:on

    void loadConfig() {
        File langFile = new File(WildTP.instace.getDataFolder(), "Messages.yml");
        langConf = YamlConfiguration.loadConfiguration(langFile);

        try {
            papersPlease();

            dV().forEach((k, v) -> {
                if (!langConf.contains(k)) langConf.set(k, v);
            });

            langConf.save(langFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setStrings();
    }

    static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private HashMap<String, String> dV() {
        HashMap<String, String> dv = new HashMap<String, String>();
        // @formatter:off
        dv.put("ATTEMPT",       "&6Search a safe location... &7(%ATTEMPT%/%MAX%)");
        dv.put("BREAK_SIGN",    "&aYou have broken a WildTP sign");
        dv.put("COOLDOWN",      "&4You must wait %TIME% until you can use the command/sign again");
        dv.put("DIDNT_WAIT",    "&cYou need to hold still while you wait!");
        dv.put("GENERROR",      "&c&lError occurred, check console logs");
        dv.put("NO_BIOME",      "&4You may not put signs in %BIOME%");
        dv.put("NO_BREAK",      "&4Hey! You can not break WildTp sign!");
        dv.put("NO_LOCATION",   "&4No suitable locations found.");
        dv.put("NO_MONEY",      "&cYou are too poor to deserve a teleport at this time.");
        dv.put("NO_PERMS",      "&4You do not have permission!");
        dv.put("NO_SIGN_PERMS", "&4You do not have permission to make a wild sign");
        dv.put("PENDING_RTP",   "&cA WildTP request is already pending...");
        dv.put("PORTAL404",     "&cThis portal doesn't exists...");
        dv.put("PORTALADD",     "&aPortal &l%NAME% &acreated!");
        dv.put("PORTALBEGIN",   "&bClick two corners to create your portal.");
        dv.put("PORTALBIG",     "&cPortal too big! max: x={%xMax%}, x={%yMax%}, x={%zMax%}");
        dv.put("PORTALCANCEL",  "&7(click the same block twice to cancel)");
        dv.put("PORTALCONTAIN", "&cThis portal cannot contains another one! Cancelled.");
        dv.put("PORTALDEL",     "&aPortal &l%NAME% &adeleted!");
        dv.put("PORTALEXIST",   "&cThis portal name is already in use!");
        dv.put("PORTALGOHAN",   "&bNow click other corner.");
        dv.put("PORTALHERE",    "&cTry again: This block is part of another portal!");
        dv.put("PORTALHOVER",   "&bClick to teleport to it.");
        dv.put("PORTALINKED",    "&cPortal %PORTAL% is already linked to another one.");
        dv.put("PORTALINKOK",    "&aPortals are now linked.");
        dv.put("PORTALSTOP",    "&6Portal creation cancelled.");
        dv.put("PROCEED",       "&aProcessing...");
        dv.put("RELOADED",      "&aPlugin config has successfuly been reloaded.");
        dv.put("WAIT_MSG",      "&6Teleporting in {wait} seconds");
        dv.put("YES_SIGN",      "&aSuccessfully made a new WildTP sign");
        // are your sure? really sure?
        dv.put("CONFIRMKO",     "&cNo pending confirmation...");
        dv.put("CONFIRMOK",     "&a&l[ CONFIRM ]");
        dv.put("CONFIRMON",     "&bClick to confirm!");
        dv.put("CONFIRMSG",
            "&6---------------------------------------"
        + "\n&6This teleportation cost %COST% $."
        + "\n         %CONFIRM%"
        + "\n&7(You have %TIME% seconds to confirm.)"
        + "\n&6---------------------------------------");
        // Time runs... follow the white rabbit!
        dv.put("TIME.DAY.PLURAL",      "days");
        dv.put("TIME.DAY.SINGULAR",    "day");
        dv.put("TIME.HOUR.PLURAL",     "hours");
        dv.put("TIME.HOUR.SINGULAR",   "hour");
        dv.put("TIME.MINUTE.PLURAL",   "minutes");
        dv.put("TIME.MINUTE.SINGULAR", "minute");
        dv.put("TIME.SECOND.PLURAL",   "seconds");
        dv.put("TIME.SECOND.SINGULAR", "second");
        dv.put("TIME.SOON",            "moments");
        // Yep... follow him, he's got pills.
        dv.put("DIRECTION.EAST",  "East");
        dv.put("DIRECTION.NORTH", "North");
        dv.put("DIRECTION.SOUTH", "South");
        dv.put("DIRECTION.WEST",  "West");
        // @formatter:on
        return dv;
    }

    private void setStrings() {
        TooWildForEnums me = this;

        dV().keySet().forEach(k -> {
            String f;

            if (k.startsWith("DIRECTION.")) f = k.replace("DIRECTION.", "CARD_");
            else if (k.startsWith("TIME.")) {
                String[] p = k.split("\\.");
                f = "TIME_" + (p.length == 2 ? p[1] : p[1].substring(0, 1) + (p[2].equals("PLURAL") ? "s" : ""));
            }
            else f = k;

            try {
                me.getClass().getDeclaredField(f).set(me, translate(langConf.getString(k)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void papersPlease() {
        if (langConf.contains("CONFIRMSG") && langConf.getString("CONFIRMSG").split("%CONFIRM%").length != 2) {
            langConf.set("CONFIRMSG", dV().get("CONFIRMSG"));
            Bukkit.getLogger().warning("Messages.yml: invalid CONFIRMSG... Reset to default!");
        } // @see TeleportGoneWild.oralExam() => Could cause issues.

        if (langConf.contains("DIRECTION"))
            for (String c : langConf.getKeys(false)) {
                if (!langConf.contains("DIRECTION." + c)) continue;

                String o = langConf.getString("DIRECTION." + c), v = o.replaceAll("\\s", "");

                if (!o.equals(v)) {
                    langConf.set("DIRECTION." + c, v);
                    Bukkit.getLogger().warning("Messages.yml: DIRECTION." + c + " contains blanks... removed!");
                }
            } // Used in command, so cannot contains blank characters.
    }
}
