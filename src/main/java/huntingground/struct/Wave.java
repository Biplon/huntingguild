package main.java.huntingground.struct;

import java.util.ArrayList;

public class Wave
{
    public final String waveid;

    public final double waveprecountdown;

    public final ArrayList<WaveMonster> wavemonsters = new ArrayList();

    public Wave(String waveid, double waveprecountdown)
    {
        this.waveid = waveid;
        this.waveprecountdown = waveprecountdown;
    }

    public void addWaveMonster(WaveMonster wavemonster)
    {
        wavemonsters.add(wavemonster);
    }

    public void removeWaveMonster(WaveMonster wavemonster)
    {
        wavemonsters.remove(wavemonster);
    }

    @Override
    public String toString()
    {
        return "Wave{" +
                "waveid=" + waveid +
                ", waveprecountdown=" + waveprecountdown +
                '}';
    }
}
