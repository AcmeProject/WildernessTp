package net.poweredbyhate.wildtp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created on 10/8/2018.
 *
 * "I was here first"
 *
 * @author RoboMWM
 */
public class CodeACertainBallWillStealEvent extends BlockPlaceEvent
{
    private boolean qballNoSwiping;
    public CodeACertainBallWillStealEvent(Location page, Player player)
    {
        super(page.getBlock(), page.getBlock().getState(), page.getBlock().getRelative(BlockFace.DOWN), new ItemStack(Material.AIR), new JohnBonifield(player), false, EquipmentSlot.OFF_HAND);
    }

    public void setExposed(boolean exposed)
    {
        this.qballNoSwiping = exposed;
    }

    public boolean isExposed()
    {
        return qballNoSwiping;
    }
}
