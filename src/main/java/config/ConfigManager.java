package main.java.config;

import main.java.HuntingGuild;

import java.io.File;

public class ConfigManager
{
    static ConfigManager instance;
    public static String spawncommand = "mo lspawn ${type} ${number} ${x} ${y} ${z} ${world}";
    //public static String spawncommand = "summon ${type} ${x} ${y} ${z}";

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
        }
        catch (Exception e)
        {
            HuntingGuild.getInstance().getLogger().severe("Could not load the config! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
