package main.java.huntingground;

import main.java.group.Group;
import main.java.huntingground.struct.Spawnpoint;
import main.java.huntingground.struct.Wave;
import org.bukkit.World;

public class HuntingGround
{
    public String huntinggroundname;

    public Group groupinhuntingground;

    public Spawnpoint[] spawnpoints;

    public Wave[] waves;

    public World world;

    public int grouplifes;

    public boolean clearinventory;

    public boolean playerowninventory;

    public String[] equipcommands;

    public String[] huntinggroundendcommands;

    public HuntingGround(String huntinggroundname, Group groupinhuntingground, Spawnpoint[] spawnpoints, Wave[] waves, World world, int grouplifes, boolean clearinventory, boolean playerowninventory, String[] equipcommands, String[] huntinggroundendcommands)
    {
        this.huntinggroundname = huntinggroundname;
        this.groupinhuntingground = groupinhuntingground;
        this.spawnpoints = spawnpoints;
        this.waves = waves;
        this.world = world;
        this.grouplifes = grouplifes;
        this.clearinventory = clearinventory;
        this.playerowninventory = playerowninventory;
        this.equipcommands = equipcommands;
        this.huntinggroundendcommands = huntinggroundendcommands;
    }

}
