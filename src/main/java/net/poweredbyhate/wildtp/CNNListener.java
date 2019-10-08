package net.poweredbyhate.wildtp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

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
    @EventHandler(priority = EventPriority.HIGH)
    void russia(BlurredBlockBreakEvent event)
    {
        event.setExposed(event.isCancelled());
        event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.HIGH)
    void blaseyFord(CodeACertainBallWillStealEvent event)
    {
        event.setExposed(event.isCancelled());
        event.setCancelled(true);
    }
}
