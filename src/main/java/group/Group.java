package main.java.group;

import main.java.PlayertoSql;
import main.java.api.Playermanagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class Group
{
    public Player[] group;

    public boolean[] ready;

    public Location[] loc;

    public Group(int groupsize)
    {
        group = new Player[groupsize];
        ready = new boolean[groupsize];
        loc = new Location[groupsize];
        Arrays.fill(ready, false);
    }

    public boolean addPlayer(Player p)
    {
        if (!isFull())
        {
            for (int i = 0; i < group.length; i++)
            {
                if (group[i] == null)
                {
                    group[i] = p;
                    return true;
                }
            }
        }
        return false;
    }

    public void savePlayerloc()
    {
        for (int i = 0; i < group.length; i++)
        {
            loc[i] = group[i].getLocation();
        }
    }

    public boolean removePlayer(Player p)
    {
        for (int i = 0; i < group.length; i++)
        {
            if (group[i].getUniqueId().equals(p.getUniqueId()))
            {
                group[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean setPlayerready(Player p)
    {
        for (int i = 0; i < group.length; i++)
        {
            if (group[i] == p)
            {
                ready[i] = true;
            }
        }
        for (boolean b : ready)
        {
            if (!b)
            {
                return false;
            }
        }
        return true;
    }

    public void saveInventory()
    {
        for (Player p : group)
        {
            Playermanagement.getInstance().savePlayerIgnoreDisableSync(p);
            Playermanagement.getInstance().disablePlayerSave(p);
            Playermanagement.getInstance().disablePlayerLoad(p);
        }
    }

    public boolean restoreInventory()
    {
        for (Player p : group)
        {
            Bukkit.getLogger().info(p.getDisplayName());
            Playermanagement.getInstance().loadPlayerIgnoreDisableSync(p);
            Playermanagement.getInstance().enablePlayerLoad(p);
            Playermanagement.getInstance().enablePlayerSave(p);
        }
        return true;
    }

    public void clearGroup()
    {
        Arrays.fill(group, null);
        Arrays.fill(ready, false);
    }

    public boolean isFull()
    {
        for (Player player : group)
        {
            if (player == null)
            {
                return false;
            }
        }
        return true;
    }

    public int getFullSlots()
    {
        int full = 0;
        for (Player player : group)
        {
            if (player != null)
            {
                full++;
            }
        }
        return full;
    }

    public int getGroupSize()
    {
        return group.length;
    }

    public int getFreeGroupSlots()
    {
        int freeslots = 0;
        for (Player player : group)
        {
            if (player == null)
            {
                freeslots++;
            }
        }
        return freeslots;
    }

}
