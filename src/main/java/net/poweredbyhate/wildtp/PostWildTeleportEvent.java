package net.poweredbyhate.wildtp;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Lax on 5/9/2016.
 */
public class PostWildTeleportEvent extends Event {

    public static final HandlerList panHandlers = new HandlerList();
    private Player wildLing;

    public PostWildTeleportEvent(Player wildLing) {
        this.wildLing = wildLing;
    }

    @Override
    public HandlerList getHandlers() {
        return panHandlers;
    }

    public Player getWildLing() {
        return this.wildLing;
    }
}
