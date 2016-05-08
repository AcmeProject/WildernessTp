package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Lax on 5/7/2016.
 */
public class ChecKar {

    private HashMap<UUID, Long> kewwwlDown = new HashMap<>();


    public boolean isInCooldown(UUID youyouEyeDee) {
        if (!kewwwlDown.containsKey(youyouEyeDee) || Bukkit.getServer().getPlayer(youyouEyeDee).hasPermission("wild.wildtp.cooldown.bypass")) {
            return false;
        }
        if (kewwwlDown.get(youyouEyeDee) > System.currentTimeMillis()) {
            return WildTP.instace.ifurwildandunoitclapurhands;
        } else {
            kewwwlDown.remove(youyouEyeDee);
            return false;
        }
    }

    public void addKewlzDown(UUID uuid) {
        kewwwlDown.put(uuid, (long)(WildTP.coolDownTeim * 1000) + System.currentTimeMillis());
    }


}
