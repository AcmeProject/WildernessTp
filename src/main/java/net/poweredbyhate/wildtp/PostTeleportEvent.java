package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Lax on 5/10/2016.
 */
public class PostTeleportEvent implements Listener {

    @EventHandler
    public void onPostTele(PostWildTeleportEvent ev) {
        try {
            ev.getWildLing().getWorld().playSound(ev.getWildLing().getLocation(), Sound.valueOf(WildTP.instace.getConfig().getString("Sound")) , 1, 1);
        }
        catch (Throwable boop){}
        if (WildTP.doCommandz) {
            for (String cmds : WildTP.instace.getConfig().getStringList("PostCommands")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("%PLAYER%", ev.getWildLing().getName()));
            }
        }
    }
}
