package main.java.huntingground;

import main.java.HuntingGuild;
import main.java.group.Group;
import main.java.huntingground.struct.Spawnpoint;
import main.java.huntingground.struct.Wave;
import main.java.huntingground.struct.WaveMonster;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HuntingGroundBuilder
{
    /*
    fin:
    huntinggroundname
    world
    equipcommands
    huntinggroundlosecommands
    huntinggroundwincommands
    grouplifes
    clearinventory
    playerowninventory
    spawnpoints
    waves
    wavemonster
    group



    save
     */

    public final String huntinggroundname;

    public final World world;

    public Group groupinhuntingground;

    private final ArrayList<Spawnpoint> spawnpoints = new ArrayList();

    private final ArrayList<Wave> waves = new ArrayList();

    public int grouplifes;

    private final ArrayList<String> equipcommands = new ArrayList();

    private final ArrayList<String> huntinggroundlosecommands = new ArrayList();

    private final ArrayList<String> huntinggroundwincommands = new ArrayList();

    public boolean clearinventory;

    public boolean playerowninventory;


    public HuntingGroundBuilder(String huntinggroundname, String world)
    {
        this.huntinggroundname = huntinggroundname;
        this.world = Bukkit.getServer().getWorld(world);
        ;
    }

    public int getGrouplifes()
    {
        return grouplifes;
    }

    public void setGrouplifes(int grouplifes)
    {
        this.grouplifes = grouplifes;
    }

    public boolean isClearinventory()
    {
        return clearinventory;
    }

    public void setClearinventory(boolean clearinventory)
    {
        this.clearinventory = clearinventory;
    }

    public boolean isPlayerowninventory()
    {
        return playerowninventory;
    }

    public void setPlayerowninventory(boolean playerowninventory)
    {
        this.playerowninventory = playerowninventory;
    }

    public ArrayList<String> getEquipcommands()
    {
        return equipcommands;
    }

    public ArrayList<String> getHuntinggroundwincommands()
    {
        return huntinggroundwincommands;
    }

    public ArrayList<String> getHuntinggroundlosecommands()
    {
        return huntinggroundlosecommands;
    }

    public ArrayList<Spawnpoint> getSpawnpoints()
    {
        return spawnpoints;
    }

    public ArrayList<Wave> getWaves()
    {
        return waves;
    }

    public String getHuntinggroundname()
    {
        return huntinggroundname;
    }

    public World getWorld()
    {
        return world;
    }

    public int getGroupinhuntingground()
    {
        return groupinhuntingground.group.length;
    }

    public boolean addWaveMonstertoWave(String waveid,String mobname,String amout,String Spawnpoint)
    {
        for (Wave w: waves)
        {
            if (w.waveid.equals(waveid))
            {
                for (Spawnpoint item:spawnpoints)
                {
                    if (item.spawnpointname.equals(Spawnpoint))
                    {
                        return w.wavemonsters.add(new WaveMonster(mobname,Integer.parseInt(amout),item));
                    }
                }
            }

        }
        return false;
    }

    public boolean removeWaveMonsterfromWave(String waveid,String mobname)
    {
        for (Wave w: waves)
        {
            if (w.waveid.equals(waveid))
            {
                return w.wavemonsters.removeIf(n -> (n.mobname == mobname));
            }

        }
        return false;
    }

    public String[] getWavemonsterfromWave(String waveid)
    {
        for (Wave w: waves)
        {
            if (w.waveid.equals(waveid))
            {
                String[] item = new String[w.wavemonsters.size()];
                for (int i = 0; i < item.length; i++)
                {
                    item[i] =  w.wavemonsters.get(i).toString();
                }
                return item;
            }

        }

        return null;
    }

    public boolean setGroupinhuntingground(String size)
    {
        try
        {
            this.groupinhuntingground = new Group(Integer.parseInt(size));
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public boolean addWave(String id,String cooldown)
    {
        if ( waves.add(new Wave(id,Double.parseDouble(cooldown))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addSpawnpoint(Spawnpoint sp)
    {
        spawnpoints.add(sp);
        return true;
    }

    public boolean removeWave(String number)
    {
        return waves.removeIf(n -> (n.waveid == number));
    }

    public boolean removeSpawnpoint(String name)
    {
        return spawnpoints.removeIf(n -> (n.spawnpointname == name));
    }

    public boolean addHuntinggroundwincommands(String command)
    {
        if (!huntinggroundwincommands.contains(command))
        {
            huntinggroundwincommands.add(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addHuntinggroundlosecommands(String command)
    {
        if (!huntinggroundlosecommands.contains(command))
        {
            huntinggroundlosecommands.add(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean addEquipcommands(String command)
    {
        if (!equipcommands.contains(command))
        {
            equipcommands.add(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeHuntinggroundwincommands(String command)
    {
        if (huntinggroundwincommands.contains(command))
        {
            huntinggroundwincommands.remove(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeHuntinggroundlosecommands(String command)
    {
        if (huntinggroundlosecommands.contains(command))
        {
            huntinggroundlosecommands.remove(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeEquipcommands(String command)
    {
        if (equipcommands.contains(command))
        {
            equipcommands.remove(command);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean saveHuntingGround()
    {
        File f = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/", huntinggroundname + ".yml");
        if (f.exists())
        {
            f.delete();
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        cfg.set("general.huntinggroundname",huntinggroundname);
        cfg.set("general.world",world.getName());
        cfg.set("inventory.keepplayerinventory",playerowninventory);
        cfg.set("inventory.clearplayerinventoryonexit",clearinventory);
        cfg.set("group.maxplayer",groupinhuntingground.group.length);
        cfg.set("group.grouplives",grouplifes);
        for (int i = 0; i < equipcommands.size(); i++)
        {
            cfg.set("commands.playerjoin."+i,equipcommands.get(i));
        }
        for (int i = 0; i < huntinggroundwincommands.size(); i++)
        {
            cfg.set("commands.playerleavewin."+i,huntinggroundwincommands.get(i));
        }
        for (int i = 0; i < huntinggroundlosecommands.size(); i++)
        {
            cfg.set("commands.playerleavelose."+i,huntinggroundlosecommands.get(i));
        }
        for (int i = 0; i < spawnpoints.size(); i++)
        {
            cfg.set("spawnpoints."+i+".name" ,spawnpoints.get(i).spawnpointname);
            cfg.set("spawnpoints."+i+".coords" ,spawnpoints.get(i).posx +","+spawnpoints.get(i).posy +","+spawnpoints.get(i).posz);
        }
        for (int i = 0; i < waves.size(); i++)
        {
            cfg.set("waves."+i+".id" ,waves.get(i).waveid);
            cfg.set("waves."+i+".cooldown" ,waves.get(i).waveprecountdown);
            for (int j = 0; j < waves.get(i).wavemonsters.size(); j++)
            {
                cfg.set("waves."+i+".monster."+j+".name" ,waves.get(i).wavemonsters.get(j).mobname);
                cfg.set("waves."+i+".monster."+j+".amount" ,waves.get(i).wavemonsters.get(j).amount);
                cfg.set("waves."+i+".monster."+j+".spawnpoint" ,waves.get(i).wavemonsters.get(j).sp.spawnpointname);
            }
        }
        try
        {
            cfg.save(f);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
