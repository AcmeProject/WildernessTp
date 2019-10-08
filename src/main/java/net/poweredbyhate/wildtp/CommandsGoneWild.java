package net.poweredbyhate.wildtp;

import static net.poweredbyhate.wildtp.WildTP.instace;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.google.common.collect.ImmutableList;

/**
 * Created by John on 5/6/2016.
 */
public class CommandsGoneWild implements CommandExecutor, TabCompleter {

    WildTP olivia; // API usage examples: https://youtu.be/e58IdlvZkRE
    
    static final List<String> directions = ImmutableList.of("east", "north", "south", "west");

    public CommandsGoneWild(WildTP wilde) {
        this.olivia = wilde;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        WildTP.debug("Wild command called by " + sender);

        String err = TooWildForEnums.NO_PERMS;

        if (args.length == 0) {
            if (sender.hasPermission("wild.wildtp")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    WildTP.debug(p.getName() + " called /wild args 0");
                    new TeleportGoneWild().WildTeleport(p, p.hasPermission("wild.wildtp.delay.bypass"));
                    return true;
                }

                err = "&kOne does not simply go wild.";
            }
        }
        else if (String.join(" ", args).equals(TeleportGoneWild.openSesame)) {
            if (sender instanceof Player) {
                BukkitRunnable fry = olivia.ohWait.get(((Player) sender).getUniqueId());
                if (fry != null) { fry.run(); return true; }
                err = TooWildForEnums.CONFIRMKO;
            }
            else err = "&kI'm walking on sunshine!";
        }
        else if (directions.contains(args[0].toLowerCase())) {
            if (sender.hasPermission("wild.wildtp.direction")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    WorldConfig wc = instace.thugz.get(p.getWorld().getName());
                    int maxX = wc.maxXY, maxZ = wc.maxXY, minX = wc.minXY, minZ = wc.minXY;
                    switch (args[0].toLowerCase()) {
                        case "north": maxZ = p.getLocation().getBlockZ(); break;
                        case "east":  minX = p.getLocation().getBlockX(); break;
                        case "south": minZ = p.getLocation().getBlockZ(); break;
                        case "west":  maxX = p.getLocation().getBlockX(); break;
                    }
                    WildTP.debug(p.getName() + " called /wild <direction>");
                    new TeleportGoneWild().WildTeleport(p, maxX, minX, maxZ, minZ, p.hasPermission("wild.wildtp.delay.bypass"));
                    return true;
                }

                err = "&kYou can't: Trump had built a wall!";
            }
        }
        else if (Bukkit.getWorld(args[0]) != null) {
            if (    sender.hasPermission("wild.wildtp.world")
                && (sender.hasPermission("wild.wildtp.world.*") || sender.hasPermission("wild.wildtp.world." + args[0]))
            ) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    WildTP.debug(p.getName() + " called /wild <world>");
                    new TeleportGoneWild().WildTeleport(p, args[0], p.hasPermission("wild.wildtp.delay.bypass"));
                    return true;
                }

                err = "&kThere is a man who sold the world...";
            }
        }
        else if (sender.getServer().getPlayerExact(args[0]) != null) {
            if (sender.hasPermission("wild.wildtp.others")) {
                WildTP.debug(sender.getName() + " Called /wild args " + args[0]);
                new TeleportGoneWild().WildTeleport(sender.getServer().getPlayer(args[0]), true);
                return true;
            }
        }
        else
          return false;

        sender.sendMessage(TooWildForEnums.translate(err));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
          List<String> candidates = new ArrayList<String>();

          if (sender.hasPermission("wild.wildtp.direction")) candidates.addAll(directions);

          if (sender.hasPermission("wild.wildtp.others"))
              for (Player player : Bukkit.getOnlinePlayers()) candidates.add(player.getName());

          if (sender.hasPermission("wild.wildtp.world"))
              for (World world : Bukkit.getWorlds()) candidates.add(world.getName()); 

          return filterList(candidates, args[0]);
      }

      return null;
    }

    // Package static methods (to let other classes use them if needed) 

    static List<String> filterList(List<String> list, String startWith) {
        List<String> filtered = new ArrayList<String>();

        for (String element : list)
            if (element.toLowerCase().startsWith(startWith.toLowerCase())) filtered.add(element);

        return filtered;
    }
}
