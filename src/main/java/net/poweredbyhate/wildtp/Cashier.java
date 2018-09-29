package net.poweredbyhate.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import static net.poweredbyhate.wildtp.WildTP.instace;

/**
 * Created on 2/3/2018.
 *
 * @author RoboMWM
 */
public class Cashier
{
    public Cashier(final Location location)
    {
        if (!instace.noCreditJustCash)
            return;
        instace.cash = location;
        if (location == null)
        {
            WildTP.debug("couldn't get da cash");
            return;
        }
        WildTP.debug(location.toString());
        new BukkitRunnable()
        {
            final int X = location.getChunk().getX();
            final int Z = location.getChunk().getZ();
            int x = X;
            int z = Z;
            int distance = 0;
            int stage = 0;
            int direction = 0;
            final int viewDistance = Bukkit.getViewDistance();
            @Override
            public void run()
            {
                while(true)
                {
                    if (instace.cash != location || distance > viewDistance)
                    {
                        return;
                    }
                    if (distance == 0)
                    {
                        location.getWorld().getChunkAtAsync(location, true);
                        WildTP.debug(String.valueOf(x) + " " + String.valueOf(z));
                        distance++;
                        x++;
                        z++;
                        return;
                    }

                    if (stage % (distance * 2) == 0) //corner
                    {
                        if (stage >= distance * 8) //done with this radius
                        {
                            distance++;
                            stage = 0;
                            direction = 0;
                            x = X + distance;
                            z = Z + distance;
                            return;
                        }
                        direction++;
                    }

                    WildTP.debug(String.valueOf(x) + " " + String.valueOf(z) + " distance: " + distance + " stage: " + stage + " direction: " + direction);

                    switch(direction)
                    {
                        case 1:
                            location.getWorld().getChunkAtAsync(x, z--, true);
                            break;
                        case 2:
                            location.getWorld().getChunkAtAsync(x--, z, true);
                            break;
                        case 3:
                            location.getWorld().getChunkAtAsync(x, z++, true);
                            break;
                        case 4:
                            location.getWorld().getChunkAtAsync(x++, z, true);
                            break;
                        default:
                            return;
                    }
                    stage++;
                }
            }
        }.runTaskLaterAsynchronously(instace, 300L);
    }
}
