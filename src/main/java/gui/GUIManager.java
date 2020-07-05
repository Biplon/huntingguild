package main.java.gui;

import main.java.enums.HgGuis;

public class GUIManager
{
    public static GUIManager instance;

    public GUIManager()
    {
        instance = this;
    }

    public static GUIManager getInstance()
    {
        return instance;
    }

    public HGGUI getGUIInstance(HgGuis gui)
    {
        return new HGGUI(gui);
    }
}
