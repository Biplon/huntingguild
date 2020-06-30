package main.java.huntingground;

import org.bukkit.World;

import java.util.ArrayList;

public class HuntingGroundManager
{
    static HuntingGroundManager instance;

    private final ArrayList<HuntingGround> huntingGrounds = new ArrayList();
    private final ArrayList<HuntingGroundBuilder> huntingGroundBuilders = new ArrayList();

    public HuntingGroundManager()
    {
        instance = this;
    }

    public static HuntingGroundManager getInstance()
    {
        return instance;
    }


    public void createHuntingGrounds()
    {

    }

    public HuntingGround getHuntinground(String huntinggroundname)
    {
        for (HuntingGround item : huntingGrounds)
        {
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return item;
            }
        }
        return null;
    }

    public HuntingGroundBuilder getHuntingGroundBuilder(String huntinggroundname)
    {
        for (HuntingGroundBuilder item : huntingGroundBuilders)
        {
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return item;
            }
        }
        return null;
    }


    public boolean createNewHuntingGround(String huntinggroundname, String world)
    {
        huntingGroundBuilders.add(new HuntingGroundBuilder(huntinggroundname, world));
        return true;
    }
}
