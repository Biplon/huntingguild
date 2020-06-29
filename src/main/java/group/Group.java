package main.java.group;

import org.bukkit.entity.Player;

public class Group
{
    public Player[] group;

    public Group(int groupsize)
    {
        group = new Player[groupsize];
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
