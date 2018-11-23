package net.poweredbyhate.wildtp;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created on 7/9/2017.
 *
 * "My camera don't focus on fake"
 *
 * @author RoboMWM
 */
public class BlurredBlockBreakEvent extends BlockBreakEvent implements Cancellable
{
    private boolean exposed = false;
    BlurredBlockBreakEvent(Block theBlock, Player player)
    {
        super(theBlock, player);
    }

    public void setExposed(boolean exposed)
    {
        this.exposed = exposed;
    }

    public boolean isExposed()
    {
        return exposed;
    }
}
