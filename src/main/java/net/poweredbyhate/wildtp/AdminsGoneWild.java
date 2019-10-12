package net.poweredbyhate.wildtp;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
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
    
    private boolean noWE = (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null);

    public AdminsGoneWild(WildTP wildTP) {
        this.tpWild = wildTP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            for (String aHalpMessage : halpMassage(sender))
                sender.sendMessage(TooWildForEnums.translate(aHalpMessage));
            return true;
        }

        boolean nw = (noWE && sender.hasPermission("wild.wildtp.create.portal")
            && (args[0].equals("create") || args[0].equals("delete") || args[0].equals("list")));
        String err = nw? TooWildForEnums.NO_WE: TooWildForEnums.NO_PERMS;

        if (nw) WildTP.debug(sender.getName() + " Tried to " + args[0] + " portal without WE");
        else
            switch (args[0].toLowerCase()) {
                case "create":
                    if (sender.hasPermission("wild.wildtp.create.portal")) {
                        if (args.length < 2) return false;
                        tpWild.getPortalz().createPortal((Player) sender, args[1]);
                        return true;
                    }
                    break;

                case "delete":
                    if (sender.hasPermission("wild.wildtp.create.portal")) {
                        if (args.length < 2) return false;
                        tpWild.getPortalz().deletePortal(sender, args[1]);
                        return true;
                    }
                    break;

                case "gui":
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
                        tpWild.getPortalz().listPortals(sender);
                        return true;
                    }
                    break;
    
                case "reload":
                    if (sender.hasPermission("wild.wildtp.reload")) {
                        tpWild.reloadConfig();
                        sender.sendMessage(TooWildForEnums.translate(TooWildForEnums.RELOADED));
                        return true;
                    }
                    break;
            }

        sender.sendMessage(TooWildForEnums.translate(err));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> candidates = new ArrayList<String>();

            if (sender.hasPermission("wild.wildtp.reload")) candidates.add("reload");
            if (sender.hasPermission("wild.wildtp.set"))    candidates.add("gui");
            if (!noWE && sender.hasPermission("wild.wildtp.create.portal")) {
                candidates.add("create");
                candidates.add("delete");
                candidates.add("list");
            }

            return CommandsGoneWild.filterList(candidates, args[0]);
        }

        return null;
    }

    private List<String> halpMassage(CommandSender sender) {
        List<String> list = new ArrayList<String>();

        list.add("&6-------------------Help-------------------------");
        list.add("&6* Command - Description");
        list.add("&6* /WildTP - shows this help message");

        if (sender.hasPermission("wild.wildtp.reload")) list.add("&6* /WildTP reload - Reloads the plugin's config");
        if (sender.hasPermission("wild.wildtp.set"))    list.add("&6* /WildTP gui - Reloads the plugin's config");
        if (!noWE && sender.hasPermission("wild.wildtp.create.portal")) {
            list.add("&6* /WildTP create <name> - Creates a portal");
            list.add("&6* /WildTP delete <name> - Deletes a portal");
            list.add("&6* /WildTP list - Lists portals");
        }

        if (sender.hasPermission("wild.wildtp"))           list.add("&6* /Wild - Teleports player to random location");
        if (sender.hasPermission("wild.wildtp.direction")) list.add("&6* /Wild [direction] - Directional random tp");
        if (sender.hasPermission("wild.wildtp.others"))    list.add("&6* /Wild [player] - Random teleport a player");
        if (sender.hasPermission("wild.wildtp.world"))     list.add("&6* /Wild [world] - Random teleport to a world");

        list.add("&6------------------------------------------------");

        return list;
    }
}
