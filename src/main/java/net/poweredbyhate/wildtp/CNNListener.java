package net.poweredbyhate.wildtp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 7/9/2017.
 *
 * The trustworthy and truthful CNN
 * https://twitter.com/theblaze/status/882758680327966721
 *
 * @author RoboMWM
 */
public class CNNListener implements Listener
{
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    void russia(BlurredBlockBreakEvent event)
    {
        event.setExposed(event.isCancelled());
        event.setCancelled(true);
    }
}
