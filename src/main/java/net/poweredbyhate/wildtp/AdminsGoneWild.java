package net.poweredbyhate.wildtp;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * Created by Lax on 5/6/2016.
 */
public class AdminsGoneWild implements CommandExecutor, TabCompleter {
    WildTP tpWild;

    public AdminsGoneWild(WildTP wildTP) {
        this.tpWild = wildTP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            for (String aHalpMessage : halpMassage(sender)) sender.sendMessage(aHalpMessage);
            return true;
        }

        String err = TooWildForEnums.NO_PERMS;

        switch (args[0].toLowerCase()) {
            case "create":
                if (sender.hasPermission("wild.wildtp.create.portal")) {
                    if (args.length < 2) return false;
                    tpWild.portalz.initPortal((Player) sender, args[1], (args.length == 3 && args[2].equals("FORCE")));
                    return true;
                }
                break;

            case "delete":
                if (sender.hasPermission("wild.wildtp.create.portal")) {
                    if (args.length < 2) return false;
                    tpWild.portalz.deletePortal(sender, args[1]);
                    return true;
                }
                break;

            case "gui":
                if (!WildTP.enableUselessGUI) return false;

                if (!(sender instanceof Player)) {
                    err = "Open a GUI... from the console? ... Hum hum...";
                    break;
                }

                if (sender.hasPermission("wild.wildtp.set")) {
                    new GeeYouEye().openMenu((Player) sender);
                    return true;
                }
                break;

            case "list":
                if (sender.hasPermission("wild.wildtp.create.portal")) {
                    tpWild.portalz.listPortals(sender);
                    return true;
                }
                break;

            case "reload":
                if (sender.hasPermission("wild.wildtp.reload")) {
                    tpWild.reloadConfig();
                    sender.sendMessage(TooWildForEnums.RELOADED);
                    return true;
                }
                break;

            default:
                return false;
        }

        sender.sendMessage(err);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> candidates = new ArrayList<String>();

        if (args.length == 1) {
            if (WildTP.enableUselessGUI && sender.hasPermission("wild.wildtp.set")) candidates.add("gui");
            if (sender.hasPermission("wild.wildtp.reload")) candidates.add("reload");
            if (sender.hasPermission("wild.wildtp.create.portal")) {
                candidates.add("create");
                candidates.add("delete");
                candidates.add("list");
            }

            return CommandsGoneWild.filterList(candidates, args[0]);
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            candidates.addAll(tpWild.portalz.ports.keySet());
            return CommandsGoneWild.filterList(candidates, args[1]);
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            candidates.add("FORCE");
            return CommandsGoneWild.filterList(candidates, args[2]);
        }

        return candidates;
    }

    private List<String> halpMassage(CommandSender sender) {
        List<String> list = new ArrayList<String>();

        list.add("§6-------------------Help-------------------------");
        list.add("§6* Command - Description");
        list.add("§6* /WildTP - shows this help message");

        if (sender.hasPermission("wild.wildtp.reload")) list.add("§6* /WildTP reload - Reloads the plugin's config");

        if (WildTP.enableUselessGUI && sender.hasPermission("wild.wildtp.set"))
            list.add("§6* /WildTP gui - Open the plugin user interface");

        if (sender.hasPermission("wild.wildtp.create.portal")) {
            list.add("§6* /WildTP create <name> - Creates a portal");
            list.add("§6* /WildTP delete <name> - Deletes a portal");
            list.add("§6* /WildTP list - Lists portals");
        }

        if (sender.hasPermission("wild.wildtp")) list.add("§6* /Wild - Teleports player to random location");
        if (sender.hasPermission("wild.wildtp.direction")) list.add("§6* /Wild [direction] - Directional random tp");
        if (sender.hasPermission("wild.wildtp.world")) list.add("§6* /Wild [world] - Random teleport to a world");
        if (sender.hasPermission("wild.wildtp.others")) {
            list.add("§6* /Wild [player] - Random teleport a player");
            list.add("§6* /Wild [player] [world] - TP player to given world");
        }

        list.add("§6------------------------------------------------");

        return list;
    }
}
