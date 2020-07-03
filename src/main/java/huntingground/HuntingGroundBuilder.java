package main.java.huntingground;

import main.java.HuntingGuild;
import main.java.group.Group;
import main.java.huntingground.struct.Spawnpoint;
import main.java.huntingground.struct.Wave;
import main.java.huntingground.struct.WaveMonster;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    public final String world;

    public Group groupinhuntingground;

    private final ArrayList<Spawnpoint> mobspawnpoints = new ArrayList();

    private final ArrayList<Wave> waves = new ArrayList();

    public int grouplifes;

    private final ArrayList<String> equipcommands = new ArrayList();

    private final ArrayList<String> huntinggroundlosecommands = new ArrayList();

    private final ArrayList<String> huntinggroundwincommands = new ArrayList();

    private final ArrayList<Spawnpoint> huntinggroundplayerspawns = new ArrayList();

    public boolean clearinventory;

    public boolean playerowninventory;


    public HuntingGroundBuilder(String huntinggroundname, String world)
    {
        this.huntinggroundname = huntinggroundname;
        this.world = world;
    }

    public HuntingGroundBuilder(String pfad)
    {
        File f = new File(pfad);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);


        huntinggroundname = cfg.getString("general.huntinggroundname");
        world = cfg.getString("general.world");
        playerowninventory =  cfg.getBoolean("inventory.keepplayerinventory");
        clearinventory =  cfg.getBoolean("inventory.clearplayerinventoryonexit");
        groupinhuntingground = new Group(cfg.getInt("group.maxplayer"));
        grouplifes = cfg.getInt("group.grouplives");

        boolean isnext = true;
        int count = 0;
        while (isnext)
        {
            if (cfg.getString("commands.playerjoin."+count) !=null)
            {
                equipcommands.add(cfg.getString("commands.playerjoin."+count));
                count++;
            }
            else
            {
                isnext = false;
            }
        }
        isnext = true;
        count = 0;
        while (isnext)
        {
            if (cfg.getString("commands.playerleavewin."+count) !=null)
            {
                huntinggroundwincommands.add(cfg.getString("commands.playerleavewin."+count));
                count++;
            }
            else
            {
                isnext = false;
            }
        }

        isnext = true;
        count = 0;
        while (isnext)
        {
            if (cfg.getString("commands.playerleavelose."+count) !=null)
            {
                huntinggroundlosecommands.add(cfg.getString("commands.playerleavelose."+count));
                count++;
            }
            else
            {
                isnext = false;
            }
        }

        isnext = true;
        count = 0;
        String[] coords;

        while (isnext)
        {
            if (cfg.getString("spawnpoints."+count+".name") !=null)
            {
                coords = cfg.getString("spawnpoints."+count+".coords").split(",");
                mobspawnpoints.add(new Spawnpoint(count,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
                count++;
            }
            else
            {
                isnext = false;
            }
        }

        isnext = true;
        count = 0;
        while (isnext)
        {
            if (cfg.getString("playerspawnpoints."+count+".name") !=null)
            {
                coords = cfg.getString("playerspawnpoints."+count+".coords").split(",");
                huntinggroundplayerspawns.add(new Spawnpoint(count,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
                count++;
            }
            else
            {
                isnext = false;
            }
        }



        isnext = true;
        count = 0;

        boolean isnext2 = true;
        int count2 = 0;
        while (isnext)
        {
            if (cfg.getString("waves."+count+".cooldown") !=null)
            {
                waves.add(new Wave(count,Double.parseDouble(Objects.requireNonNull(cfg.getString("waves." + count + ".cooldown"))),Boolean.parseBoolean(Objects.requireNonNull(cfg.getString("waves." + count + ".autostart")))));
                while (isnext2)
                {
                    if (cfg.getString("waves."+count+".monster."+count2+".name") !=null)
                    {
                        waves.get(count).addWaveMonster(new WaveMonster(cfg.getString("waves."+count+".monster."+count2+".name"),cfg.getInt("waves."+count+".monster."+count2+".amount"), getMobSpawnpoint(cfg.getInt("waves."+count+".monster."+count2+".spawnpoint"))));
                        count2++;
                    }
                    else
                    {
                        isnext2 = false;
                    }
                }
                count++;
            }
            else
            {
                isnext = false;
            }
        }
    }

    private Spawnpoint getMobSpawnpoint(int index)
    {
        return mobspawnpoints.size() >= index ? mobspawnpoints.get(index) : null;
    }

    private Spawnpoint getPlayerSpawnpoint(int index)
    {
        return huntinggroundplayerspawns.size() >= index ? huntinggroundplayerspawns.get(index) : null;
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

    public ArrayList<Spawnpoint> getMobspawnpoints()
    {
        return mobspawnpoints;
    }

    public ArrayList<Spawnpoint> getPlayerSpawnpoints()
    {
        return huntinggroundplayerspawns;
    }

    public ArrayList<Wave> getWaves()
    {
        return waves;
    }

    public String getHuntinggroundname()
    {
        return huntinggroundname;
    }

    public String getWorld()
    {
        return world;
    }

    public int getGroupinhuntingground()
    {
        return groupinhuntingground.group.length;
    }

    public boolean addWaveMonstertoWave(int waveid,String mobname,String amout,int spawnpointindex)
    {
        return waves.get(waveid).wavemonsters.add(new WaveMonster(mobname,Integer.parseInt(amout), mobspawnpoints.get(spawnpointindex)));
    }

    public boolean removeWaveMonsterfromWave(int waveid,String mobname)
    {
                return waves.get(waveid).wavemonsters.removeIf(n -> (n.mobname == mobname));
    }

    public String[] getWavemonsterfromWave(int waveid)
    {

                String[] item = new String[waves.get(waveid).wavemonsters.size()];
                for (int i = 0; i < item.length; i++)
                {
                    item[i] =  waves.get(waveid).wavemonsters.get(i).toString();
                }
                return item;

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

    public boolean addWave(int id,String cooldown,boolean autostart)
    {
        if ( waves.add(new Wave(id,Double.parseDouble(cooldown),autostart)))
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
        mobspawnpoints.add(sp);
        return true;
    }

    public boolean addPlayerSpawnpoint(Spawnpoint sp)
    {
        huntinggroundplayerspawns.add(sp);
        return true;
    }

    public boolean removeWave(int index)
    {
        return waves.remove(index) != null;
    }

    public boolean removeMobSpawnpoint(int index)
    {
        return mobspawnpoints.remove(index) != null;
    }

    public boolean removePlayerSpawnpoint(int index)
    {
        return huntinggroundplayerspawns.remove(index) != null;
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
        File f = new File(HuntingGuild.getInstance().getDataFolder() + "/huntinggrounds/buildmode/", huntinggroundname + ".yml");
        if (f.exists())
        {
            f.delete();
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        cfg.set("general.huntinggroundname",huntinggroundname);
        cfg.set("general.world",world);
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
        for (int i = 0; i < mobspawnpoints.size(); i++)
        {
            cfg.set("spawnpoints."+i+".coords" , mobspawnpoints.get(i).posx +","+ mobspawnpoints.get(i).posy +","+ mobspawnpoints.get(i).posz);
        }
        for (int i = 0; i < huntinggroundplayerspawns.size(); i++)
        {
            cfg.set("playerspawnpoints."+i+".coords" , mobspawnpoints.get(i).posx +","+ mobspawnpoints.get(i).posy +","+ mobspawnpoints.get(i).posz);
        }
        for (int i = 0; i < waves.size(); i++)
        {
            cfg.set("waves."+i+".cooldown" ,waves.get(i).waveprecountdown);
            cfg.set("waves."+i+".autostart" ,waves.get(i).autostart);
            for (int j = 0; j < waves.get(i).wavemonsters.size(); j++)
            {
                cfg.set("waves."+i+".monster."+j+".name" ,waves.get(i).wavemonsters.get(j).mobname);
                cfg.set("waves."+i+".monster."+j+".amount" ,waves.get(i).wavemonsters.get(j).amount);
                cfg.set("waves."+i+".monster."+j+".spawnpoint" ,waves.get(i).wavemonsters.get(j).sp.id);
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
