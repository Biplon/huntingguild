package main.java.group;

import main.java.api.Playermanagement;
import main.java.enums.HgGuis;
import main.java.gui.GUIManager;
import main.java.huntingground.HuntingGround;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Group
{
    public HuntingGround myhg;

    public Player[] group;

    public boolean[] ready;

    public Location[] loc;

    public Group(int groupSize)
    {
        group = new Player[groupSize];
        ready = new boolean[groupSize];
        loc = new Location[groupSize];
        Arrays.fill(ready, false);
    }

    public Group(int groupSize,HuntingGround hg)
    {
        myhg = hg;
        group = new Player[groupSize];
        ready = new boolean[groupSize];
        loc = new Location[groupSize];
        Arrays.fill(ready, false);
    }

    public void addPlayer(Player p)
    {
        if (!isFull())
        {
            for (int i = 0; i < group.length; i++)
            {
                if (group[i] == null)
                {
                    group[i] = p;
                    if (group.length > 1)
                    {
                        myhg.sendMessage(p.getDisplayName() +"Joint the hunting group");
                        myhg.sendMessage( "Group: " + getFullSlots()+ "/" + getGroupSize());
                        myhg.sendMessage( "You Need: " + getFreeGroupSlots()+ " Player to start");
                    }
                    if (isFull())
                    {
                        readyCheck();
                    }
                    return;
                }
            }
        }
    }

    public void savePlayerloc()
    {
        for (int i = 0; i < group.length; i++)
        {
            loc[i] = group[i].getLocation();
        }
    }

    public void removePlayer(Player p, boolean disconnect)
    {
        for (int i = 0; i < group.length; i++)
        {
            if (group[i].getUniqueId().equals(p.getUniqueId()))
            {
                if (myhg.isinuse)
                {
                    myhg.teleportPlayerBack(p,loc[i]);
                    if (!disconnect)
                    {
                        Playermanagement.getInstance().loadPlayerIgnoreDisableSync(p);
                    }
                    Playermanagement.getInstance().enablePlayerLoad(p);
                    Playermanagement.getInstance().enablePlayerSave(p);
                }
                group[i] = null;
                ready[i] = false;
                loc[i] = null;
                if (group.length > 1)
                {
                    myhg.sendMessage(p.getDisplayName() + "Leave the hunting group");
                    if (!myhg.isinuse)
                    {
                        myhg.sendMessage( "Group: " + getFullSlots()+ "/" + getGroupSize());
                        myhg.sendMessage( "You Need: " + getFreeGroupSlots()+ " Player to start");
                    }
                }
                return;
            }
        }
    }

    public void readyCheck()
    {
        for (Player p: group)
        {
            p.openInventory(GUIManager.getInstance().getGUIInstance(HgGuis.hggroupreadycheck).getInventory());
        }
    }

    public boolean setPlayerReady(Player p)
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

    public boolean isPlayerInGroup(Player p)
    {
        for (Player pl:group)
        {
            if (pl.getUniqueId() == p.getUniqueId())
            {
                return true;
            }
        }
        return false;
    }

    public void restoreInventory()
    {
        for (Player p : group)
        {
            Playermanagement.getInstance().loadPlayerIgnoreDisableSync(p);
            Playermanagement.getInstance().enablePlayerLoad(p);
            Playermanagement.getInstance().enablePlayerSave(p);
        }
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
