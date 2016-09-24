package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Lax on 5/7/2016.
 */
public class ChecKar {

    private HashMap<UUID, Long> kewwwlDown = new HashMap<>();

    public boolean isInCooldown(UUID youyouEyeDee) {
        return (Bukkit.getPlayer(youyouEyeDee).hasMetadata("wild.Cooldown"));
    }

    public void addKewlzDown(final UUID uuid) {
        Bukkit.getPlayer(uuid).setMetadata("wild.Cooldown", new FixedMetadataValue(WildTP.instace, true));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getPlayer(uuid) != null)
                    Bukkit.getPlayer(uuid).removeMetadata("wild.Cooldown", WildTP.instace);
            }
        }.runTaskLater(WildTP.instace, WildTP.coolDownTeim * 20);
    }

//    public boolean isInCooldown(UUID youyouEyeDee) {
//        WildTP.debug("Printing kewwldown");
//        for (UUID u :kewwwlDown.keySet()) {
//            WildTP.debug(u + " " + kewwwlDown.get(u));
//        }
//        WildTP.debug("Cooldown check requested for " + youyouEyeDee);
//        if (Bukkit.getServer().getPlayer(youyouEyeDee).hasPermission("wild.wildtp.cooldown.bypass")) {
//            WildTP.debug("Player has bypass perms");
//            return false;
//        }
//        if (!kewwwlDown.containsKey(youyouEyeDee)) {
//            WildTP.debug("Player not in cooldown");
//            return false;
//        }
//        if (kewwwlDown.get(youyouEyeDee) > System.currentTimeMillis()) {
//            WildTP.debug("Player in cooldown");
//            return true;
//        } else {
//            kewwwlDown.remove(youyouEyeDee);
//            return false;
//        }
//    }
//
//    public void addKewlzDown(UUID uuid) {
//        WildTP.debug("Adding cooldown for " + uuid);
//        kewwwlDown.put(uuid, (long)WildTP.coolDownTeim * 1000 + System.currentTimeMillis());
//    }
//
//    public long getTimeLeft(Player p) {
//        return kewwwlDown.get(p.getUniqueId()) - System.currentTimeMillis();
//    }


}
