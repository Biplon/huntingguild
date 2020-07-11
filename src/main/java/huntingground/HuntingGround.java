package main.java.huntingground;

import main.java.HuntingGuild;
import main.java.config.ConfigManager;
import main.java.group.Group;
import main.java.playermanager.PlayerVisitInstanceManager;
import main.java.struct.Spawnpoint;
import main.java.struct.Wave;
import main.java.struct.WaveMonster;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

    private final boolean modeDungeon;

    private final ArrayList<String> startcommands = new ArrayList();

    private final ArrayList<String> huntinggroundlosecommands = new ArrayList();

    private final ArrayList<String> huntinggroundwincommands = new ArrayList();

    private final ArrayList<Spawnpoint> huntinggroundplayerspawns = new ArrayList();

    public Spawnpoint activePlayerSpawnpoint;

    public ArrayList<Creature> enemylist = new ArrayList();

    public boolean playerowninventory;

    public boolean isinuse = false;

    public boolean iswaveactive = false;

    private int wavecount;

    public int visitsPerHour;

    public HuntingGround(String configpfad)
    {
        File f = new File(configpfad);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        huntinggroundname = cfg.getString("general.huntinggroundname");
        PlayerVisitInstanceManager.getInstance().addInstance(huntinggroundname);
        world = cfg.getString("general.world");
        World w = Bukkit.getWorld(world);
        modeDungeon = cfg.getBoolean("general.dungeonmode");
        playerowninventory = cfg.getBoolean("inventory.keepplayerinventory");
        hggroup = new Group(cfg.getInt("group.maxplayer"),cfg.getInt("group.minplayer") == 0 ? cfg.getInt("group.maxplayer") : cfg.getInt("group.minplayer"), cfg.getBoolean("group.leavedeath"), this);
        grouplives = cfg.getInt("group.grouplives");
        grouplivescurrent = grouplives;
        visitsPerHour  = cfg.getInt("group.visitsperhour");
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
                spawnpoints.add(new Spawnpoint(count, w, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
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
                coords = cfg.getString("playerspawnpoints." + count + ".coords").split(",");
                huntinggroundplayerspawns.add(new Spawnpoint(count, w, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                count++;
            }
            else
            {
                isnext = false;
            }
        }
        activePlayerSpawnpoint = huntinggroundplayerspawns.get(0);
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

    public boolean isDungeonMode()
    {
        return modeDungeon;
    }

    public void teleportPlayerToHG()
    {
        for (Player p : hggroup.group)
        {
            if (p != null)
            {
                p.teleport(huntinggroundplayerspawns.get(0).loc);
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

    public void startHuntingGround()
    {
        for (Player p : hggroup.group)
        {
            if (!playerowninventory)
            {
                p.getInventory().clear();
            }
            for (String s : startcommands)
            {
                Bukkit.getScheduler().callSyncMethod(HuntingGuild.getInstance(), () ->Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName())));
            }
        }
        sendActionbarMessage("First wave in 10 sec");
        Bukkit.getScheduler().runTaskLaterAsynchronously(HuntingGuild.getInstance(), this::startWave, 200);
    }

    public boolean prepareStart()
    {
        if (hggroup.isFull())
        {
            isinuse = true;

            if (!playerowninventory)
            {
                hggroup.saveInventory();
            }
            hggroup.savePlayerloc();
            teleportPlayerToHG();

            Bukkit.getScheduler().runTaskLaterAsynchronously(HuntingGuild.getInstance(), this::startHuntingGround, 2);
            return true;
        }
        return false;
    }

    public void changePlayerRespawnPoint(int spawnid)
    {
        activePlayerSpawnpoint = huntinggroundplayerspawns.get(spawnid);
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
            PlayerVisitInstanceManager.getInstance().addPlayer(p.getUniqueId(),huntinggroundname);
        }
        if (win)
        {
            sendMessage("win");
        }
        else
        {
            sendMessage("lose");
        }
        resetHuntingGround();
    }

    public boolean canJoin(UUID pid)
    {
        if (PlayerVisitInstanceManager.getInstance().getVisits(pid,huntinggroundname) < visitsPerHour || visitsPerHour == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void resetHuntingGround()
    {
        isinuse = false;
        wavecount = 0;
        iswaveactive = false;
        grouplivescurrent = grouplives;
        activePlayerSpawnpoint = huntinggroundplayerspawns.get(0);
        hggroup.clearGroup();
    }

    public void reduceGroupLive(Player p)
    {
        if (grouplives >0)
        {
            grouplivescurrent--;
            if (grouplivescurrent <= 0)
            {
                endHuntingGround(false);
            }
        }
        if (hggroup.playerLeaveByDead)
        {
            hggroup.removePlayer(p,false);
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
                data.put("number", "" + wm.amount);
                data.put("x", "" + wm.sp.loc.getBlockX());
                data.put("y", "" + wm.sp.loc.getBlockY());
                data.put("z", "" + wm.sp.loc.getBlockZ());
                data.put("world", world);
                String formattedString = StrSubstitutor.replace(ConfigManager.spawncommand, data);
                Bukkit.getScheduler().callSyncMethod(HuntingGuild.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedString));
                data.clear();
            }
            wavecount++;
            Bukkit.getScheduler().callSyncMethod(HuntingGuild.getInstance(), this::getEnemys);
            iswaveactive = true;
            sendActionbarMessage("Wave started");
        }
    }

    public void initWaveStart()
    {
        if (waves.get(wavecount).autostart)
        {
            sendActionbarMessage("Wave start in " + waves.get(wavecount).waveprecountdown + " sec");
            Bukkit.getScheduler().runTaskLaterAsynchronously(HuntingGuild.getInstance(), this::startWave, (long) (waves.get(wavecount).waveprecountdown * 20));
        }
    }

    private boolean getEnemys()
    {
        for (Spawnpoint sp : spawnpoints)
        {
            List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(sp.loc.getWorld()).getNearbyEntities(sp.loc, 15, 15, 15);
            for (Entity e : nearbyEntities)
            {
                if (e instanceof Creature)
                {
                    enemylist.add((Creature) e);
                }
            }
        }
        return true;
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
            sendActionbarMessage("wave clear");
            if (wavecount >= waves.size())
            {
                sendActionbarMessage("hg clear");
                endHuntingGround(true);
            }
            else
            {
                initWaveStart();
            }
        }
    }

    public void sendMessageWithClickEvent(String clickabletext, String command,boolean leader)
    {
        TextComponent message = new TextComponent( clickabletext );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, command ) );
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

    public void sendActionbarMessage(String msg)
    {
        for (Player p : hggroup.group)
        {
            if (p != null)
            {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
            }
        }
    }
}
