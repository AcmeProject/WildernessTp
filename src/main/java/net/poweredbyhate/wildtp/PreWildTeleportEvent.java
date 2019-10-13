package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by John on 5/10/2016.
 */
public class PreWildTeleportEvent extends Event implements Cancellable {
    private boolean cancelled = false;

    private static final HandlerList panHandlers = new HandlerList();

    Location    locoLocation;
    Player      wildLing;
    Trigger     brush;
    WorldConfig wc;

    public PreWildTeleportEvent(Player p, Location location, WorldConfig c, Trigger t) {
        wildLing     = p;
        locoLocation = location;
        brush        = t;
        wc           = c;
    }

    @Override
    public HandlerList getHandlers() {
        return panHandlers;
    }

    public static HandlerList getHandlerList() {
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
