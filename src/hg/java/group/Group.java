package hg.java.group;

import pts.java.api.PlayerManagement;
import hg.java.enums.HgGuis;
import hg.java.gui.GUIManager;
import hg.java.huntingground.HuntingGround;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Group
{
    public HuntingGround myhg;

    public boolean playerLeaveByDead;

    public int minsize;

    public Player[] group;

    public boolean[] ready;

    public Location[] loc;

    public Group(int groupSize,int minsize,boolean leaveDead)
    {
        group = new Player[groupSize];
        ready = new boolean[groupSize];
        loc = new Location[groupSize];
        Arrays.fill(ready, false);
        this.minsize = minsize;
        playerLeaveByDead = leaveDead;
    }

    public Group(int groupSize,int minsize,boolean leaveDead, HuntingGround hg)
    {
        myhg = hg;
        group = new Player[groupSize];
        ready = new boolean[groupSize];
        loc = new Location[groupSize];
        Arrays.fill(ready, false);
        this.minsize = minsize;
        playerLeaveByDead = leaveDead;
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
                        myhg.sendMessage(p.getDisplayName() +" joint the group");
                        myhg.sendMessage( "Group: " + getFullSlots()+ "/" + getGroupSize());
                        myhg.sendMessage( "You Need: " + getFreeGroupSlots()+ " Player to start");
                    }
                    if (groupMinSizeReached())
                    {
                        myhg.sendMessageWithClickEvent("You have the min Group size. Do you want to start ?","/hgstartreadycheck",true);
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
                        PlayerManagement.getInstance().loadPlayerIgnoreDisableSync(p);
                    }
                    PlayerManagement.getInstance().enablePlayerLoad(p);
                    PlayerManagement.getInstance().enablePlayerSave(p);
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
                if (getFreeGroupSlots() == getGroupSize())
                {
                    myhg.resetHuntingGround();
                }
                return;
            }
        }
    }

    public void readyCheck()
    {
        for (Player p: group)
        {
            p.openInventory(GUIManager.getInstance().createGUI(HgGuis.hggroupreadycheck));
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
            PlayerManagement.getInstance().savePlayerIgnoreDisableSync(p);
            PlayerManagement.getInstance().disablePlayerSave(p);
            PlayerManagement.getInstance().disablePlayerLoad(p);
        }
    }

    public boolean isPlayerInGroup(Player p)
    {
        for (Player pl : group)
        {
            if (pl != null)
            {
                if (pl.getUniqueId() == p.getUniqueId())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void restoreInventory()
    {
        for (Player p : group)
        {
            PlayerManagement.getInstance().loadPlayerIgnoreDisableSync(p);
            PlayerManagement.getInstance().enablePlayerLoad(p);
            PlayerManagement.getInstance().enablePlayerSave(p);
        }
    }

    public boolean groupMinSizeReached()
    {
        if (minsize < getGroupSize() && getFullSlots() >= minsize)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void clearGroup()
    {
        Arrays.fill(group, null);
        Arrays.fill(ready, false);
        Arrays.fill(loc,null);
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
