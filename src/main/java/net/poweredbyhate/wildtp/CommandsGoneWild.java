package net.poweredbyhate.wildtp;

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
import net.poweredbyhate.wildtp.TeleportGoneWild.Direction;
import net.poweredbyhate.wildtp.TeleportGoneWild.Trigger;

/**
 * Created by John on 5/6/2016.
 */
public class CommandsGoneWild implements CommandExecutor, TabCompleter {
    WildTP olivia; // API usage examples: https://youtu.be/e58IdlvZkRE

    public CommandsGoneWild(WildTP wilde) {
        this.olivia = wilde;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        WildTP.debug("Wild command called by " + sender);

        String err = TooWildForEnums.NO_PERMS;

        if (sender instanceof Player && TooCool2Teleport.isCold((Player) sender)) {
            err = TooWildForEnums.PENDING_RTP;
        }
        else if (args.length == 0) {
            if (sender.hasPermission("wild.wildtp")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    new TeleportGoneWild(Trigger.COMMAND, p).WildTeleport();
                    WildTP.debug(p.getName() + " called /wild args 0");

                    return true;
                }

                err = "§kOne does not simply go wild.";
            }
        }
        else if (args[0].equalsIgnoreCase("portal") && args.length == 2) {
            if (sender.hasPermission("wild.wildtp.portals")) {
                if (sender instanceof Player) {
                    if (!olivia.portalz.ports.containsKey(args[1])) err = TooWildForEnums.PORTAL404;
                    else {
                        olivia.portalz.gotoLabel5(((Player) sender), args[1]);
                        return true;
                    }
                }

                err = "§kWeeeeeeeeeeeeeeeeeeeeeeeeeeeee!";
            }
        }
        else if (String.join(" ", args).equals(TeleportGoneWild.openSesame)) {
            if (sender instanceof Player) {
                BukkitRunnable fry = olivia.ohWait.get(((Player) sender).getUniqueId());

                if (fry != null) {
                    fry.run();
                    return true;
                }

                err = TooWildForEnums.CONFIRMKO;
            }
            else err = "§kI'm walking on sunshine!";
        }
        else if (directions().contains(args[0].toLowerCase())) {
            if (sender.hasPermission("wild.wildtp.direction")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    new TeleportGoneWild(Trigger.COMMAND, p, p.getWorld()).WildTeleport(getDir(args[0]));
                    WildTP.debug(p.getName() + " called /wild <direction> (" + args[0] + ")");

                    return true;
                }

                err = "§kYou can't: Trump had built a wall!";
            }
        }
        else {
            World w = Bukkit.getWorld(args[0]);

            if (w != null) {
                if (sender.hasPermission("wild.wildtp.world")
                && (sender.hasPermission("wild.wildtp.world.*") || sender.hasPermission("wild.wildtp.world." + args[0]))) {
                    if (sender instanceof Player)
                    {
                        WildTP.debug("wild.wildtp.world: " + sender.hasPermission("wild.wildtp.world"));
                        WildTP.debug("wild.wildtp.world.*: " + sender.hasPermission("wild.wildtp.world.*"));
                        WildTP.debug("wild.wildtp.world." + args[0] + ": " + sender.hasPermission("wild.wildtp.world." + args[0]));

                        Player p = (Player) sender;

                        new TeleportGoneWild(Trigger.COMMAND, p, w).WildTeleport();
                        WildTP.debug(p.getName() + " called /wild <world> (" + w.getName() + ")");

                        return true;
                    }

                    err = "§kThere is a man who sold the world...";
                }
            }
            else {
                Player p = Bukkit.getPlayerExact(args[0]);
                if (p == null) return false;

                if (sender.hasPermission("wild.wildtp.others")) {
                    w = (args.length == 2)
                            ? Bukkit.getWorld(args[1])
                            : (sender instanceof Player) ? ((Player) sender).getWorld() : p.getWorld();
                    if (w == null) return false;

                    if (canTPto(sender, w)) {
                        new TeleportGoneWild(Trigger.OTHERGUY, p, w).WildTeleport();
                        WildTP.debug(sender.getName() + " Called /wild args " + args[0]);

                        return true;
                    }
                }
            }
        }

        sender.sendMessage(err);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> candidates = new ArrayList<String>();

        if (args.length == 1) {
            if (sender.hasPermission("wild.wildtp.portals")) candidates.add("portal");

            if (sender.hasPermission("wild.wildtp.direction")) candidates.addAll(directions());

            if (sender.hasPermission("wild.wildtp.others"))
                for (Player player : Bukkit.getOnlinePlayers()) candidates.add(player.getName());

            if (sender.hasPermission("wild.wildtp.world"))
                for (World world : Bukkit.getWorlds()) candidates.add(world.getName());

            return filterList(candidates, args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("portal") && sender.hasPermission("wild.wildtp.portals")) {
            candidates.addAll(olivia.portalz.ports.keySet());
            return filterList(candidates, args[1]);
        }

        if (args.length == 2 && sender.hasPermission("wild.wildtp.others") && Bukkit.getPlayerExact(args[0]) != null) {
            for (World world : Bukkit.getWorlds()) if (canTPto(sender, world)) candidates.add(world.getName());

            return filterList(candidates, args[1]);
        }

        return candidates;
    }

    private boolean canTPto(CommandSender sender, World world) {
        if (sender.hasPermission("wild.wildtp.others.*")) return true;

        return sender.hasPermission("wild.wildtp.others." + world.getName());
    }

    private List<String> directions() {
        List<String> cardinals = new ArrayList<String>();

        cardinals.add(TooWildForEnums.CARD_EAST.toLowerCase());
        cardinals.add(TooWildForEnums.CARD_NORTH.toLowerCase());
        cardinals.add(TooWildForEnums.CARD_SOUTH.toLowerCase());
        cardinals.add(TooWildForEnums.CARD_WEST.toLowerCase());

        return cardinals;
    }

    private Direction getDir(String openDir) {
        // @formatter:off
        if (openDir.equalsIgnoreCase(TooWildForEnums.CARD_EAST))  return Direction.EAST;
        if (openDir.equalsIgnoreCase(TooWildForEnums.CARD_NORTH)) return Direction.NORTH;
        if (openDir.equalsIgnoreCase(TooWildForEnums.CARD_SOUTH)) return Direction.SOUTH;
        if (openDir.equalsIgnoreCase(TooWildForEnums.CARD_WEST))  return Direction.WEST;
        // @formatter:on
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
