package main.java.config;

public class HuntingGroundConfigManager
{
    static HuntingGroundConfigManager instance;

    public HuntingGroundConfigManager()
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

    public static HuntingGroundConfigManager getInstance()
    {
        return instance;
    }

    public void loadHuntingGrounds()
    {

    }
}
