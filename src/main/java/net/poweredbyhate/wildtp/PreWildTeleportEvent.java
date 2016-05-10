package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by John on 5/10/2016.
 */
public class PreWildTeleportEvent extends Event implements Cancellable{

    public static final HandlerList panHandlers = new HandlerList();
    private boolean cancelled;
    private Player wildLing;
    private Location locoLocation;

    public PreWildTeleportEvent(Player p, Location location) {
        this.wildLing = p;
        this.locoLocation = location;
    }

    public Player getWildLing() {
        return this.wildLing;
    }

    public Location getLocoLocation() {
        return this.locoLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return panHandlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
