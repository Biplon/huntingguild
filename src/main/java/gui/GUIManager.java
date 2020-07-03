package main.java.gui;

import main.java.HuntingGuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;

import java.io.File;

public class GUIManager
{
    public static GUIManager instance;

    public HGGUI joingui;

    public HGGUI leavegui;

    public HGGUI editorgui;

    public GUIManager()
    {
        instance = this;
        createGUIs();
    }

    public static GUIManager getInstance()
    {
        return instance;
    }

    private void createGUIs()
    {

        joingui = new HGGUI("Join hunting ground",45);
        joingui.initializeItems();

        leavegui = new HGGUI("Leave hunting ground",27);
    //    leavegui.initializeItems();

        editorgui = new HGGUI("Hunting ground editor",54);
     //   editorgui.initializeItems();


    }


}
