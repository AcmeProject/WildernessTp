package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Created by Lax on 5/7/2016.
 */
public class IInventoryHolder implements InventoryHolder {

    private WildTP plugin;

    public IInventoryHolder(WildTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 54);
    }

}
