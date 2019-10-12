package net.poweredbyhate.wildtp;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by John on 5/5/2016.
 */
public class SignClickListener implements Listener {

    private WildTP kim;

    public SignClickListener(WildTP wilde) {
        kim = wilde;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player bob = ev.getPlayer();
        if (!bob.hasPermission("wild.wildtp.sign")) return;

        BlockState bs = ev.getClickedBlock().getState();

        if (bs instanceof Sign && kim.isThisRealLife(((Sign) bs).getLines(), bs.getWorld().getName())) {
            String asylum = kim.seekAsylum(((Sign) bs).getLines(), false);
            WildTP.debug(bob.getName() + " used a WildTP sign (world:" + asylum +")");

            if (asylum == null)
                new TeleportGoneWild().WildTeleport(ev.getPlayer(), bob.hasPermission("wild.wildtp.delay.bypass"));
            else
                new TeleportGoneWild().WildTeleport(ev.getPlayer(), asylum, bob.hasPermission("wild.wildtp.delay.bypass"));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent ev) {
        Block dwayneJohnson = ev.getBlock();

        if (dwayneJohnson.getState() instanceof Sign
            && kim.isThisRealLife(((Sign) ev.getBlock().getState()).getLines(), dwayneJohnson.getWorld().getName())
        ) {
            Player jack = ev.getPlayer();

            if (jack.hasPermission("wild.wildtp.break.sign")) {
                jack.sendMessage(TooWildForEnums.translate(TooWildForEnums.BREAK_SIGN));
                WildTP.debug(jack.getName() + " broke a WildTP sign");
            } else {
                ev.setCancelled(true);
                jack.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_BREAK));
                WildTP.debug(jack.getName() + " tried to break a WildTP sign without Cartman permission!");
            }
        }
    }
}
