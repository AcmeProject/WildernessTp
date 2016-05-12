package net.poweredbyhate.wildtp;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by John on 5/11/2016.
 */
public class PreTeleportEvent implements Listener {

    @EventHandler
    public void onPreTeleport(PreWildTeleportEvent ev) {
        if (!chargePlayer(ev.getWildLing())) {
            ev.setCancelled(true);
        }
    }

    public boolean chargePlayer(Player p) {
        if (WildTP.cost == 0) {
            return true;
        }
        if (WildTP.econ.getBalance(p) < WildTP.cost) {
            return false;
        }
        EconomyResponse e = WildTP.econ.withdrawPlayer(p, WildTP.cost);
        return e.transactionSuccess();
    }
}
