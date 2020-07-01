package main.java.event;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnEntityDeath implements Listener
{
    @EventHandler
    public void onDeath(final EntityDeathEvent event)
    {
        HuntingGroundManager.getInstance().CheckEntity(event.getEntity(), event.getEntity() instanceof Player);
    }
}
