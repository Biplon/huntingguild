package main.java.huntingground;

import main.java.config.ConfigManager;
import main.java.group.Group;
import main.java.huntingground.struct.Spawnpoint;
import main.java.huntingground.struct.Wave;
import main.java.huntingground.struct.WaveMonster;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;


public class HuntingGround
{
    public final String huntinggroundname;

    public final String world;

    public Group hggroup;

    private final ArrayList<Spawnpoint> spawnpoints = new ArrayList();

    private final ArrayList<Wave> waves = new ArrayList();

    public int grouplives;
    public int grouplivescurrent;

    private final ArrayList<String> startcommands = new ArrayList();

    private final ArrayList<String> huntinggroundlosecommands = new ArrayList();

    private final ArrayList<String> huntinggroundwincommands = new ArrayList();

    public final ArrayList<Creature> enemylist = new ArrayList();

    public boolean clearinventory;

    public boolean playerowninventory;

    public boolean isinuse = false;

    private boolean iswaveactive = false;

    private int wavecount;

    public HuntingGround(String configpfad)
    {
        File f = new File(configpfad);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);


        huntinggroundname = cfg.getString("general.huntinggroundname");
        world = cfg.getString("general.world");
        playerowninventory = cfg.getBoolean("inventory.keepplayerinventory");
        clearinventory = cfg.getBoolean("inventory.clearplayerinventoryonexit");
        hggroup = new Group(cfg.getInt("group.maxplayer"));
        grouplives = cfg.getInt("group.grouplives");

        boolean isnext = true;
        int count = 0;
        while (isnext)
        {
            if (cfg.getString("commands.playerjoin." + count) != null)
            {
                startcommands.add(cfg.getString("commands.playerjoin." + count));
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
            if (cfg.getString("commands.playerleavewin." + count) != null)
            {
                huntinggroundwincommands.add(cfg.getString("commands.playerleavewin." + count));
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
            if (cfg.getString("commands.playerleavelose." + count) != null)
            {
                huntinggroundlosecommands.add(cfg.getString("commands.playerleavelose." + count));
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
            if (cfg.getString("spawnpoints." + count + ".name") != null)
            {
                coords = cfg.getString("spawnpoints." + count + ".coords").split(",");
                spawnpoints.add(new Spawnpoint(cfg.getString("spawnpoints." + count + ".name"), Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
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
            if (cfg.getString("waves." + count + ".id") != null)
            {
                waves.add(new Wave(cfg.getString("waves." + count + ".id"), Double.parseDouble(Objects.requireNonNull(cfg.getString("waves." + count + ".cooldown"))), Boolean.parseBoolean(Objects.requireNonNull(cfg.getString("waves." + count + ".autostart")))));
                while (isnext2)
                {
                    if (cfg.getString("waves." + count + ".monster." + count2 + ".name") != null)
                    {
                        waves.get(count).addWaveMonster(new WaveMonster(cfg.getString("waves." + count + ".monster." + count2 + ".name"), cfg.getInt("waves." + count + ".monster." + count2 + ".amount"), getSpawnpoint(cfg.getString("waves." + count + ".monster." + count2 + ".spawnpoint"))));
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

    private Spawnpoint getSpawnpoint(String name)
    {
        for (Spawnpoint sp : spawnpoints)
        {
            if (sp.spawnpointname.equals(name))
            {
                return sp;
            }
        }
        return null;
    }

    public boolean starthuntingground()
    {
        if (hggroup.isFull())
        {
            isinuse = true;
            if (!playerowninventory)
            {
                hggroup.saveInventory();
            }
            for (Player p : hggroup.group)
            {
                if (!playerowninventory)
                {
                    p.getInventory().clear();
                }
                for (String s : startcommands)
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName()));
                }
            }
            grouplivescurrent = grouplives;
            return true;
        }
        return false;
    }


    public boolean endhuntinground(boolean win)
    {
        if (!playerowninventory)
        {
            hggroup.restoreInventory();
        }
        for (Player p : hggroup.group)
        {
            if (win)
            {
                for (String s : huntinggroundwincommands)
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName()));
                }
            }
            else
            {
                for (String s : huntinggroundlosecommands)
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName()));
                }
            }

        }
        if (win)
        {
            sendMessage("win");
        }
        else
        {
            sendMessage("lose");
        }
        isinuse = false;
        wavecount = 0;
        iswaveactive = false;
        hggroup.clearGroup();
        sendMessage("you leaved the hunting ground group");
        return true;
    }

    public void reduceGroupLive()
    {
        grouplivescurrent--;
        if (grouplivescurrent <= 0)
        {
            endhuntinground(false);
        }
    }

    Map<String, String> data = new HashMap<String, String>();

    public boolean startWave()
    {
        if (!iswaveactive)
        {
            for (WaveMonster wm : waves.get(wavecount).wavemonsters)
            {

                // "mo lspawn ${type} ${number} ${x} ${y} ${z} ${world}"

                data.put("type", wm.mobname);
                data.put("number", "" + wm.amount);
                data.put("x", "" + wm.sp.posx);
                data.put("y", "" + wm.sp.posy);
                data.put("z", "" + wm.sp.posz);
                data.put("world", world);
                String formattedString = StrSubstitutor.replace(ConfigManager.spawncommand, data);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedString);
                data.clear();
                wavecount++;
            }
            GetEnemys();
            iswaveactive = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    private void GetEnemys()
    {
        for (Spawnpoint sp : spawnpoints)
        {
            Location EntityArea = new Location(Bukkit.getWorld(world), sp.posx, sp.posy, sp.posz);
            List<Entity> nearbyEntities = (List<Entity>) EntityArea.getWorld().getNearbyEntities(EntityArea, 15, 15, 15);
            for (Entity e : nearbyEntities)
            {
                if (e instanceof Creature)
                {
                    enemylist.add((Creature) e);
                }
            }
        }
    }

    public void clearEnemyList()
    {
        enemylist.removeAll(Collections.singleton(null));
        sendMessage("lululu");
        checkIsWaveClear();
    }


    public void checkIsWaveClear()
    {
        if (enemylist.size() == 0)
        {
            sendMessage("wave clear");
            if (wavecount >= waves.size())
            {
                sendMessage("hg clear");
                endhuntinground(true);
            }
            else if (waves.get(wavecount).autostart)
            {
                sendMessage("next wave");
                startWave();
            }
            iswaveactive = false;

        }
    }

    private void sendMessage(String msg)
    {
        for (Player p : hggroup.group)
        {
            if (p != null)
            {
                p.sendMessage(msg);
            }
        }
    }

}
