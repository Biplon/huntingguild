package main.java.huntingground.struct;

public class Wave
{
    public final int waveid;

    public final double waveprecountdown;

    public final WaveMonster[] wavemonster;

    public Wave(int waveid, double waveprecountdown, WaveMonster[] wavemonster)
    {
        this.waveid = waveid;
        this.waveprecountdown = waveprecountdown;
        this.wavemonster = wavemonster;
    }

}
