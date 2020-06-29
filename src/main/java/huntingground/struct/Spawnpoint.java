package main.java.huntingground.struct;

public class Spawnpoint
{
    public final String spawnpointname;

    public final double posx;

    public final double posy;

    public final double posz;

    public Spawnpoint(String spawnpointname,double posx,double posy, double posz)
    {
       this.spawnpointname = spawnpointname;
       this.posx = posx;
       this.posy = posy;
       this.posz = posz;
    }
}
