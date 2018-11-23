package net.poweredbyhate.wildtp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created on 11/23/2018.
 *
 * @author RoboMWM
 */
public class DumWorldEdit
{
    public String createPortal(Player p, String name) {
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region sel = null;
        try
        {
            sel = we.getSession(p).getSelection(BukkitAdapter.adapt(p).getWorld());
        }
        catch (Throwable ball){}
        if (name == null) {
            p.sendMessage("Invalid Name");
            return null;
        }
        if (sel == null) {
            p.sendMessage("Invalid WorldEdit Selection");
            return null;
        }
        return (stringConvert(p, sel.getMaximumPoint())+"~"+stringConvert(p, sel.getMinimumPoint()));
    }

    public String stringConvert(Entity e, BlockVector3 loc) {
        return (e.getWorld().getName()+"."+loc.getBlockX()+"."+loc.getBlockY()+"."+loc.getBlockZ());
    }
}
