package main.java.huntingground.struct;

public class Spawnpoint
{
    public final String spawnpointname;

    public final int posx;

    public final int posy;

    public final int posz;

    public Spawnpoint(String spawnpointname,int posx,int posy, int posz)
    {
       this.spawnpointname = spawnpointname;
       this.posx = posx;
       this.posy = posy;
       this.posz = posz;
    }

    @Override
    public String toString()
    {
        return "Spawnpoint{" +
                "spawnpointname='" + spawnpointname + '\'' +
                ", posx=" + posx +
                ", posy=" + posy +
                ", posz=" + posz +
                '}';
    }
}
