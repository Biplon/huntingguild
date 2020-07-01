package main.java.group;

public class GroupManager
{
    static GroupManager instance;

    public GroupManager()
    {

            instance = this;

    }


    public static GroupManager getInstance()
    {
        return instance;
    }
}
