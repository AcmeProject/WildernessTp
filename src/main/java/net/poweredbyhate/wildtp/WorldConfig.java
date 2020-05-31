package net.poweredbyhate.wildtp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by arboriginal on 7/10/2019.
 */
class WorldConfig {
    private int minXY, maxXY;

    boolean bar_enabled, doCommandz, dr0p1n, freeze, freezePortal, moveCancel, moveCancelPortal,
            bypass_cooldown_cmd,
            bypass_cooldown_gui,
            bypass_cooldown_guy,
            bypass_cooldown_sg1,
            bypass_cooldown_pan,
            bypass_cost_cmd,
            bypass_cost_gui,
            bypass_cost_guy,
            bypass_cost_sg1,
            bypass_cost_pan,
            bypass_delay_cmd,
            bypass_delay_gui,
            bypass_delay_guy,
            bypass_delay_sg1,
            bypass_delay_pan, harlemShake, whoYaGonaCall; // THOSE BASTARDS!

    double fusRoDah;
    
    int coolDownTeim, cost, dr0pFr0m, retries, confirmDelay, maxX, maxZ, minX, minZ, wamuppah,
            portal_max_x, portal_max_y, portal_max_z;

    BarColor        bar_color_searching, bar_color_waiting;
    BarStyle        bar_style_searching, bar_style_waiting;
    ChecKar         checKar;
    HashSet<String> bioman, commando, nonoBlocks, portal_gms;
    World           world;

    HashMap<Trigger, PotionEffect[]> effects;

    WorldConfig(World madWorld, ConfigurationSection c, @Nullable ChecKar shared) {
        world = madWorld;
        ConfigurationSection s = c.getConfigurationSection("overrides." + madWorld.getName());
        bar_enabled         = b("enableBar",                s, c);
        bioman              = h("BlockedBiomes",            s, c);
        bypass_cooldown_cmd = b("Bypass.cooldown.COMMAND",  s, c);
        bypass_cooldown_gui = b("Bypass.cooldown.GUI",      s, c);
        bypass_cooldown_guy = b("Bypass.cooldown.OTHERGUY", s, c);
        bypass_cooldown_pan = b("Bypass.cooldown.SIGN",     s, c);
        bypass_cooldown_sg1 = b("Bypass.cooldown.PORTAL",   s, c);
        bypass_cost_cmd     = b("Bypass.cost.COMMAND",      s, c);
        bypass_cost_gui     = b("Bypass.cost.GUI",          s, c);
        bypass_cost_guy     = b("Bypass.cost.OTHERGUY",     s, c);
        bypass_cost_pan     = b("Bypass.cost.SIGN",         s, c);
        bypass_cost_sg1     = b("Bypass.cost.PORTAL",       s, c);
        bypass_delay_cmd    = b("Bypass.delay.COMMAND",     s, c);
        bypass_delay_gui    = b("Bypass.delay.GUI",         s, c);
        bypass_delay_guy    = b("Bypass.delay.OTHERGUY",    s, c);
        bypass_delay_pan    = b("Bypass.delay.SIGN",        s, c);
        bypass_delay_sg1    = b("Bypass.delay.PORTAL",      s, c);
        commando            = h("PostCommands",             s, c);
        confirmDelay        = i("paidTPconfirmation",       s, c);
        coolDownTeim        = i("Cooldown",                 s, c);
        cost                = i("Cost",                     s, c);
        doCommandz          = b("DoCommands",               s, c);
        dr0p1n              = b("dropPlayerFromAbove",      s, c);
        dr0pFr0m            = i("dropPlayerFromHeight",     s, c);
        freeze              = b("freezeWhileRTP",           s, c);
        freezePortal        = b("Portals.freezeWhileRTP",   s, c);
        fusRoDah            = d("Portals.push",             s, c);
        harlemShake         = b("movingBorder",             s, c);
        moveCancel          = b("moveCancelRTP",            s, c);
        moveCancelPortal    = b("Portals.moveCancelRTP",    s, c);
        nonoBlocks          = h("BlockedBlocks",            s, c);
        portal_gms          = h("Portals.Gamemodes",        s, c);
        portal_max_x        = i("Portals.xMax",             s, c);
        portal_max_y        = i("Portals.yMax",             s, c);
        portal_max_z        = i("Portals.zMax",             s, c);
        retries             = i("Retries",                  s, c);
        wamuppah            = i("Wait",                     s, c);
        whoYaGonaCall       = b("callFiremenInNether",      s, c);
        checKar = (shared == null) ? new ChecKar(coolDownTeim) : shared;
        effects = hurryPeter(s, c, wamuppah);
        if (bar_enabled) paulDance(s, c);
        maxXY = i("MaxXY", s, c);
        minXY = i("MinXY", s, c);
        weNeedToBuildaWallTrumpSaidItAndObviouslyEverybodyLikeHim();
    }

    void weNeedToBuildaWallTrumpSaidItAndObviouslyEverybodyLikeHim() {
        if (WildTP.wb) {
            BorderData b = Config.Border(world.getName());
            // We don't need no education...
            if (b != null) {
                Boolean s = b.getShape();
                int     x = b.getRadiusX(), z = b.getRadiusZ();
                // (guitar riff)
                if ((s == null && Config.ShapeRound()) || (s != null && s)) {
                    x = (int) (Math.sqrt(2) * x) / 2;
                    z = (int) (Math.sqrt(2) * z) / 2;
                }
                // We don't need no thought control...
                sendBrick(minXY, maxXY, (int) b.getX() - x, (int) b.getX() + x, (int) b.getZ() - z, (int) b.getZ() + z);
                return;
            }
        }
        WorldBorder b = world.getWorldBorder();
        if (b == null) return;
        int r = (int) b.getSize() / 2, x = b.getCenter().getBlockX(), z = b.getCenter().getBlockZ();
        sendBrick(minXY, maxXY, x - r, x + r, z - r, z + r);
    }

    private boolean b(String k, ConfigurationSection v, ConfigurationSection d) {
        if (v != null && v.contains(k))
            return v.getBoolean(k);
        return d.getBoolean(k);
    }

    private double d(String k, ConfigurationSection v, ConfigurationSection d) {
        return ((v != null && v.contains(k)) ? v.getDouble(k) : d.getDouble(k));
    }

    private HashSet<String> h(String k, ConfigurationSection v, ConfigurationSection d) {
        return new HashSet<String>((v != null && v.contains(k)) ? v.getStringList(k) : d.getStringList(k));
    }

    private int i(String k, ConfigurationSection v, ConfigurationSection d) {
        return (v != null && v.contains(k)) ? v.getInt(k) : d.getInt(k);
    }

    private HashMap<Trigger, PotionEffect[]> hurryPeter(ConfigurationSection v, ConfigurationSection d, int w) {
        HashMap<Trigger, PotionEffect[]> map = new HashMap<Trigger, PotionEffect[]>();
        for (Trigger when : Trigger.values()) {
            HashSet<PotionEffect> effects    = new HashSet<PotionEffect>();
            String                emmaString = "Effects." + when.toString();
            List<String>          emmaGivDis = (v != null && v.contains(emmaString))
                    ? v.getStringList(emmaString) : d.getStringList(emmaString);
            emmaGivDis.forEach(patronus -> {
                String[]         spell = patronus.split(":");
                PotionEffectType agrud = PotionEffectType.getByName(spell[0]);
                if (agrud == null) return;
                int voldo = 0; if (spell.length == 2)
                try { voldo = Integer.parseInt(spell[1]); } catch (Exception e) {}
                effects.add(new PotionEffect(agrud, w + 1200, voldo, false, false, false));
            }); map.put(when, (PotionEffect[]) effects.toArray(new PotionEffect[] {}));
        }
        return map;
    }

    private String s(String k, ConfigurationSection v, ConfigurationSection d) {
        return (v != null && v.contains(k)) ? v.getString(k) : d.getString(k);
    }

    private void paulDance(ConfigurationSection v, ConfigurationSection d) {
        try {
            bar_color_searching = BarColor.valueOf(s("barColor.searching", v, d));
            bar_color_waiting   = BarColor.valueOf(s("barColor.waiting",   v, d));
            bar_style_searching = BarStyle.valueOf(s("barStyle.searching", v, d));
            bar_style_waiting   = BarStyle.valueOf(s("barStyle.waiting",   v, d));
        }
        catch (Exception e) {
            WildTP.instace.getLogger().warning("Your bossbar settings are wrong... bar disabled.");
            bar_enabled = false;
        }
    }

    private void sendBrick(int minXY, int maxXY, int x, int X, int z, int Z) {
        minX = Math.max(x, minXY);
        maxX = Math.min(X, maxXY);
        minZ = Math.max(z, minXY);
        maxZ = Math.min(Z, maxXY);

        WildTP.debug(world.getName() + " borders: " + minX + ";" + maxX + ":" + minZ + ";" + maxZ);
    }
}
