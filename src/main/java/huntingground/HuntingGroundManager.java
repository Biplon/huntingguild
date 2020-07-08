package main.java.huntingground;

import main.java.HuntingGuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HuntingGroundManager
{
    static HuntingGroundManager instance;

    private final ArrayList<HuntingGround> huntingGrounds = new ArrayList();
    private final ArrayList<HuntingGround> dungeons = new ArrayList();
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
            HuntingGround tmp = new HuntingGround(s);
            if (tmp.isDungeonMode())
            {
                dungeons.add(tmp);
            }
            else
            {
                huntingGrounds.add(tmp);
            }
        }
    }

    private static void search(final String pattern, final File folder, List<String> result)
    {
        for (final File f : Objects.requireNonNull(folder.listFiles()))
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

    public HuntingGround getHuntingground(String huntinggroundname)
    {
        for (HuntingGround item : huntingGrounds)
        {
            assert item.huntinggroundname != null;
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return item;
            }
        }
        for (HuntingGround item : dungeons)
        {
            assert item.huntinggroundname != null;
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return item;
            }
        }
        return null;
    }

    public boolean existHG(String huntinggroundname)
    {
        for (HuntingGround item : huntingGrounds)
        {
            assert item.huntinggroundname != null;
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return true;
            }
        }
        for (HuntingGround item : dungeons)
        {
            assert item.huntinggroundname != null;
            if (item.huntinggroundname.equals(huntinggroundname))
            {
                return true;
            }
        }
        return false;
    }

    public void setPlayerReady(Player p)
    {
        for (HuntingGround hg : huntingGrounds)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                if (hg.hggroup.setPlayerReady(p))
                {
                    if (hg.startHuntingGround())
                    {

                    }

                }
                return;
            }
        }
        for (HuntingGround hg : dungeons)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                if (hg.hggroup.setPlayerReady(p))
                {
                    if (hg.startHuntingGround())
                    {

                    }

                }
                return;
            }
        }
    }

    public void leavePlayer(Player p, boolean disconnect)
    {
        for (HuntingGround hg : huntingGrounds)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                hg.hggroup.removePlayer(p, disconnect);
                return;
            }
        }
        for (HuntingGround hg : dungeons)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                hg.hggroup.removePlayer(p, disconnect);
                return;
            }
        }
    }

    public HuntingGround getHuntingground(int index)
    {
        if (huntingGrounds.size() > index)
        {
            return huntingGrounds.get(index);
        }
        else
        {
            return null;
        }
    }

    public HuntingGround getDungeon(int index)
    {
        if (dungeons.size() > index)
        {
            return dungeons.get(index);
        }
        else
        {
            return null;
        }
    }

    public HuntingGroundBuilder getHuntingGroundBuilder(int index)
    {
        if (huntingGroundBuilders.size() > index)
        {
            return huntingGroundBuilders.get(index);
        }
        else
        {
            return null;
        }
    }

    public HuntingGroundBuilder getHuntingGroundBuilder(String huntinggroundname)
    {
        for (HuntingGroundBuilder item : huntingGroundBuilders)
        {
            assert item.huntinggroundname != null;
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
        if (getHuntingground(hgname) != null)
        {
            if (!getHuntingground(hgname).isinuse)
            {
                file = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml");
                if (file.renameTo(new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/" + hgname + ".yml")))
                {
                    if (getHuntingground(hgname).isDungeonMode())
                    {
                        dungeons.remove(getHuntingground(hgname));
                    }
                    else
                    {
                        huntingGrounds.remove(getHuntingground(hgname));
                    }
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
                    if (getHuntingGroundBuilder(hgname).modeDungeon)
                    {
                        dungeons.add(new HuntingGround(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml"));
                    }
                    else
                    {
                        huntingGrounds.add(new HuntingGround(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/active/" + hgname + ".yml"));
                    }

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

    public void CheckEntity(Entity e)
    {
        if (e instanceof Creature)
        {
            for (HuntingGround hg : huntingGrounds)
            {
                if (hg.isinuse && hg.iswaveactive)
                {
                    hg.clearEnemyList();
                }
            }
            for (HuntingGround hg : dungeons)
            {
                if (hg.isinuse && hg.iswaveactive)
                {
                    hg.clearEnemyList();
                }
            }
        }
    }

    public HuntingGround getHuntingGroundOfPlayer(Player p)
    {
        for (HuntingGround hg : huntingGrounds)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                return hg;
            }
        }
        for (HuntingGround hg : dungeons)
        {
            if (hg.hggroup.isPlayerInGroup(p))
            {
                return hg;
            }
        }
        return null;
    }

    public int getHuntingrounds()
    {
        return huntingGrounds.size();
    }

    public int getDungeons()
    {
        return dungeons.size();
    }
}
