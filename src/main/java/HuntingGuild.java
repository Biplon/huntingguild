package main.java;

import main.java.config.ConfigManager;
import main.java.config.HuntingGroundConfigManager;
import main.java.group.GroupManager;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class HuntingGuild extends JavaPlugin
{
    static HuntingGuild instance;


    public void onEnable()
    {
        instance = this;
        try
        {
            new GroupManager();
            new ConfigManager();
            new HuntingGroundConfigManager();
            new HuntingGroundManager();
            ConfigManager.loadConfig();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[HuntingGuild] has been enabled!");
    }

    @Override
    public void onDisable()
    {
        HandlerList.unregisterAll(this);
        Bukkit.getLogger().info("[HuntingGuild] has been disabled!");
    }

    public static HuntingGuild getInstance()
    {
        return instance;
    }

}
