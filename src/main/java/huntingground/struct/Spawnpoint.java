package main.java.huntingground.struct;

public class Spawnpoint
{
    public final int id;

    public final int posx;

    public final int posy;

    public final int posz;

    public Spawnpoint(int id, int posx, int posy, int posz)
    {
        this.id = id;
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
    }

    @Override
    public String toString()
    {
        return "Spawnpoint{" +
                "  id=" + id +
                ", posx=" + posx +
                ", posy=" + posy +
                ", posz=" + posz +
                '}';
    }
}
