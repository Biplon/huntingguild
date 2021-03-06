package hg.java.struct;

public class WaveMonster
{
    public final String mobname;

    public final int amount;

    public final Spawnpoint sp;

    public WaveMonster(String mobname, int amount, Spawnpoint sp)
    {
        this.mobname = mobname;
        this.amount = amount;
        this.sp = sp;
    }

    @Override
    public String toString()
    {
        return "WaveMonster{" +
                "mobname='" + mobname + '\'' +
                ", amount=" + amount +
                ", sp=" + sp +
                '}';
    }
}
