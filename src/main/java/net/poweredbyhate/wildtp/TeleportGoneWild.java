package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.randomeWorlds;
import static net.poweredbyhate.wildtp.WildTP.useRandomeWorldz;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportGoneWild {
    private boolean[]      boo;
    private Direction      way;
    private Trigger        how;
    private Player         who;   // he's doctor!
    private PotionEffect[] queen; // it's a kind of magic!
    private World          whr;
    private WorldConfig    wc;

    static enum Direction {
        EAST, NORTH, SOUTH, WEST
    }

    static enum Trigger {
        COMMAND, GUI, JOIN, OTHERGUY, PORTAL, SIGN
    }

    static final String openSesame = "$hut Up And Take My M0ney!";

    TeleportGoneWild(final Trigger trigger, final Player p) {
        this(trigger, p, null);
    }

    TeleportGoneWild(final Trigger trigger, final Player p, World w) {
        how = trigger;
        who = p;
        whr = w;
    }

    boolean WildTeleport() {
        return WildTeleport(null);
    }

    boolean WildTeleport(Direction direction) {
        return (cook(direction) && WildTPListener.chargePlayer(who, wc, how, false)) ? realTeleportt() : false;
    }

    static private KeyedBossBar auscultate(Player sickPlayer, BarColor color, BarStyle style) {
        NamespacedKey key     = cerealKiller(sickPlayer);
        KeyedBossBar  bossbar = Bukkit.getBossBar(key);

        if (bossbar == null) {
            bossbar = Bukkit.createBossBar(key, "", color, style);
            bossbar.addPlayer(sickPlayer);
            bossbar.setVisible(true);
        }
        else {
            bossbar.setColor(color);
            bossbar.setStyle(style);
        }

        return bossbar;
    }

    static NamespacedKey cerealKiller(Player freddy) {
        return new NamespacedKey(instace, "iNoWutUdidLastTick/" + freddy.getUniqueId());
    }

    static void cure(Player guiltyPlayer) {
        NamespacedKey key     = cerealKiller(guiltyPlayer);
        KeyedBossBar  bossbar = Bukkit.getBossBar(key);
        if (bossbar == null) return;

        bossbar.removeAll();
        Bukkit.removeBossBar(cerealKiller(guiltyPlayer));
    }

    static void focus(Player innocentPlayer, WorldConfig virus, int retry) {
        if (!virus.bar_enabled) return;

        final KeyedBossBar bossbar = auscultate(innocentPlayer, virus.bar_color_searching, virus.bar_style_searching);
        if (bossbar == null) return;

        int attempt = virus.retries - retry;

        bossbar.setProgress((float) attempt / virus.retries);
        bossbar.setTitle(TooWildForEnums.ATTEMPT
                .replace("%ATTEMPT%", "" + attempt).replace("%MAX%", "" + virus.retries));
    }

    static void infect(Player innocentPlayer, WorldConfig ebola) {
        if (!ebola.bar_enabled) return;

        final KeyedBossBar bossbar = auscultate(innocentPlayer, ebola.bar_color_waiting, ebola.bar_style_waiting);
        if (bossbar == null) return;

        final int milk = ebola.wamuppah;

        bossbar.setProgress(1);
        bossbar.setTitle(TooWildForEnums.PROCEED);

        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() { // @formatter:off
                if (isCancelled()) return;
                if (bossbar == null || bossbar.getPlayers().isEmpty()) { cancel(); return; };
                // @formatter:on
                int   s = milk - ++i;
                float l = Math.max((float) s / milk, 0F);

                bossbar.setProgress(l);
                bossbar.setTitle((l > 0) ? TooWildForEnums.WAIT_MSG.replace("{wait}", "" + s)
                        : TooWildForEnums.PROCEED);
            }
        }.runTaskTimer(instace, 20, 20);
    }

    private boolean cook(Direction meat) {
        if (who == null || !who.isOnline()) return false;
        // @note: no need to do world randomizer later each tries, enough once here + so we have wc
        if (whr == null) whr = useRandomeWorldz ? getRandomeWorld() : who.getWorld();
        if (whr == null) return false;
        // for after the digestion...
        wc = instace.thugz.get(whr.getName());
        if (wc == null) return false;
        // @formatter:off
        way = meat; // Sorry vegans, here meat is the way!
        boo = TooCool2Teleport.powerSupply(
            wc.freeze     || (wc.freezePortal     && how == Trigger.PORTAL),
            wc.moveCancel || (wc.moveCancelPortal && how == Trigger.PORTAL));
        queen = wc.effects.get(how);
        // @formatter:on
        return true;
    }

    private boolean realTeleportt() {
        WildTP.debug("Wild teleport called for " + who.getName() + " for world " + whr.getName());

        final long myTralala = ChecKar.getEpoch() + wc.confirmDelay; // i63cgUeSsY0
        final UUID ginaWilde = who.getUniqueId();
        // What everybody waiting for...
        bangbang(ginaWilde);

        BukkitRunnable taskyNhutch = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isCancelled() && ChecKar.getEpoch() < myTralala) {
                    ChecKar shaker = wc.checKar;
                    // Almost impossible... she's too hot
                    if (shaker.isInCooldown(ginaWilde, wc, how)) {
                        WildTP.debug("In cooldown: yes");
                        who.sendMessage(TooWildForEnums.COOLDOWN.replace("%TIME%", shaker.getTimeLeft(who)));
                    }
                    else getRandomeLocation();
                }
                // ding dong dong you touch my tralala
                bangbang(ginaWilde);
            }
        };

        if (wc.cost == 0 || wc.confirmDelay < 1 || bypass("cost")) taskyNhutch.runTask(instace);
        else {
            instace.ohWait.put(ginaWilde, taskyNhutch);
            taskyNhutch.runTaskLaterAsynchronously(instace, (wc.confirmDelay + 1) * 20);
            oralExam(who, wc.confirmDelay, wc.cost);
        }

        return true;
    }

    private boolean realTeleportt2(Location loc) {
        if (!TooCool2Teleport.isCold(who)) return true;

        PreWildTeleportEvent preWildTeleportEvent = new PreWildTeleportEvent(who, loc, wc, how);
        WildTP.debug("Calling preTeleportEvent");

        Bukkit.getPluginManager().callEvent(preWildTeleportEvent);
        WildTP.debug("Called preWildTeleportEvent");

        if (preWildTeleportEvent.isCancelled()) {
            WildTP.debug("preWildTeleport Cancelled");
            return true;
        }

        WildTP.debug("preping 2 port 2 " + loc);

        if (wc.wamuppah < 1 || bypass("delay")) goWild(loc);
        else {
            WildTP.debug("Player needs to wait more");

            TooCool2Teleport.addPlayer(who, boo, queen, new BukkitRunnable() {
                @Override
                public void run() {
                    if (!isCancelled()) goWild(loc);
                }
            }.runTaskLater(instace, wc.wamuppah * 20));

            TeleportGoneWild.infect(who, wc);
            who.sendMessage(TooWildForEnums.WAIT_MSG.replace("{wait}", "" + wc.wamuppah));
        }

        return true;
    }

    private World getRandomeWorld() {
        Map<Integer, World> hesDaMap    = new LinkedHashMap<Integer, World>();
        Integer             totalChance = 0;

        for (String worldString : randomeWorlds.getKeys(false)) {
            World vote4Pedro = Bukkit.getWorld(worldString);

            if (vote4Pedro == null) {
                Bukkit.getLogger().warning("World « " + worldString + " » does not exist. "
                        + "We recommend removing the world from randomWorlds in config.yml");
                continue; // You should vote for Pedro!
            }

            totalChance += randomeWorlds.getInt(worldString);
            hesDaMap.put(totalChance, vote4Pedro);
        }

        int daChosenOne = RandomLocationSearchTask.r4nd0m(totalChance, 0);

        for (Integer blah : hesDaMap.keySet()) if (blah >= daChosenOne) return hesDaMap.get(blah);
        // Nooooooooooooo! You were the chosen one...
        return null;
    }

    private void getRandomeLocation() {
        TooCool2Teleport.addPlayer(who, boo, queen,
                Bukkit.getScheduler().runTaskAsynchronously(instace, new Runnable() {
                    @Override
                    public void run() {
                        try { // Start searching random task
                            FutureTask<Location> futureTask = new RandomLocationSearchTask(who, wc).search(way);
                            // Wait and get the random location
                            Location loco = futureTask.get();

                            if (loco == null) WildTP.debug("No suitable locations found");
                            // Teleport player in main thread
                            else if (Bukkit.getScheduler().callSyncMethod(instace, new Callable<Boolean>() {
                                @Override
                                public Boolean call() throws Exception {
                                    return realTeleportt2(loco);
                                }
                            }).get()) return;
                            else WildTP.debug("Random location searching timeout");
                        }
                        catch (InterruptedException | ExecutionException ignored) {
                            TooCool2Teleport.microwave(who);
                            WildTP.debug(ignored.getMessage());
                            return;
                        }

                        TooCool2Teleport.microwave(who);
                        who.sendMessage(TooWildForEnums.NO_LOCATION);
                    }
                }));
    }

    private void goWild(Location loc) {
        if (!TooCool2Teleport.microwave(who)) return;

        WildTP.debug("Teleporting " + who.getName() + loc);

        if (wc.dr0p1n && !RandomLocationSearchTask.bonelessIceScream(loc)) {
            WildTP.debug("Drop in feature enabled: Setting y=" + wc.dr0pFr0m);
            loc.setY(wc.dr0pFr0m);
            loc.setPitch(64);
            OuchieListener.plsSaveDisDood(who);
        }

        if (!who.teleport(loc)) {
            WildTP.debug("teleport was canceled.");
            return;
        }

        WildTP.debug(who.getName() + " Teleported to " + who.getLocation());

        if (!bypass("cooldown")) {
            WildTP.debug(who.getName() + " Adding to cooldown");
            wc.checKar.addKewlzDown(who.getUniqueId());
            WildTP.debug("Added to cooldown " + who.getUniqueId());
        }

        PostWildTeleportEvent postWildTeleportEvent = new PostWildTeleportEvent(who, wc);
        Bukkit.getPluginManager().callEvent(postWildTeleportEvent);
    }

    private boolean bypass(String what) {
        return ChecKar.bypass(what, who, wc, how);
    }

    private void bangbang(UUID xActress) {
        if (instace.ohWait.containsKey(xActress)) {
            instace.ohWait.get(xActress).cancel();
            instace.ohWait.remove(xActress);
        }
    }

    private void oralExam(Player rafiki, int timon, int pumbaa) {
        String[] parTs = TooWildForEnums.CONFIRMSG.replace("%TIME%", "" + timon).replace("%COST%", "" + pumbaa)
                .split("%CONFIRM%");

        rafiki.spigot().sendMessage(
                new ComponentBuilder(parTs[0]).append(
                        new ComponentBuilder(TooWildForEnums.CONFIRMOK)
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wild " + openSesame))
                                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        TextComponent.fromLegacyText(TooWildForEnums.CONFIRMON)))
                                .create())
                        .append(parTs[1]).create());
    }
}
