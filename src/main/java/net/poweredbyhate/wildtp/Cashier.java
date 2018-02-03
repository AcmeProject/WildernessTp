package net.poweredbyhate.wildtp;

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
        instace.cash = location;
        if (location == null)
        {
            WildTP.debug("couldn't get da cash");
            return;
        }
        WildTP.debug(location.toString());
        new BukkitRunnable()
        {
            int x = location.getChunk().getX();
            int z = location.getChunk().getZ();
            int distance = 0;
            int stage = 1;
            int direction = 1;
            @Override
            public void run()
            {
                if (instace.cash != location || distance > instace.getServer().getViewDistance())
                {
                    cancel();
                    return;
                }
                if (distance == 0)
                {
                    location.getChunk().load(true);
                    WildTP.debug(String.valueOf(x) + " " + String.valueOf(z));
                    distance++;
                    return;
                }
                if (stage % distance + 2 == 0) //corner
                {
                    if (stage >= distance * 8) //done with this radius
                    {
                        distance++;
                        stage = 1;
                        direction = 1;
                        x = location.getChunk().getX() + distance;
                        z = location.getChunk().getZ() + distance;
                        return;
                    }
                    direction++;
                }
                switch(direction)
                {
                    case 1:
                        location.getWorld().getChunkAt(x, --z).load(true);
                        break;
                    case 2:
                        location.getWorld().getChunkAt(--x, z).load(true);
                        break;
                    case 3:
                        location.getWorld().getChunkAt(x, ++z).load(true);
                        break;
                    case 4:
                        location.getWorld().getChunkAt(++x, z).load(true);
                        break;
                    default:
                        cancel();
                }
                stage++;
                WildTP.debug(String.valueOf(x) + " " + String.valueOf(z));
            }
        }.runTaskTimer(instace, 300L, 40L);
    }
}
