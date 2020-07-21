package hg.java;

import hg.java.command.*;
import hg.java.config.ConfigManager;
import hg.java.event.InventoryClick;
import hg.java.event.OnEntityGetDamage;
import hg.java.event.OnPlayerQuit;
import hg.java.huntingground.HuntingGroundManager;
import hg.java.playermanager.PlayerVisitInstanceManager;
import hg.java.event.OnEntityDeath;
import hg.java.gui.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HuntingGuild extends JavaPlugin
{
    static HuntingGuild instance;

    public void onEnable()
    {
        instance = this;
        new ConfigManager();
        new HuntingGroundManager();
        try
        {
            ConfigManager.loadConfig();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[HuntingGuild] register commands!");
        regCommands();
        Bukkit.getLogger().info("[HuntingGuild] commands registered!");
        Bukkit.getLogger().info("[HuntingGuild] load hunting grounds!");
        new PlayerVisitInstanceManager();
        HuntingGroundManager.getInstance().loadHuntingGrounds();
        Bukkit.getLogger().info("[HuntingGuild] hunting grounds loaded!");
        regEvents();
        new GUIManager();
        Bukkit.getLogger().info("[HuntingGuild] has been enabled!");
    }

    private void regCommands()
    {
        this.getCommand("hgeditcommands").setExecutor(new CommandHuntingGroundCommands());
        this.getCommand("hgeditgrouplive").setExecutor(new CommandHuntingGroundGroupLive());
        this.getCommand("hgcreate").setExecutor(new CommandHuntingGroundCreate());
        this.getCommand("hgowninv").setExecutor(new CommandPlayerOwnInventory());
        this.getCommand("hgsetmsp").setExecutor(new CommandSetMobSpawnpoint());
        this.getCommand("hgsetpsp").setExecutor(new CommandSetPlayerSpawnpoint());
        this.getCommand("hgsetwave").setExecutor(new CommandWave());
        this.getCommand("hgsetwavemo").setExecutor(new CommandWaveMonster());
        this.getCommand("hgcreateg").setExecutor(new CommandCreateGroupForHG());
        this.getCommand("hgsave").setExecutor(new CommandSaveHG());
        this.getCommand("hgmode").setExecutor(new CommandChangeHGMode());
        this.getCommand("hgstartwave").setExecutor(new CommandStartWave());
        this.getCommand("hgjoin").setExecutor(new CommandJoinHG());
        this.getCommand("dujoin").setExecutor(new CommandJoinDU());
        this.getCommand("hgleave").setExecutor(new CommandLeaveHG());
        this.getCommand("hgplready").setExecutor(new CommandSetPlayerReady());
        this.getCommand("hgchangerespawn").setExecutor(new CommandChangeHGPlayerSpawnPoint());
        this.getCommand("hgstartreadycheck").setExecutor(new CommandStartReadyCheck());
        this.getCommand("hgsetvisits").setExecutor(new CommandSetVisitsPerHour());
        this.getCommand("hgload").setExecutor(new CommandLoadHG());
    }

    private void regEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnEntityDeath(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new OnPlayerQuit(), this);
        pm.registerEvents(new OnEntityGetDamage(), this);
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
