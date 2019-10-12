package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static net.poweredbyhate.wildtp.WildTP.instace;

public class TeleportGoneWild {

    boolean bypass;
    static final String openSesame = "$hut Up And Take My M0ney!";

    public void WildTeleport(final Player p, final String world, final boolean bypass) {
        this.bypass = bypass;
        World world1 = Bukkit.getWorld(world);
        if (world1 == null) world1 = p.getWorld();
        WorldConfig wc = instace.thugz.get(world1.getName());
        realTeleportt(p, world1, wc.maxXY, wc.minXY, wc.maxXY, wc.minXY);
    }

    public void WildTeleport(final Player p, final boolean bypass) {
        this.bypass = bypass;
        WorldConfig wc = instace.thugz.get(p.getWorld().getName());
        realTeleportt(p, null, wc.maxXY, wc.minXY, wc.maxXY, wc.minXY);
    }

    public void WildTeleport(final Player p, final int maxX, final int minX, final int maxZ, final int minZ, final boolean bypass)
    {
        this.bypass = bypass;
        realTeleportt(p, null, maxX, minX, maxZ, minZ);
    }

    public boolean realTeleportt(final Player p, World world, int maxX, int minX, int maxZ, int minZ) {
        final World southPark = (world == null)? p.getWorld(): world;
        WildTP.debug("Wild teleport called for " + p.getName() + " for world " + southPark.getName());

        WorldConfig cartman = instace.thugz.get(southPark.getName());
        if (cartman == null) return false; // Respect his authority!

        int kyle = cartman.confirmDelay, stan = cartman.cost; // They killed Kenny!
        long expire = ChecKar.getEpoch() + kyle;
        UUID alienProbe = p.getUniqueId();

        BukkitRunnable taskyNhutch = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isCancelled() && ChecKar.getEpoch() < expire) {
                    ChecKar shaker = cartman.checKar;
                    if (shaker.isInCooldown(p.getUniqueId())) {
                        WildTP.debug("In cooldown: yes");
                        p.sendMessage(TooWildForEnums.translate(TooWildForEnums.COOLDOWN.replace("%TIME%", shaker.getTimeLeft(p))));
                    }
                    else getRandomeLocation(southPark, p, maxX, minX, maxZ, minZ);
                }

                bangbang(alienProbe);
            }
        };
        
        if (stan == 0 || kyle < 1) { taskyNhutch.runTask(instace); return true; }

        bangbang(alienProbe);
        instace.ohWait.put(alienProbe, taskyNhutch);
        taskyNhutch.runTaskLaterAsynchronously(instace, (kyle + 1) * 20);
        oralExam(p, kyle, stan);

        return true;
    }

    public boolean realTeleportt2(final Player p, Location locNotFinal)
    {
        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(p, locNotFinal);
        WildTP.debug("Calling preTeleportEvent");
        Bukkit.getServer().getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");
        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return !preWildTeleportEvent.isRetry();
        }

        WorldConfig wc = instace.thugz.get(locNotFinal.getWorld().getName());

        if (wc.dr0p1n && !bonelessIceScream(locNotFinal)) {
            WildTP.debug("Drop in feature enabled: Setting y=" + wc.dr0pFr0m);
            locNotFinal.setY(wc.dr0pFr0m);
            locNotFinal.setPitch(64);
            OuchieListener.plsSaveDisDood(p);
        }
        final Location loc = locNotFinal;
        WildTP.debug("preping 2 port 2 " + loc);
        if (wc.wamuppah > 0 && !bypass) {
            WildTP.debug("Player needs to wait more");
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.WAIT_MSG.replace("{wait}", "" + wc.wamuppah)));
            TooCool2Teleport.addPlayer(p, goWild(p, loc, wc.wamuppah*20L));
        }
        else
            goWild(p, loc, 0L);
        return true;
    }

    public void getRandomeLocation(World world, Player player, int maxX, int minX, int maxZ, int minZ)
    {
        // Start searching random task
        FutureTask<Location> futureTask = RandomLocationSearchTask.search(world, player);

        instace.getServer().getScheduler().runTaskAsynchronously(instace, new Runnable() {
            @Override
            public void run() {
                try {
                    // Wait and get the random location
                    Location loco = futureTask.get();
                    if (loco == null) {
                        player.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
                        WildTP.debug("No suitable locations found");
                        return;
                    }
                    // Teleport player in main thread
                    if (instace.getServer().getScheduler().callSyncMethod(instace, new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return realTeleportt2(player, loco);
                        }
                    }).get()) {
                        return;
                    }
                } catch (InterruptedException | ExecutionException ignored) {
                }
                player.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_LOCATION));
                WildTP.debug("Random location searching timeout");
            }
        });
    }

    private boolean bonelessIceScream(Location location)
    {
        if (location.getWorld().getEnvironment() != World.Environment.NETHER)
            return false;
        location = location.clone();
        location.setY(127);
        return location.getBlock().getType() == Material.BEDROCK;
    }

    public BukkitTask goWild(final Player p, final Location loc, final Long time)
    {
        return new BukkitRunnable() {
            @Override
            public void run() {
                WorldConfig wc = instace.thugz.get(loc.getWorld().getName());
                WildTP.debug("Starting Random Teleport");
                if (!bypass) {
                    if (wc.wamuppah > 0 && !TooCool2Teleport.isCold(p)) return;
                    TooCool2Teleport.makeHot(p);
                }
                WildTP.debug("Teleporting " + p.getName() + loc);
                if (!p.teleport(loc)) {
                    WildTP.debug("teleport was canceled.");
                    return;
                }
                WildTP.debug(p.getName()+ " Teleported to " + p.getLocation());
                WildTP.debug(p.getName() + " Adding to cooldown");
                wc.checKar.addKewlzDown(p.getUniqueId());
                WildTP.debug("Added to cooldown " + p.getUniqueId());
                PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(postWildTeleportEvent);
            }
        }.runTaskLater(instace, time);
    }

    private void bangbang(UUID u) {
        if (instace.ohWait.containsKey(u)) {
            instace.ohWait.get(u).cancel();
            instace.ohWait.remove(u);
        }
    }

    private void oralExam(Player rafiki, int timon, int pumbaa) {
        String[] parTs = TooWildForEnums.translate(TooWildForEnums.CONFIRMSG)
            .replace("%TIME%", "" + timon).replace("%COST%", "" + pumbaa).split("%CONFIRM%");
  
        rafiki.spigot().sendMessage(
            new ComponentBuilder(parTs[0]).append(
                  new ComponentBuilder(TooWildForEnums.translate(TooWildForEnums.CONFIRMOK))
                  .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wild " + openSesame))
                  .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                      TextComponent.fromLegacyText(TooWildForEnums.translate(TooWildForEnums.CONFIRMON))))
                .create()
            ).append(parTs[1]).create()
        );
    }
}
