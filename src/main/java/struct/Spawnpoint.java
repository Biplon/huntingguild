package main.java.struct;

import org.bukkit.Location;
import org.bukkit.World;

public class Spawnpoint
{
    public final int id;

    public  final Location loc;

    public Spawnpoint(int id, World world, int posx, int posy, int posz)
    {
        this.id = id;
        loc = new Location(world,posx,posy,posz);
    }

    @Override
    public String toString()
    {
        return "Spawnpoint{" +
                "id=" + id +
                ", loc=" + loc +
                '}';
    }
}
