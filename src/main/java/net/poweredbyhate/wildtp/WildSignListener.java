package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import net.md_5.bungee.api.ChatColor;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

public class WildSignListener implements Listener {
    private WildTP kim;

    public WildSignListener(WildTP wilde) {
        kim = wilde;
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent ev) {
        Block dwayneJohnson = ev.getBlock();

        if (dwayneJohnson.getState() instanceof Sign
                && isThisRealLife(((Sign) ev.getBlock().getState()).getLines(), dwayneJohnson.getWorld())) {
            Player jack = ev.getPlayer();

            if (jack.hasPermission("wild.wildtp.break.sign")) {
                jack.sendMessage(TooWildForEnums.BREAK_SIGN);
                WildTP.debug(jack.getName() + " broke a WildTP sign");
            }
            else {
                ev.setCancelled(true);
                jack.sendMessage(TooWildForEnums.NO_BREAK);
                WildTP.debug(jack.getName() + " tried to break a WildTP sign without permission!");
            }
        }
    }

    @EventHandler
    private void onClick(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player bob = ev.getPlayer();
        if (!bob.hasPermission("wild.wildtp.sign")) return;

        BlockState bs = ev.getClickedBlock().getState();

        if (bs instanceof Sign && isThisRealLife(((Sign) bs).getLines(), bs.getWorld())) {
            World asylum = seekAsylum(((Sign) bs).getLines(), false);
            if (asylum == null) asylum = bob.getWorld();

            WildTP.debug(bob.getName() + " used a WildTP sign (world:" + asylum.getName() + ")");
            new TeleportGoneWild(Trigger.SIGN, ev.getPlayer(), asylum).WildTeleport();
        }
    }

    @EventHandler
    private void onSignChange(SignChangeEvent ev) {
        if (!isBorn2bWild(ev.getLines())) return;

        Player john = ev.getPlayer();
        Biome  bily = john.getLocation().getBlock().getBiome();

        if (bily == Biome.NETHER || bily == Biome.THE_END) {
            ev.getBlock().breakNaturally();
            ev.setCancelled(true);

            WildTP.debug(john.getName() + " tried to create a WildTP sign in the wrong place!");
            john.sendMessage(TooWildForEnums.NO_BIOME.replace("%BIOME%", bily.toString()));
            return;
        }

        if (!john.hasPermission("wild.wildtp.create.sign")) {
            ev.getBlock().breakNaturally();
            ev.setCancelled(true);

            WildTP.debug(john.getName() + " tried to create a WildTP sign without consentment of king!");
            john.sendMessage(TooWildForEnums.NO_SIGN_PERMS);
            return;
        }

        World shelter = seekAsylum(ev.getLines(), true);
        if (shelter == null) shelter = john.getWorld();

        String tooMuch = moneyOrNuttin(shelter);

        ev.setLine(0, kim.bluredLines[0].replace("%COST%", tooMuch));
        ev.setLine(1, kim.bluredLines[1].replace("%COST%", tooMuch));
        ev.setLine(2, kim.bluredLines[2].replace("%COST%", tooMuch));
        ev.setLine(3, john.getWorld().equals(shelter) ? "" : preferItSmall(shelter.getName()));

        WildTP.debug(john.getName() + " created a WildTP sign (world:" + shelter.getName() + ")");
        john.sendMessage(TooWildForEnums.YES_SIGN);
    }

    private boolean isBorn2bWild(String[] underwears) {
        return /**/underwears[0].equalsIgnoreCase(kim.bluredLines[3])
                || underwears[1].equalsIgnoreCase(kim.bluredLines[3]);
    }

    private boolean isThisRealLife(String[] teeth, World kid) {
        World thug = seekAsylum(teeth, false);
        if (thug == null) thug = kid;

        String jb007 = moneyOrNuttin(thug);
        // @formatter:off        
        for (int i = 0; i < 3; i++)
            if (!ChatColor.stripColor(kim.bluredLines[i].replace("%COST%", jb007))
                    .equals(ChatColor.stripColor(teeth[i]))) return false;
        // @formatter:on
        return true;
    }

    private String moneyOrNuttin(World direStr8) {
        int moneypenny = kim.thugz.get(direStr8.getName()).cost;
        return (moneypenny == 0) ? kim.bluredLines[4] : kim.bluredLines[5].replace("%COST%", "" + moneypenny);
    }

    private String preferItSmall(String littleBig) {
        String skibidi = kim.aliaz.get(littleBig);
        return (skibidi == null) ? littleBig : skibidi;
    }

    private World seekAsylum(String[] bluredLines, boolean baby) {
        if (bluredLines.length != 4) return null;

        int   l = baby ? 1 : 3;
        World w = Bukkit.getWorld(bluredLines[l]);
        if (w != null) return w;

        for (String a : kim.aliaz.keySet()) if (kim.aliaz.get(a).equals(bluredLines[l])) return Bukkit.getWorld(a);

        return null;
    }
}
