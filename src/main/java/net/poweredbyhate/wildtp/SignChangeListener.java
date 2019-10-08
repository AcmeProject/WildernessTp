package net.poweredbyhate.wildtp;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private WildTP kim;

    public SignChangeListener(WildTP wilde) {
        kim = wilde;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent ev) {
        if (kim.isBorn2bWild(ev.getLines())) {
            Player john = ev.getPlayer();

            if (john.getLocation().getBlock().getBiome() == Biome.NETHER || john.getLocation().getBlock().getBiome() == Biome.THE_END) {
                john.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_BIOME.replace("%BIOME%", john.getLocation().getBlock().getBiome().toString())));
                ev.setCancelled(true);
                ev.getBlock().breakNaturally();
                WildTP.debug(john.getName() + " tried to create a WildTP sign in the wrong place!");
                return;
            }
            if (john.hasPermission("wild.wildtp.create.sign")) {
                String shelter = kim.seekAsylum(ev.getLines(), true),
                       tooMuch = kim.moneyOrNuttin((shelter == null)? john.getWorld().getName(): shelter);
                ev.setLine(0, kim.bluredLines[0].replace("%COST%", tooMuch));
                ev.setLine(1, kim.bluredLines[1].replace("%COST%", tooMuch));
                ev.setLine(2, kim.bluredLines[2].replace("%COST%", tooMuch));
                ev.setLine(3, (shelter == null)? "": kim.preferItSmall(shelter));
                john.sendMessage(TooWildForEnums.translate(TooWildForEnums.YES_SIGN));
                WildTP.debug(john.getName() + " created a WildTP sign (world:" + shelter +")");
            } else {
                john.sendMessage(TooWildForEnums.translate(TooWildForEnums.NO_SIGN_PERMS));
                ev.setCancelled(true);
                WildTP.debug(john.getName() + " tried to create a WildTP sign without consentment of king!");
            }
        }
    }
}