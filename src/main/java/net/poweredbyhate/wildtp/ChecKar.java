package net.poweredbyhate.wildtp;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by Lax on 5/7/2016.
 */
class ChecKar {
    private long freezr;

    private HashMap<UUID, Long> kewwwlDown = new HashMap<UUID, Long>();

    ChecKar(int coolDownTeim) {
        freezr = coolDownTeim;
    }

    static long getEpoch() {
        return System.currentTimeMillis() / 1000;
    }

    static boolean bypass(String what, Player p, WorldConfig c, Trigger t) {
        switch (what) {
            case "delay":
                switch (t) { // @formatter:off
                    case JOIN:    return true;
                    case COMMAND: return c.bypass_delay_cmd || p.hasPermission("wild.wildtp.delay.bypass");
                    case GUI:     return c.bypass_delay_gui || p.hasPermission("wild.wildtp.delay.bypass");
                    case OTHERGUY:return c.bypass_delay_guy || p.hasPermission("wild.wildtp.delay.bypass");
                    case PORTAL:  return c.bypass_delay_sg1 || p.hasPermission("wild.wildtp.delay.bypass");
                    case SIGN:    return c.bypass_delay_pan || p.hasPermission("wild.wildtp.delay.bypass");
                } // @formatter:on

            case "cooldown":
                switch (t) { // @formatter:off
                    case JOIN:    return true;
                    case COMMAND: return c.bypass_cooldown_cmd || p.hasPermission("wild.wildtp.cooldown.bypass");
                    case GUI:     return c.bypass_cooldown_gui || p.hasPermission("wild.wildtp.cooldown.bypass");
                    case OTHERGUY:return c.bypass_cooldown_guy || p.hasPermission("wild.wildtp.cooldown.bypass");
                    case PORTAL:  return c.bypass_cooldown_sg1 || p.hasPermission("wild.wildtp.cooldown.bypass");
                    case SIGN:    return c.bypass_cooldown_pan || p.hasPermission("wild.wildtp.cooldown.bypass");
                } // @formatter:on

            case "cost":
                switch (t) { // @formatter:off
                    case JOIN:    return true;
                    case COMMAND: return c.bypass_cost_cmd || p.hasPermission("wild.wildtp.world.*.free")
                                                           || p.hasPermission("wild.wildtp.world." + c.world.getName() + ".free");
                    case GUI:     return c.bypass_cost_gui || p.hasPermission("wild.wildtp.world.*.free")
                                                           || p.hasPermission("wild.wildtp.world." + c.world.getName() + ".free");
                    case OTHERGUY:return c.bypass_cost_guy || p.hasPermission("wild.wildtp.world.*.free")
                                                           || p.hasPermission("wild.wildtp.world." + c.world.getName() + ".free");
                    case PORTAL:  return c.bypass_cost_sg1 || p.hasPermission("wild.wildtp.world.*.free")
                                                           || p.hasPermission("wild.wildtp.world." + c.world.getName() + ".free");
                    case SIGN:    return c.bypass_cost_pan || p.hasPermission("wild.wildtp.world.*.free")
                                                           || p.hasPermission("wild.wildtp.world." + c.world.getName() + ".free");
                } // @formatter:on
        }
        // @note: hasPermission() is voluntary tested after the booleans because booleans are faster
        return false;
    }

    boolean isInCooldown(UUID youyouEyeDee, WorldConfig yabadabadoo, Trigger tirelipimpon) {
        WildTP.debug("Cooldown check requested for " + youyouEyeDee);

        if (bypass("cooldown", Bukkit.getPlayer(youyouEyeDee), yabadabadoo, tirelipimpon)) {
            WildTP.debug("Player has bypass perms");
            return false;
        }

        if (!kewwwlDown.containsKey(youyouEyeDee)) {
            WildTP.debug("Player not in cooldown");
            return false;
        }

        if (kewwwlDown.get(youyouEyeDee) > getEpoch()) {
            WildTP.debug("Player in cooldown");
            return true;
        }

        kewwwlDown.remove(youyouEyeDee);
        return false;
    }

    void addKewlzDown(UUID uuid) {
        WildTP.debug("Adding cooldown for " + uuid);
        kewwwlDown.put(uuid, freezr + getEpoch());
    }

    String getTimeLeft(Player p) {
        return formatTime(kewwwlDown.get(p.getUniqueId()) - getEpoch());
    }

    String formatTime(Long seconds) {
        return formatTime(seconds, 1);
    }

    String formatTime(Long seconds, int depth) {
        if (seconds == null || seconds < 5) return TooWildForEnums.TIME_SOON;
        if (seconds < 60) return seconds + " " + ((seconds > 1) ? TooWildForEnums.TIME_Ss : TooWildForEnums.TIME_S);

        if (seconds < 3600) {
            Long   count     = (long) Math.ceil(seconds / 60);
            String res       = (count > 1) ? count + " " + TooWildForEnums.TIME_Ms : "1 " + TooWildForEnums.TIME_M;
            Long   remaining = seconds % 60;

            return (depth > 0 && remaining >= 5) ? res + ", " + formatTime(remaining, --depth) : res;
        }

        if (seconds < 86400) {
            Long   count = (long) Math.ceil(seconds / 3600);
            String res   = (count > 1) ? count + " " + TooWildForEnums.TIME_Hs : "1 " + TooWildForEnums.TIME_H;

            return (depth > 0) ? res + ", " + formatTime(seconds % 3600, --depth) : res;
        }
        //Because 5 day teleport delay is needed.
        Long   count = (long) Math.ceil(seconds / 86400);
        String res   = (count > 1) ? count + " " + TooWildForEnums.TIME_Ds : "1 " + TooWildForEnums.TIME_D;

        return (depth > 0) ? res + ", " + formatTime(seconds % 86400, --depth) : res;
    }
}
