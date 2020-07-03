package main.java.group;

import main.java.PlayertoSql;
import main.java.api.Playermanagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Group
{
    public Player[] group;

    public ItemStack[] inven;

    public Group(int groupsize)
    {
        group = new Player[groupsize];
        inven = new ItemStack[groupsize];
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

    public void saveInventory()
    {
        for (Player p: group)
        {
            Playermanagement.getInstance().savePlayerIgnoreDisableSync(p);
            Playermanagement.getInstance().disablePlayerSave(p);
            Playermanagement.getInstance().disablePlayerLoad(p);
        }
    }

    public boolean restoreInventory()
    {
        for (Player p: group)
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
        for (int i = 0; i < group.length; i++)
        {
            group[i] = null;
        }
    }

    public boolean isFull()
    {
        for (int i = 0; i < group.length; i++)
        {
            if (group[i] == null)
            {
                return false;
            }
        }
        return true;
    }

    public int getFullSlots()
    {
        int full = 0;
        for (int i = 0; i < group.length; i++)
        {
            if (group[i] != null)
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
        for (int i = 0; i < group.length; i++)
        {
            if (group[i] == null)
            {
                freeslots++;
            }
        }
        return freeslots;
    }

}
