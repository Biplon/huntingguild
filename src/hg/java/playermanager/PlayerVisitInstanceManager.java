package hg.java.playermanager;

import pts.java.PlayertoSql;
import hg.java.struct.InstanceVisit;
import org.bukkit.Bukkit;

import java.util.*;

public class PlayerVisitInstanceManager
{
    private static PlayerVisitInstanceManager instance;

    List<InstanceVisit> visits = new ArrayList<>();

    public PlayerVisitInstanceManager()
    {
        instance = this;
        runTask();
    }

    public static PlayerVisitInstanceManager getInstance()
    {
        return instance;
    }

    public void addPlayer(UUID pid, String hg)
    {
        for (InstanceVisit iv :visits)
        {
            if (iv.instancename.equals(hg))
            {
                iv.addVisitor(pid);
                return;
            }
        }
    }

    public int getVisits(UUID pid, String hg)
    {
        for (InstanceVisit iv :visits)
        {
            if (iv.instancename.equals(hg))
            {
                return iv.getVisits(pid);
            }
        }
        return 0;
    }

    public void addInstance(String instanceName)
    {
        visits.add(new InstanceVisit(instanceName));
    }

    private void runTask()
    {
        Bukkit.getScheduler().runTaskTimerAsynchronously(PlayertoSql.getInstance(), this::cleanVisits, 60 * 60 * 20L, 60 * 60 * 20L);
    }

    private void cleanVisits()
    {
        for (InstanceVisit iv :visits)
        {
           iv.clearVisitors();
        }
    }
}
