package net.poweredbyhate.wildtp;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Lax on 10/4/2016.
 */
public class PortalzGoneWild implements Listener {

    public HashMap<String,String[]> ports = new HashMap<>();
    public HashSet<Player> recentTPs = new HashSet<>();
    File portalFile;
    FileConfiguration portalConf;

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent ev) {
        final Player p = ev.getPlayer();
        for (String s : ports.keySet()) {
            //Bukkit.broadcastMessage(String.valueOf(isInside(p.getLocation(), locationConvert(ports.get(s)[0]), locationConvert(ports.get(s)[1]))));
            if(!hasMoved(ev)){
                return;
            }
            if (recentTPs.contains(p))
                return;
            if (isInside(p.getLocation(), locationConvert(ports.get(s)[0]), locationConvert(ports.get(s)[1]))) {
                WildTP.debug("Player: " + p.getDisplayName() + " entered a portal");
                recentTPs.add(p);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        recentTPs.remove(p);
                    }
                }.runTaskLater(WildTP.instace, 60L);
                new TeleportGoneWild().WildTeleport(p, true);
            }
        }
    }

    public void createPortal(Player p, String name) {
        WildTP.debug("Got create portal");
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region sel = null;
        try
        {
            sel = we.getSession(p).getSelection(BukkitAdapter.adapt(p).getWorld());
        }
        catch (Throwable ball){}
        if (name == null) {
            p.sendMessage("Invalid Name");
            return;
        }
        if (sel == null) {
            p.sendMessage("Invalid WorldEdit Selection");
            return;
        }
        savePortal("Portals."+name,stringConvert(p, sel.getMaximumPoint())+"~"+stringConvert(p, sel.getMinimumPoint()), p);
    }

    public void deletePortal(CommandSender p, String name)
    {
        try {
            portalConf.load(portalFile);
            portalConf.getConfigurationSection("Portals").set(name, null);
            portalConf.save(portalFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            p.sendMessage("Error occurred, check console logs");
            return;
        }
    }

    public void listPortals(CommandSender p)
    {
        StringBuilder yesIusedeez = new StringBuilder("Portals:  ");
        for (String name : ports.keySet())
            yesIusedeez.append(name + ", ");
        yesIusedeez.setLength(yesIusedeez.length() - 2);
        p.sendMessage(yesIusedeez.toString());
    }

    public void loadConfig() {
        WildTP.debug("Got Load Portal Config");
        portalFile = new File(WildTP.instace.getDataFolder(), "Portals.yml");
        portalConf = new YamlConfiguration();
        if (!portalFile.exists()) {
            try {
                portalFile.createNewFile();
                portalConf.load(portalFile);
                portalConf.set("Version",0.1);
                portalConf.save(portalFile);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                portalConf.load(portalFile);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        if (portalConf.getConfigurationSection("Portals") == null) {
            WildTP.debug("No portals saved.");
            return;
        }
        for (String s : portalConf.getConfigurationSection("Portals").getKeys(false)) {
            ports.put(s, portalConf.getString("Portals."+s).split("~"));
            WildTP.debug("Converting portal " + s);
            WildTP.debug(ports.values());
        }
    }

    public void savePortal(String name, String portal, Player p) {
        WildTP.debug("Got save portal " + name + " " + portal);
        portalFile = new File(WildTP.instace.getDataFolder(), "Portals.yml");
        portalConf = new YamlConfiguration();
        try {
            portalConf.load(portalFile);
            portalConf.set(name, portal);
            portalConf.save(portalFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            p.sendMessage("Error occurred, check console logs");
            return;
        }
        p.sendMessage("Portal " + name + " created!");
        loadConfig(); //dum lax
    }

    public boolean hasMoved(PlayerMoveEvent ev) {
        return (ev.getFrom().getX() != ev.getTo().getX() || ev.getFrom().getBlockY() != ev.getTo().getBlockY() || ev.getFrom().getZ() != ev.getTo().getZ());
    }

    public boolean isInside(Location loc, Location l1, Location l2) { //shitty code but not as shit as qballz
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        return loc.getBlockX() >= x1 && loc.getBlockX() <= x2 && loc.getBlockY() >= y1 && loc.getBlockY() <= y2 && loc.getBlockZ() >= z1 && loc.getBlockZ() <= z2;
    }

    public Location locationConvert(String s) {
        String[] x = s.split("\\.");
        return new Location(Bukkit.getServer().getWorld(x[0]),Integer.parseInt(x[1]),Integer.parseInt(x[2]),Integer.parseInt(x[3]));
    }

    public String stringConvert(Entity e, Vector loc) {
        return (e.getWorld().getName()+"."+loc.getBlockX()+"."+loc.getBlockY()+"."+loc.getBlockZ());
    }
}
