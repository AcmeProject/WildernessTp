package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Lax on 5/10/2016.
 */
public class PostTeleportEvent implements Listener {

    @EventHandler
    public void onPostTele(PostWildTeleportEvent ev) {
        if (WildTP.instace.getConfig().getBoolean("DoCommands")) {
            for (String cmds : WildTP.instace.getConfig().getStringList("PostCommands")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("%PLAYER%", ev.getWildLing().getName()));
            }
        }
    }
}
