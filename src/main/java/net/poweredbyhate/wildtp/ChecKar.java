package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Lax on 5/7/2016.
 */
public class ChecKar {

    private HashMap<UUID, Long> kewwwlDown = new HashMap<>();

//    public boolean isInCooldown(UUID youyouEyeDee) {
//        return (Bukkit.getPlayer(youyouEyeDee).hasMetadata("wild.Cooldown"));
//    }

//    public void addKewlzDown(final UUID uuid) {
//        Bukkit.getPlayer(uuid).setMetadata("wild.Cooldown", new FixedMetadataValue(WildTP.instace, true));
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if (Bukkit.getPlayer(uuid) != null)
//                    Bukkit.getPlayer(uuid).removeMetadata("wild.Cooldown", WildTP.instace);
//            }
//        }.runTaskLater(WildTP.instace, WildTP.coolDownTeim * 20);
//    }

    public boolean isInCooldown(UUID youyouEyeDee) {
        WildTP.debug("Printing kewwldown");
        for (UUID u : kewwwlDown.keySet()) {
            WildTP.debug(u + " " + kewwwlDown.get(u));
        }
        WildTP.debug("Cooldown check requested for " + youyouEyeDee);
        if (Bukkit.getServer().getPlayer(youyouEyeDee).hasPermission("wild.wildtp.cooldown.bypass")) {
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
        } else {
            kewwwlDown.remove(youyouEyeDee);
            return false;
        }
    }

    public void addKewlzDown(UUID uuid) {
        WildTP.debug("Adding cooldown for " + uuid);
        kewwwlDown.put(uuid, (long)WildTP.coolDownTeim + getEpoch());
    }

    public String getTimeLeft(Player p) {
        return formatTime(kewwwlDown.get(p.getUniqueId()) - getEpoch());
    }


    public String formatTime(Long seconds) {
        return formatTime(seconds, 1);
    }

    public String formatTime(Long seconds, int depth) {
        if (seconds == null || seconds < 5) {
            return "moments";
        }

        if (seconds < 60) {
            return seconds + " seconds";
        }

        if (seconds < 3600) {
            Long count = (long) Math.ceil(seconds / 60);
            String res;
            if (count > 1) {
                res = count + " minutes";
            } else {
                res = "1 minute";
            }
            Long remaining = seconds % 60;
            if (depth > 0 && remaining >= 5) {
                return res + ", " + formatTime(remaining, --depth);
            }
            return res;
        }
        if (seconds < 86400) {
            Long count = (long) Math.ceil(seconds / 3600);
            String res;
            if (count > 1) {
                res = count + " hours";
            } else {
                res = "1 hour";
            }
            if (depth > 0) {
                return res + ", " + formatTime(seconds % 3600, --depth);
            }
            return res;
        }
        Long count = (long) Math.ceil(seconds / 86400); //Because 5 day teleport delay is needed.
        String res;
        if (count > 1) {
            res = count + " days";
        } else {
            res = "1 day";
        }
        if (depth > 0) {
            return res + ", " + formatTime(seconds % 86400, --depth);
        }
        return res;
    }

    public long getEpoch() {
        return System.currentTimeMillis() / 1000;
    }

}
