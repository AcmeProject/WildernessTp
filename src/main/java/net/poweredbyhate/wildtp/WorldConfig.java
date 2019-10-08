package net.poweredbyhate.wildtp;

import java.util.HashSet;
import javax.annotation.Nullable;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by arboriginal on 7/10/2019.
 */
class WorldConfig {
    boolean doCommandz, dr0p1n;
    ChecKar checKar;
    HashSet<String> bioman, commando, nonoBlocks;
    int coolDownTeim, cost, dr0pFr0m, maxXY, minXY, retries, confirmDelay, wamuppah;

    public WorldConfig(String eminame, ConfigurationSection c, @Nullable ChecKar shared) {
        ConfigurationSection s = c.getConfigurationSection("overrides." + eminame);

        bioman       = h("BlockedBiomes",        s, c);
        nonoBlocks   = h("BlockedBlocks",        s, c);
        commando     = h("PostCommands",         s, c);
        doCommandz   = b("DoCommands",           s, c);
        dr0p1n       = b("dropPlayerFromAbove",  s, c);
        coolDownTeim = i("Cooldown",             s, c);
        cost         = i("Cost",                 s, c);
        dr0pFr0m     = i("dropPlayerFromHeight", s, c);
        maxXY        = i("MaxXY",                s, c);
        minXY        = i("MinXY",                s, c);
        retries      = i("Retries",              s, c);
        wamuppah     = i("Wait",                 s, c);
        confirmDelay = i("paidTPconfirmation",   s, c);

        checKar = (shared == null)? new ChecKar(coolDownTeim): shared;
    }

    private boolean b(String k, ConfigurationSection v, ConfigurationSection d) {
        return (v != null && v.contains(k))? v.getBoolean(k): d.getBoolean(k);
    }

    private int i(String k, ConfigurationSection v, ConfigurationSection d) {
        return (v != null && v.contains(k))? v.getInt(k): d.getInt(k);
    }

    private HashSet<String> h(String k, ConfigurationSection v, ConfigurationSection d) {
        return new HashSet<String>((v != null && v.contains(k))? v.getStringList(k): d.getStringList(k));
    }
}
