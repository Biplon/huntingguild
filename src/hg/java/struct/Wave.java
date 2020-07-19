package hg.java.struct;

import java.util.ArrayList;

public class Wave
{
    public final int waveid;

    public final double waveprecountdown;

    public final boolean autostart;

    public final ArrayList<WaveMonster> waveMonsters = new ArrayList();

    public Wave(int waveid, double waveprecountdown, boolean autostart)
    {
        this.waveid = waveid;
        this.waveprecountdown = waveprecountdown;
        this.autostart = autostart;
    }

    public void addWaveMonster(WaveMonster wavemonster)
    {
        waveMonsters.add(wavemonster);
    }

    @Override
    public String toString()
    {
        return "Wave{" +
                "waveid=" + waveid +
                ", waveprecountdown=" + waveprecountdown +
                ", autostart=" + autostart +
                '}';
    }
}
