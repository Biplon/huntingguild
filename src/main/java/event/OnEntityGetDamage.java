package main.java.event;

import main.java.huntingground.HuntingGround;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class OnEntityGetDamage implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDmg(final EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player p = (Player) event.getEntity();
            if (p.getHealth() <= event.getFinalDamage())
            {
                HuntingGround hg = HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(p);
                if (hg != null)
                {
                    p.teleport(hg.activePlayerSpawnpoint.loc);
                    p.setHealth(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
                    p.setFoodLevel(20);
                    for (PotionEffect effect : p.getActivePotionEffects())
                    {
                        p.removePotionEffect(effect.getType());
                    }
                    hg.reduceGroupLive();
                    event.setCancelled(true);
                }
            }
        }
    }
}
