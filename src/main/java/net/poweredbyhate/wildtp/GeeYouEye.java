package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lax on 5/7/2016.
 */
public class GeeYouEye implements Listener {

    public void openMenu(Player p) {
        Inventory inv = Bukkit.createInventory(new IInventoryHolder(WildTP.instace), 9, ChatColor.AQUA + "Unnecessary Feature");
        inv.setItem(4, createItem(Material.DOUBLE_PLANT, 1, 0, "&aWild Teleport", "Teleport to the wild!"));
        p.openInventory(inv);
    }

    public ItemStack createItem(Material material, int amount, int shrt, String displayname, String lore) {
        ItemStack i = new ItemStack(material, amount, (short) shrt);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));

        ArrayList<String> loreList = new ArrayList<String>();
        String[] lores = lore.split("/");
        loreList.addAll(Arrays.asList(lores));

        im.setLore(loreList);
        i.setItemMeta(im);
        return i;
    }

    @EventHandler
    public void onClick(InventoryClickEvent ev) {
        try
        {
            if (ev.getInventory().getHolder() instanceof  IInventoryHolder) {
                ev.setCancelled(true);
                if (ev.getClickedInventory().getName().contains("Unnecessary Feature") && ev.getInventory().getItem(ev.getRawSlot()).getItemMeta().getDisplayName().toLowerCase().contains("wild teleport")) {
                    ev.getWhoClicked().closeInventory();
                    new TeleportGoneWild().WildTeleport((Player) ev.getWhoClicked(), ev.getWhoClicked().hasPermission("wild.wildtp.delay.bypass"));
                }
            }
        }
        catch (Exception PhanaticD){}
    }
}
