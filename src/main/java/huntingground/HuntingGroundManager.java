package main.java.huntingground;

public class HuntingGroundManager
{
    static HuntingGroundManager instance;

    public HuntingGroundManager()
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

    public static HuntingGroundManager getInstance()
    {
        return instance;
    }


    public void createHuntingGrounds()
    {

    }
}
