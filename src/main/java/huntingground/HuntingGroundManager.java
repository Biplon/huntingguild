package main.java.huntingground;

import main.java.HuntingGuild;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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


    public void loadHuntingGrounds()
    {
        File folder = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/");

        if (!folder.exists())
        {
            folder.mkdir();
        }
        List<String> result = new ArrayList<>();

        search(".*\\.yml", folder, result);

        for (String s : result)
        {
            huntingGroundBuilders.add(new HuntingGroundBuilder(s));
        }

        result.clear();
        folder = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        search(".*\\.yml", folder, result);

        for (String s : result)
        {
            huntingGrounds.add(new HuntingGround(s));
        }
    }

    private static void search(final String pattern, final File folder, List<String> result)
    {
        for (final File f : folder.listFiles())
        {

            if (f.isDirectory())
            {
                search(pattern, f, result);
            }

            if (f.isFile())
            {
                if (f.getName().matches(pattern))
                {
                    result.add(f.getAbsolutePath());
                }
            }

        }
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


    public boolean changeHuntingGroundMode(String hgname)
    {
        File file;
        if (getHuntinground(hgname) != null)
        {
            if (!getHuntinground(hgname).isinuse)
            {
                file = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml");
                if (file.renameTo(new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/" + hgname + ".yml")))
                {
                    huntingGrounds.remove(getHuntinground(hgname));
                    huntingGroundBuilders.add(new HuntingGroundBuilder(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/" + hgname + ".yml"));
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (getHuntingGroundBuilder(hgname) != null)
        {
            if (getHuntingGroundBuilder(hgname).saveHuntingGround())
            {
                file = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/" + hgname + ".yml");
                if (file.renameTo(new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml")))
                {
                    huntingGroundBuilders.remove(getHuntingGroundBuilder(hgname));
                    huntingGrounds.add(new HuntingGround(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml"));
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public boolean createNewHuntingGround(String huntinggroundname, String world)
    {
        huntingGroundBuilders.add(new HuntingGroundBuilder(huntinggroundname, world));
        return true;
    }

    public void CheckEntity(Entity e, boolean player)
    {
        if (player)
        {
            Player p = (Player)e;
            for (HuntingGround hg: huntingGrounds)
            {
                for (Player pp :hg.hggroup.group)
                {
                    if (p.getUniqueId() == pp.getUniqueId())
                    {
                        hg.reduceGroupLive();
                        return;
                    }
                }
            }
        }
        else
        {
            if (e instanceof Creature)
            {
                for (HuntingGround hg: huntingGrounds)
                {
                    hg.clearEnemyList();
                }
            }

        }
    }
}
