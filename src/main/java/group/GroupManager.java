package main.java.group;

public class GroupManager
{
    static GroupManager instance;

    public GroupManager()
    {
        if(instance != null)
        {
            instance = this;
        }
        else
        {
            return;
        }
    }


    public static GroupManager getInstance()
    {
        return instance;
    }
}
