package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.instace;
import static net.poweredbyhate.wildtp.WildTP.randomeWorlds;
import static net.poweredbyhate.wildtp.WildTP.useRandomeWorldz;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import io.papermc.lib.PaperLib;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportGoneWild {
    private boolean[]      boo;
    private Direction      way;
    private final Trigger        how;
    private final Player         who;
    private PotionEffect[] queen;
    private World          whr;
    private WorldConfig    wc;
    private int retries;

    enum Direction {
        EAST, NORTH, SOUTH, WEST
    }

    enum Trigger {
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
        return cook(direction) && realTeleportt();
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

        bossbar.setProgress(Math.min((float) attempt / virus.retries, 1));
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
            public void run() {
                if (isCancelled()) return;
                if (bossbar == null || bossbar.getPlayers().isEmpty()) { cancel(); return; }
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
        WildTP.debug("starting cook()");
        if (who == null || !who.isOnline()) return false;
        if (whr == null) whr = useRandomeWorldz ? getRandomeWorld() : who.getWorld();
        if (whr == null) return false;
        // for after the digestion...
        wc = instace.thugz.get(whr.getName());
        if (wc == null) return false;

        if (wc.checKar.isInCooldown(who.getUniqueId(), wc, how)) {
            String m = TooWildForEnums.COOLDOWN.replace("%TIME%", wc.checKar.getTimeLeft(who));
            if (WildTP.ab) who.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(m)); else who.sendMessage(m);
            return false;
        }

        if (!WildTPListener.chargePlayer(who, wc, how, false)) {
            who.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_MONEY));
            return false;
        }
        way = meat;
        boo = TooCool2Teleport.powerSupply(
            wc.freeze     || (wc.freezePortal     && how == Trigger.PORTAL),
            wc.moveCancel || (wc.moveCancelPortal && how == Trigger.PORTAL));
        queen = wc.effects.get(how);
        return true;
    }

    private boolean realTeleportt() {
        WildTP.debug("Wild teleport called for " + who.getName() + " for world " + whr.getName());

        final long wasteUrTime = ChecKar.getEpoch() + wc.confirmDelay;
        final UUID youYouEyeDee = who.getUniqueId();
        // What everybody waiting for...
        bangbang(youYouEyeDee);

        BukkitRunnable taskyNhutch = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isCancelled() && ChecKar.getEpoch() < wasteUrTime) {
                    ChecKar shaker = wc.checKar;
                    if (shaker.isInCooldown(youYouEyeDee, wc, how)) {
                        WildTP.debug("In cooldown: yes");
                        String m = TooWildForEnums.COOLDOWN.replace("%TIME%", shaker.getTimeLeft(who));
                        if (WildTP.ab) who.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(m)); else who.sendMessage(m);
                    }
                    else
                    {
                        retries = wc.retries;
                        getRandomeLocation();
                    }
                }
                bangbang(youYouEyeDee);
            }
        };

        if (wc.cost == 0 || wc.confirmDelay < 1 || bypass("cost")) taskyNhutch.runTask(instace);
        else {
            instace.ohWait.put(youYouEyeDee, taskyNhutch);
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
            WildTP.debug("Player needs to wait more: " + wc.wamuppah * 20);

            TooCool2Teleport.addPlayer(who, boo, queen, new BukkitRunnable() {
                @Override
                public void run() {
                    WildTP.debug("isCancelled: " + isCancelled());
                    if (!isCancelled())
                        goWild(loc);
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
                continue;
            }

            int chance = randomeWorlds.getInt(worldString);
            if (chance < 1) //fixes bug where setting as 0 overwrites previous entry
                continue;

            totalChance += chance;
            hesDaMap.put(totalChance, vote4Pedro);
        }

        int daChosenOne = WildWarrantTax.r4nd0m(totalChance, 0);

        for (Integer blah : hesDaMap.keySet())
            if (blah >= daChosenOne)
                return hesDaMap.get(blah);
        return null;
    }

    private void getRandomeLocation() {
        WildTP.debug("starting getRandomeLocation()");
        TooCool2Teleport.addPlayer(who, boo, queen, null);
        if (retries-- < 0)
        {
            WildTP.debug("No suitable locations found");
            who.sendMessage(TooWildForEnums.NO_LOCATION);
            TooCool2Teleport.microwave(who);
            return;
        }

        if (!TooCool2Teleport.isCold(who))
        {
            WildTP.debug(who + " is cold");
            return;
        }

        TeleportGoneWild.focus(who, wc, retries);

        new WildWarrantTax(who, wc, way).search().thenAccept(location ->
        {
            Location loco = location;
            if (loco != null)
                realTeleportt2(loco);
            else
            {
                WildTP.debug("unsuitable location, trying again.");
                getRandomeLocation();
            }
        });
    }

    private void goWild(Location loc) {
        PaperLib.getChunkAtAsync(loc, true).thenAccept(chunk ->
        {
            if (!TooCool2Teleport.microwave(who))
            {
                WildTP.debug("unmicrowaved leftovers.");
                return;
            }

            WildTP.debug("Teleporting " + who.getName() + loc);

            if (WildWarrantTax.bonelessIceScream(loc)) {
                if (wc.whoYaGonaCall) {
                    WildTP.debug("Here come the §cfiremen§r!");
                    Block block = loc.getBlock(); block.setType(Material.AIR);
                    for (BlockFace flame :
                        new BlockFace[] { BlockFace.UP, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST }) {
                        Block bed = block.getRelative(flame); if (bed.getType() == Material.FIRE) bed.setType(Material.AIR);
                    } // How do we sleep while our beds are burning? //Ask St. John Vianney
                }
            }
            else if (wc.dr0p1n) {
                WildTP.debug("Drop in feature enabled: Setting y=" + wc.dr0pFr0m);
                loc.setY(wc.dr0pFr0m);
                loc.setPitch(64);
                OuchieListener.plsSaveDisDood(who);
            }

            if (!who.teleport(loc.clone().add(0.5, 0.5, 0.5), PlayerTeleportEvent.TeleportCause.COMMAND)) {
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
        });
    }

    private boolean bypass(String what) {
        return ChecKar.bypass(what, who, wc, how);
    }

    private void bangbang(UUID youYouEyeDe) {
        if (instace.ohWait.containsKey(youYouEyeDe)) {
            instace.ohWait.remove(youYouEyeDe).cancel();
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
