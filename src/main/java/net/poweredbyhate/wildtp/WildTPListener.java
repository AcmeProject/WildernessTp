package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by John on 5/11/2016.
 */
public class WildTPListener implements Listener {
    static boolean chargePlayer(Player p, WorldConfig wc, Trigger t, boolean proceed) {
        if (WildTP.econ == null) return true;

        if (wc.cost == 0 || ChecKar.bypass("cost", p, wc, t)) return true;

        if (WildTP.econ.getBalance(p) < wc.cost) return false;

        if (proceed && !WildTP.econ.withdrawPlayer(p, wc.cost).transactionSuccess()) {
            p.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_MONEY));

            return false;
        }

        return true;
    }

    @EventHandler
    private void onCoward(PlayerQuitEvent ev) {
        TeleportGoneWild.cure(ev.getPlayer());
    }

    @EventHandler
    private void onPreTeleport(PreWildTeleportEvent ev) {
        if (!chargePlayer(ev.wildLing, ev.wc, ev.brush, true))ev.setCancelled(true);
    }

    @EventHandler
    private void onPostTele(PostWildTeleportEvent ev) {
        Location lok   = ev.wildLing.getLocation();
        World    plaza = lok.getWorld();

        TeleportGoneWild.cure(ev.wildLing);

        if (ev.wc.doCommandz)
        {
            String p = ev.wildLing.getName(), x = "" + lok.getBlockX(), z = "" + lok.getBlockZ();

            for (String c : ev.wc.commando) Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    c.replace("%PLAYER%", p).replace("%X%", x).replace("%Z%", z));
        }

        String sound = WildTP.instace.getConfig().getString("Sound");
        if (sound == null || sound.isEmpty())
            return;

        try
        {
            plaza.playSound(lok, Sound.valueOf(sound), 1, 1);
        }
        catch (IllegalArgumentException boop)
        {
            WildTP.debug(sound + " is not a valid sound.");
        }
    }
}
