package main.java.config;

import main.java.HuntingGuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HuntingGroundConfigManager
{
    static HuntingGroundConfigManager instance;

    public HuntingGroundConfigManager()
    {
            instance = this;
    }

    public static HuntingGroundConfigManager getInstance()
    {
        return instance;
    }

    public void loadHuntingGrounds()
    {

    }

    public boolean createHuntingGround(String worldname,String huntinggroundname)
    {
        File f = new File(HuntingGuild.getInstance().getDataFolder()+"/huntinggrounds/", huntinggroundname+".yml");
        if (!f.exists())
        {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("general.huntinggroundname", huntinggroundname);
            cfg.set("general.world", worldname);

            try
            {
                cfg.save(f);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return true;
        }
        return false;

    }

    public boolean removeHuntingGround(String world,String huntinggroundname)
    {
        return false;
    }

    public boolean removeHuntingGroundvalue(Configvalue configvalue,String value)
    {
        return false;
    }

    public boolean editHuntingGroundvalue(Configvalue configvalue,String valueold, String valuenew)
    {
        return false;
    }

    public boolean addHuntingGroundvalue(Configvalue configvalue,String[] values)
    {
        File f = new File(HuntingGuild.getInstance().getDataFolder()+"/huntinggrounds/", "FILENAME_HERE.yml");

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        switch (configvalue)
        {
            case keepplayerinventory:
                cfg.set("inventory.keepplayerinventory", values[0]);
            case clearplayerinventoryonexit:
                cfg.set("inventory.clearplayerinventoryonexit", values[0]);
            case maxplayer:
                cfg.set("group.maxplayer", values[0]);
            case grouplives:
                cfg.set("group.grouplives", values[0]);
            case commandplayerjoinhuntingground:
                for (int i = 0; i < 100; i++)
                {
                    if (!cfg.contains("commands.playerjoinhuntingground" +i))
                    {
                        cfg.set("commands.playerjoinhuntingground"+i, values[0]);
                        break;
                    }
                }
                return false;
            case commandplayerleavehuntingground001:
                for (int i = 0; i < 100; i++)
                {
                    if (!cfg.contains("commands.playerleavehuntingground" +i))
                    {
                        cfg.set("commands.playerleavehuntingground"+i, values[0]);
                        break;
                    }
                }
                return false;
            case spawnpoints:
                for (int i = 0; i < 100; i++)
                {
                    if (!cfg.contains("spawnpoints." +i))
                    {
                        cfg.set("spawnpoints."+i +".name", values[0]);
                        cfg.set("spawnpoints."+i +".cords", values[1]);
                        break;
                    }
                }
                return false;
            case waves:
                for (int i = 0; i < 100; i++)
                {
                    if (!cfg.contains("waves." +i))
                    {
                        cfg.set("waves."+i +".countdown", values[0]);
                        break;
                    }
                }
                return false;
            case wavemob:
                for (int i = 0; i < 100; i++)
                {
                    for (int j = 0; j < 100; j++)
                    {
                        if (!cfg.contains("waves." +i+".mob"+j))
                        {
                            cfg.set("waves." +i+".mob"+j+".name", values[0]);
                            cfg.set("waves." +i+".mob"+j+".spawnpoint", values[1]);
                            cfg.set("waves." +i+".mob"+j+".count", values[2]);
                            break;
                        }
                    }

                }
                return false;
        }
        try
        {
            cfg.save(f);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

       // String s = cfg.getString("");
        return false;
    }
}
