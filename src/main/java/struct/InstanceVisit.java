package main.java.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstanceVisit
{
    public final String instancename;

    private final List<UUID> visitors = new ArrayList<>();

    public InstanceVisit(String instancename)
    {
        this.instancename = instancename;
    }

    public void addVisitor(UUID pid)
    {
        visitors.add(pid);
    }

    public int getVisits(UUID pid)
    {
        int count = 0;
        for (UUID id: visitors)
        {
            if (id == pid)
            {
                count++;
            }
        }
        return count;
    }

    public void clearVisitors()
    {
        visitors.clear();
    }
}
