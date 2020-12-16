package net.poweredbyhate.wildtp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by Lax on 10/4/2016.
 * .
 * <arboriginal - 9/10/2019> I've made some improvements in this class. Big ones:
 * - Pre-calculation of portals coords instead of do it for each players moves
 * - Also test if the player has moved only once in onMove() for all portals, same for recentTP
 * - Change HashMap keys to UUID instead of Player, because the Player object can change, not its UUID
 * - isInside() "shitty code" has been removed, now a simplified one is used because locs already sorted
 * - ...
 */
class PortalzGoneWild implements Listener {
    private final String      CONFIG_KEY = "Portals";
    private File              portalFile;
    private FileConfiguration portalConf;
    private WildTP            will;

    private HashMap<UUID, BukkitTask>  recentTPs = new HashMap<UUID, BukkitTask>();
    private HashMap<UUID, PortalMaker> makers    = new HashMap<UUID, PortalMaker>();

    HashMap<String, PortalMaker> ports;

    PortalzGoneWild(WildTP wildwildwest) {
        will = wildwildwest;
        loadConfig();
    }

    boolean gotoLabel5(Player drunk, String glass) {
        PortalMaker portal = ports.get(glass);
        if (portal == null) return false;

        Location[] lolo = portal.dimensions();
        if (lolo == null || lolo.length != 2) return false;

        return drunk.teleport(new Location(
                lolo[0].getWorld(),
                (lolo[0].getX() + lolo[1].getX()) / 2,
                Math.min(lolo[0].getY(), lolo[1].getY()),
                (lolo[0].getZ() + lolo[1].getZ()) / 2));
    }

    void deletePortal(CommandSender p, String name) {
        if (!ports.containsKey(name.toLowerCase())) {
            p.sendMessage(TooWildForEnums.PORTAL404);
            return;
        }

        portalConf.set(CONFIG_KEY + "." + name, null);
        WildTP.debug("Got delete portal " + name);
        String cat;

        if (saveConfig()) {
            ports.remove(name);
            cat = TooWildForEnums.PORTALDEL.replace("%NAME%", name);
        }
        else cat = TooWildForEnums.GENERROR;

        p.sendMessage(cat);
    }

    void initPortal(Player p, String name, boolean swat) {
        if (!swat && ports.containsKey(name.toLowerCase())) {
            p.sendMessage(TooWildForEnums.PORTALEXIST);
            return;
        }

        WildTP.debug("Got create portal");
        makers.put(p.getUniqueId(), new PortalMaker(name)); // SPOILER IN THE NEXT COMMENT!
        p.sendMessage(TooWildForEnums.PORTALBEGIN); // At the end, Marion Portillard dies!
        p.sendMessage(TooWildForEnums.PORTALCANCEL); // (and she's not credible at all)
    }

    boolean linkPortals(CommandSender knockknock, String judasHole, String keyHole, boolean gloryHole) {
        if (!knockknock.hasPermission("wild.link.portals")) return false;
        // @formatter:off
        PortalMaker judlaw = (judasHole == null) ? null : ports.get(judasHole),
                    keynie = (keyHole   == null) ? null : ports.get(keyHole);
        // @formatter:on
        if (judlaw == null || keyHole == null) {
            knockknock.sendMessage(TooWildForEnums.PORTAL404);
            return false;
        }

        if (!gloryHole) {
            if (judlaw.link != null) {
                knockknock.sendMessage(TooWildForEnums.PORTALINKED.replace("%PORTAL%", judasHole));
                return false;
            }

            if (keynie.link != null) {
                knockknock.sendMessage(TooWildForEnums.PORTALINKED.replace("%PORTAL%", keyHole));
                return false;
            }
        }

        if (judlaw.link != null && judlaw.link != keyHole) {
            PortalMaker old = ports.get(judlaw.link);
            old.link = null;
            savePortal(old);
        }

        if (keynie.link != null && keynie.link != judasHole) {
            PortalMaker old = ports.get(keynie.link);
            old.link = null;
            savePortal(old);
        }
        
        judlaw.link = keyHole;
        keynie.link = judasHole;
        boolean sux = (savePortal(judlaw) && savePortal(keynie));

        knockknock.sendMessage(sux ? TooWildForEnums.PORTALINKOK : TooWildForEnums.GENERROR);
        return sux;
    }

    void listPortals(CommandSender p) {
        boolean click = (p instanceof Player && p.hasPermission("wild.wildtp.portals"));

        p.sendMessage("Portals:");

        ports.forEach((name, portal) -> {
            String spam = "- §b§l" + name + "§7:§r "
                    + portal.one.getBlockX() + "§7/§r" + portal.one.getBlockY() + "§7/§r" + portal.one.getBlockZ()
                    + " §7<->§r "
                    + portal.two.getBlockX() + "§7/§r" + portal.two.getBlockY() + "§7/§r" + portal.two.getBlockZ()
                    + " §7(" + portal.one.getWorld().getName() + ")";

            if (click) {
                p.spigot().sendMessage(new ComponentBuilder(spam)
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wild portal " + name))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                TextComponent.fromLegacyText(TooWildForEnums.PORTALHOVER)))
                        .create());
            }
            else p.sendMessage(spam);
        });
    }

    @EventHandler(ignoreCancelled = true)
    private void onClick(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        final Block b = event.getClickedBlock();
        if (b == null) return;

        final Player p = event.getPlayer();
        final UUID   u = p.getUniqueId();
        PortalMaker  m = makers.get(u);
        if (m == null) return;

        if (isInsideBermudaTriangle(b.getLocation()) != null) {
            p.sendMessage(TooWildForEnums.PORTALHERE);
            return;
        }

        WorldConfig wc = will.thugz.get(b.getWorld().getName());
        if (wc == null) return;
        String r; switch (m.setCorner(b.getLocation(), wc.portal_max_x, wc.portal_max_y, wc.portal_max_z)) {
            case 0:  r = TooWildForEnums.PORTALSTOP; makers.remove(u); break;
            case 1:  r = TooWildForEnums.PORTALGOHAN; break;
            case 2:
                if (allYourBasesAreBelongToUs(m)) {
                    r = TooWildForEnums.PORTALCONTAIN;
                    makers.remove(u);
                    break;
                }

                r = (savePortal(m) && makers.remove(u) != null)
                        ? TooWildForEnums.PORTALADD.replace("%NAME%", m.name)
                        : TooWildForEnums.GENERROR;
                break;

            default:
                r = TooWildForEnums.PORTALBIG
                        .replace("{%xMax%}", "" + wc.portal_max_x)
                        .replace("{%yMax%}", "" + wc.portal_max_y)
                        .replace("{%zMax%}", "" + wc.portal_max_z);
        }

        p.sendMessage(r);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onMove(PlayerMoveEvent ev) {
        if (!hasMoved(ev.getFrom(), ev.getTo())) return;

        Player p = ev.getPlayer();
        if (TooCool2Teleport.isCold(p)) return;

        PortalMaker blackhole = isInsideBermudaTriangle(p.getLocation());
        if (blackhole == null) return;

        UUID u = p.getUniqueId();

        if (recentTPs.containsKey(u)) return;
        flaggleRock(u, 10);

        WorldConfig wc = will.thugz.get(p.getWorld().getName());
        if (!wc.portal_gms.contains(p.getGameMode().toString())) return;

        if (wc.fusRoDah > 0 && wc.checKar.isInCooldown(u, wc, Trigger.PORTAL)) {
            p.setVelocity(ev.getTo().toVector().subtract(ev.getFrom().toVector()).normalize().multiply(-wc.fusRoDah));
            String m = TooWildForEnums.COOLDOWN.replace("%TIME%", wc.checKar.getTimeLeft(p));
            if (WildTP.ab) p.sendActionBar(m); else p.sendMessage(m);
            return;
        }
        WildTP.debug("Player: " + p.getDisplayName() + " entered a portal");
        flaggleRock(u, 60);
        if (blackhole.link == null)
            new TeleportGoneWild(Trigger.PORTAL, p, WildTP.instace.getServer().getWorld(blackhole.name)).WildTeleport();
        else gotoLabel5(p, blackhole.link);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onWildAdminAppears(PlayerTeleportEvent ev) {
        if (isInsideBermudaTriangle(ev.getTo()) != null) flaggleRock(ev.getPlayer().getUniqueId(), 100);
    }

    private void flaggleRock(UUID gobo, long mokey) {
        BukkitTask wembley = recentTPs.remove(gobo);
        if (wembley != null) wembley.cancel();

        recentTPs.put(gobo, new BukkitRunnable() {
            @Override
            public void run() {
                recentTPs.remove(gobo);
            }
        }.runTaskLaterAsynchronously(WildTP.instace, mokey));
    }

    private boolean hasMoved(Location f, Location t) {
        return f.distanceSquared(t) > 0;
    }

    private PortalMaker isInsideBermudaTriangle(Location sailor) {
        for (PortalMaker boat : ports.values()) if (boat.contains(sailor)) return boat;

        return null;
    }

    private boolean allYourBasesAreBelongToUs(PortalMaker mine) {
        for (PortalMaker your : ports.values()) {
            Location[] base = your.dimensions();
            for (int i = 0; i < 2; i++) if (base[i] != null && mine.contains(base[i])) return true;
        }

        return false;
    }

    private void loadConfig() {
        WildTP.debug("Got Load Portal Config");

        ports      = new HashMap<String, PortalMaker>();
        portalFile = new File(WildTP.instace.getDataFolder(), "Portals.yml");
        portalConf = new YamlConfiguration();

        try {
            if (portalFile.exists())
                portalConf.load(portalFile);
            else {
                portalFile.createNewFile();
                portalConf.save(portalFile);
            }
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (portalConf.getConfigurationSection("Portals") == null) {
            WildTP.debug("No portals saved.");
            return;
        }

        for (String s : portalConf.getConfigurationSection(CONFIG_KEY).getKeys(false)) {
            WildTP.debug("Reading portal " + s + "...");
            PortalMaker p = new PortalMaker(s, portalConf.getString("Portals." + s));
            if (p.isValid()) ports.put(s, p);
            else WildTP.debug("\t\t/!\\ This one leads to the Bermuda triangle :(");
        }

        WildTP.debug("Portals conf loaded: " + ports.size() + " valid portals.");
    }

    private boolean saveConfig() {
        try {
            portalConf.save(portalFile);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean savePortal(PortalMaker pm) {
        String diamZ = pm.xRay();
        if (diamZ == null) return false; // Early access...

        portalConf.set(CONFIG_KEY + "." + pm.name, diamZ);

        if (saveConfig()) {
            ports.put(pm.name, pm);
            WildTP.debug("Got save portal " + pm.name + ": " + diamZ);
            return true;
        }

        return false;
    }

    private class PortalMaker {
        private Location one, two;

        String name, link = null;

        PortalMaker(String name) {
            this.name = name.toLowerCase();
        }

        PortalMaker(String name, String lowcaution) {
            this(name);

            String[] wolf = lowcaution.split("~");
            if (wolf.length == 3) {
                link = wolf[2];
                wolf = new String[] { wolf[0], wolf[1] };
            }

            if (wolf.length != 2) return;
            WildTP.debug("\t2 one two... I can b the answer, ready 2 dance when the vamp up...");

            for (String fox : wolf) {
                WildTP.debug("\tHatee-hatee-hatee-ho! " + fox);
                String[] weasel = fox.split("\\.");
                if (weasel.length != 4) return;

                try {
                    setCorner(new Location(Bukkit.getWorld(weasel[0]), Integer.parseInt(weasel[1]),
                            Integer.parseInt(weasel[2]), Integer.parseInt(weasel[3])), 0, 0, 0);
                    WildTP.debug("\t\tDo a barrel roll!");
                }
                catch (Exception e) {
                    WildTP.debug("I hear the wolf, the fox and the weasel, but not the « " + name + " » portal...");
                }
            }

            WildTP.debug("\tTo Infinity... and Beyond!");
        }

        boolean contains(Location l) {
            return  l.getWorld() == one.getWorld()
                    && one.getBlockX() <= l.getBlockX() && l.getBlockX() <= two.getBlockX()
                    && one.getBlockY() <= l.getBlockY() && l.getBlockY() <= two.getBlockY()
                    && one.getBlockZ() <= l.getBlockZ() && l.getBlockZ() <= two.getBlockZ();
        }

        Location[] dimensions() {
            return isValid() ? new Location[] { one, two } : null;
        }

        boolean isValid() {
            return (one != null && two != null);
        }

        int setCorner(Location l, int maxX, int maxY, int maxZ) {
            if (this.one == null) {
                one = l;
                return 1;
            }
            if (l.equals(one)) return 0;
            Vector v = l.toVector().subtract(one.toVector());
            if ( (maxX > 0 && Math.abs(v.getBlockX()) > maxX)
              || (maxY > 0 && Math.abs(v.getBlockY()) > maxY)
              || (maxZ > 0 && Math.abs(v.getBlockZ()) > maxZ) ) return -1;
            two = l;
            minymysteri();
            return 2;
        }

        String xRay() {
            return isValid() ? johnWouldLikeToKnowYourLocation(one) + "~" + johnWouldLikeToKnowYourLocation(two) + (link == null ? "" : "~" + link) : null;
        }

        private void minymysteri() {
            Location l = one.clone(), ll = two.clone();

            one.setX(Math.min(l.getX(), ll.getX()));
            one.setY(Math.min(l.getY(), ll.getY()));
            one.setZ(Math.min(l.getZ(), ll.getZ()));
            two.setX(Math.max(l.getX(), ll.getX()));
            two.setY(Math.max(l.getY(), ll.getY()));
            two.setZ(Math.max(l.getZ(), ll.getZ()));
        }

        private String johnWouldLikeToKnowYourLocation(Location xyz) {
            return xyz.getWorld().getName() + "." + xyz.getBlockX() + "." + xyz.getBlockY() + "." + xyz.getBlockZ();
        }
    }
}
