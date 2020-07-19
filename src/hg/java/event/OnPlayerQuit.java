package hg.java.event;

import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit implements Listener
{
    @EventHandler
    public void onDisconnect(final PlayerQuitEvent event)
    {
        HuntingGroundManager.getInstance().leavePlayer(event.getPlayer(),true);
    }
}
