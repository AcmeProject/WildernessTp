package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Lax on 5/10/2016.
 */
public class PostTeleportEvent implements Listener {

    private WildTP girlsGone;

    public PostTeleportEvent(WildTP wild) {
        girlsGone = wild;
    }

    @EventHandler
    public void onPostTele(PostWildTeleportEvent ev) {
        Location lok = ev.getWildLing().getLocation();
        World plaza = lok.getWorld();
        WorldConfig how = girlsGone.thugz.get(plaza.getName());

        try {
            plaza.playSound(lok, Sound.valueOf(WildTP.instace.getConfig().getString("Sound")) , 1, 1);
        }
        catch (Throwable boop){}

        if (how.doCommandz) {
            String p = ev.getWildLing().getName(), x = "" + lok.getBlockX(), z = "" + lok.getBlockZ();
            for (String c : how.commando) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%PLAYER%", p).replace("%X%", x).replace("%Z%", z));
        }
    }
}
