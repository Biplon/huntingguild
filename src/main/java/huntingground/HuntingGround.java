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

    private final ArrayList<Spawnpoint> huntinggroundplayerspawns = new ArrayList();

    public ArrayList<Creature> enemylist = new ArrayList();

    public boolean playerowninventory;

    public boolean isinuse = false;

    public boolean iswaveactive = false;

    private int wavecount;

    public HuntingGround(String configpfad)
    {
        File f = new File(configpfad);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        huntinggroundname = cfg.getString("general.huntinggroundname");
        world = cfg.getString("general.world");
        playerowninventory = cfg.getBoolean("inventory.keepplayerinventory");
        hggroup = new Group(cfg.getInt("group.maxplayer"), this);
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
            if (cfg.getString("spawnpoints." + count + ".coords") != null)
            {
                coords = cfg.getString("spawnpoints." + count + ".coords").split(",");
                spawnpoints.add(new Spawnpoint(count, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
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
            if (cfg.getString("playerspawnpoints." + count + ".coords") != null)
            {
                Bukkit.getLogger().info(cfg.getString("playerspawnpoints." + count + ".coords"));
                coords = cfg.getString("playerspawnpoints." + count + ".coords").split(",");
                huntinggroundplayerspawns.add(new Spawnpoint(count, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                count++;
            }
            else
            {
                isnext = false;
            }
        }

        isnext = true;
        count = 0;

        boolean isnext2;
        int count2 = 0;
        while (isnext)
        {
            isnext2 = true;
            count2 = 0;
            if (cfg.getString("waves." + count + ".cooldown") != null)
            {
                waves.add(new Wave(count, Double.parseDouble(Objects.requireNonNull(cfg.getString("waves." + count + ".cooldown"))), Boolean.parseBoolean(Objects.requireNonNull(cfg.getString("waves." + count + ".autostart")))));
                while (isnext2)
                {
                    if (cfg.getString("waves." + count + ".monster." + count2 + ".name") != null)
                    {
                        waves.get(count).addWaveMonster(new WaveMonster(cfg.getString("waves." + count + ".monster." + count2 + ".name"), cfg.getInt("waves." + count + ".monster." + count2 + ".amount"), getSpawnpoint(cfg.getInt("waves." + count + ".monster." + count2 + ".spawnpoint"))));
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

    private Spawnpoint getSpawnpoint(int index)
    {
        return spawnpoints.size() >= index ? spawnpoints.get(index) : null;
    }

    public void teleportPlayerToHG()
    {
        Location loc = new Location(Bukkit.getWorld(world), huntinggroundplayerspawns.get(0).posx, huntinggroundplayerspawns.get(0).posy, huntinggroundplayerspawns.get(0).posz);

        for (Player p : hggroup.group)
        {
            if (p != null)
            {
                p.teleport(loc);
            }
        }
    }

    public void teleportPlayersBack()
    {
        for (int i = 0; i < hggroup.group.length; i++)
        {
            if (hggroup.group[i] != null)
            {
                hggroup.group[i].teleport(hggroup.loc[i]);
            }
        }
    }

    public void teleportPlayerBack(Player p, Location loc)
    {
        p.teleport(loc);
    }

    public boolean startHuntingGround()
    {
        if (hggroup.isFull())
        {
            isinuse = true;

            hggroup.savePlayerloc();
            teleportPlayerToHG();

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

    public void endHuntingGround(boolean win)
    {
        teleportPlayersBack();
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
    }

    public void reduceGroupLive()
    {
        grouplivescurrent--;
        if (grouplivescurrent <= 0)
        {
            endHuntingGround(false);
        }
    }

    Map<String, String> data = new HashMap<String, String>();

    public void startWave()
    {
        if (!iswaveactive)
        {
            for (WaveMonster wm : waves.get(wavecount).waveMonsters)
            {
                // "mo lspawn ${type} ${number} ${x} ${y} ${z} ${world}"

                data.put("type", wm.mobname);
                //  data.put("number", "" + wm.amount);
                data.put("x", "" + wm.sp.posx);
                data.put("y", "" + wm.sp.posy);
                data.put("z", "" + wm.sp.posz);
                // data.put("world", world);
                String formattedString = StrSubstitutor.replace(ConfigManager.spawncommand, data);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedString);
                data.clear();
            }
            wavecount++;
            GetEnemys();
            iswaveactive = true;
        }
    }

    private void GetEnemys()
    {
        for (Spawnpoint sp : spawnpoints)
        {
            Location EntityArea = new Location(Bukkit.getWorld(world), sp.posx, sp.posy, sp.posz);
            List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(EntityArea.getWorld()).getNearbyEntities(EntityArea, 15, 15, 15);
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
        ArrayList<Creature> tmp = new ArrayList<>();
        checkIsWaveClear();
        for (Creature e : enemylist)
        {
            if (!e.isDead())
            {
                tmp.add(e);
            }
        }
        enemylist = tmp;
    }

    public void checkIsWaveClear()
    {
        if (enemylist.size() == 0 && iswaveactive)
        {
            iswaveactive = false;
            sendMessage("wave clear");
            if (wavecount >= waves.size())
            {
                sendMessage("hg clear");
                endHuntingGround(true);
            }
            else if (waves.get(wavecount).autostart)
            {
                sendMessage("next wave");
                startWave();
            }
        }
    }

    public void sendMessage(String msg)
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
