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

    public boolean saveHuntingGround()
    {
        File f = new File(HuntingGuild.getInstance().getDataFolder()+"/huntinggrounds/", "FILENAME_HERE.yml");

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        cfg.set("inventory.keepplayerinventory", "false");
        cfg.set("inventory.clearplayerinventoryonexit", "true");

        cfg.set("commands.playerjoinhuntingground001", "/lulululul");

        cfg.set("commands.playerleavehuntingground001", "/nanananana batman");

        cfg.set("group.maxplayer", "4");
        cfg.set("group.grouplives", "8");

        cfg.set("spawnpoints.001.name", "1");
        cfg.set("spawnpoints.001.cords", "0,0,0");

        cfg.set("waves.001.countdown", "0");
        cfg.set("waves.001.mob001", "schnitzelpirat");
        cfg.set("waves.001.mob001.spawnpoint", "schnitzelpirat");
        cfg.set("waves.001.mob001.count", "42");
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
