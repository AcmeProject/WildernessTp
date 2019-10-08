package net.poweredbyhate.wildtp;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by John on 5/11/2016.
 */
public class PreTeleportEvent implements Listener {

    private WildTP girlsGone;

    public PreTeleportEvent(WildTP wild) {
        girlsGone = wild;
    }

    @EventHandler
    public void onPreTeleport(PreWildTeleportEvent ev) {
        if (WildTP.econ != null && !chargePlayer(ev.getWildLing(), ev.getLocoLocation().getWorld().getName())) {
            ev.setCancelled(true);
            ev.getWildLing().sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_MONEY));
        }
    }

    public boolean chargePlayer(Player p, String w) {
        int cost = girlsGone.thugz.get(w).cost;

        if (cost == 0 || p.hasPermission("wild.wildtp.world.*.free") || p.hasPermission("wild.wildtp.world." + w + ".free")) {
            return true;
        }
        if (WildTP.econ.getBalance(p) < cost) {
            return false;
        }
        EconomyResponse e = WildTP.econ.withdrawPlayer(p, cost);
        return e.transactionSuccess();
    }
}
