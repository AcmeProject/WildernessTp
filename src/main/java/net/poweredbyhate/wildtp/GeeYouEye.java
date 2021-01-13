package net.poweredbyhate.wildtp;

import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by Lax on 5/7/2016.
 */
public class GeeYouEye implements Listener {
    void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(new IInventoryHolder(), 9, "&9Unnecessary Feature");
        inv.setItem(4, createItem(Material.SUNFLOWER, 1, "§aWild Teleport", "Teleport to the wild!"));
        p.openInventory(inv);
    }

    @EventHandler
    private void onClick(InventoryClickEvent ev) {
        if (ev == null || ev.getInventory() == null || ev.getView() == null) //https://www.spigotmc.org/threads/wilderness-tp.145440/page-12#post-2394343
            return;

        if (ev.getInventory().getHolder() instanceof IInventoryHolder)
        {
            ev.setCancelled(true);

            if (ev.getView().getTitle().contains("Unnecessary Feature")
                    && ev.getInventory().getItem(ev.getRawSlot()).getItemMeta().getDisplayName().toLowerCase()
                            .contains("wild teleport")) {
                ev.getWhoClicked().closeInventory();
                new TeleportGoneWild(Trigger.GUI, (Player) ev.getWhoClicked()).WildTeleport();
            }
        }
    }

    private ItemStack createItem(Material material, int amount, String displayname, String lore) {
        ItemStack i  = new ItemStack(material, amount);
        ItemMeta  im = i.getItemMeta();
        im.setDisplayName(displayname);

        ArrayList<String> loreList = new ArrayList<String>();
        String[]          lores    = lore.split("/");
        loreList.addAll(Arrays.asList(lores));

        im.setLore(loreList);
        i.setItemMeta(im);
        return i;
    }

    /**
     * Created by Lax on 5/7/2016.
     * .
     * Converted to internal class by arboriginal on 11/10/2019,
     * as only used here and only one method, one instruction
     */
    private class IInventoryHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() {
            return Bukkit.createInventory(null, 54);
        }
    }
}
