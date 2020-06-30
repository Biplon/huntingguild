package main.java;

import main.java.command.*;
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
        new GroupManager();
        new ConfigManager();
        new HuntingGroundConfigManager();
        new HuntingGroundManager();
        try
        {

            ConfigManager.loadConfig();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.getCommand("editcommands").setExecutor(new CommandHuntingGroundCommands());
        this.getCommand("editgrouplive").setExecutor(new CommandHuntingGroundGroupLive());
        this.getCommand("createhg").setExecutor(new CommandHuntingGroundCreate());
        this.getCommand("clearinv").setExecutor(new CommandClearInventory());
        this.getCommand("owninv").setExecutor(new CommandPlayerOwnInventory());
        this.getCommand("setmsp").setExecutor(new CommandSetMobSpawnpoint());
        this.getCommand("setwave").setExecutor(new CommandWave());
        this.getCommand("setwavemo").setExecutor(new CommandWaveMonster());
        this.getCommand("creategfhg").setExecutor(new CommandCreateGroupForHG());
        this.getCommand("hgsave").setExecutor(new CommandSaveHG());
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
