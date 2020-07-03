package main.java.config;

import main.java.HuntingGuild;

import java.io.File;

public class ConfigManager
{
    static ConfigManager instance;
    //"mo lspawn ${type} ${number} ${x} ${y} ${z} ${world}";
    public static String spawncommand = "summon ${type} ${x} ${y} ${z}";
    //summon Skeleton ~ ~ ~ {SkeletonType:1}
    public ConfigManager()
    {

            instance = this;

    }


    public static ConfigManager getInstance()
    {
        return instance;
    }

    public static void loadConfig()
    {

        File configFile = new File("plugins" + File.separator + HuntingGuild.getInstance().getName() + File.separator + "config.yml");
        if (!configFile.exists())
        {
            HuntingGuild.getInstance().getLogger().info("Creating config ...");
            HuntingGuild.getInstance().saveDefaultConfig();
        }
        try
        {
            HuntingGuild.getInstance().getLogger().info("Loading the config ...");
            HuntingGuild.getInstance().getConfig().load(configFile);
            //spawncommand = getConfigvalueString("general.spawncommand");
        }
        catch (Exception e)
        {
            HuntingGuild.getInstance().getLogger().severe("Could not load the config! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static String getConfigvalueString(String value)
    {
        if (!HuntingGuild.getInstance().getConfig().contains(value))
        {
            HuntingGuild.getInstance().getLogger().severe("Value: " + value + " not found in config.yml of" + HuntingGuild.getInstance().getName());
            return "valuenotfound";
        }
        else
        {
            return HuntingGuild.getInstance().getConfig().getString(value);
        }
    }
}
