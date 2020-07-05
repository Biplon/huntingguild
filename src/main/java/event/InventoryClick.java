package main.java.event;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryClick implements Listener
{
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e)
    {
        if (!e.getView().getTitle().contains("HG:"))
        {
            return;
        }
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR)
        {
            return;
        }

        final Player p = (Player) e.getWhoClicked();
        final String hg = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName();

        if (HuntingGroundManager.getInstance().existHG(hg))
        {
            if (!HuntingGroundManager.getInstance().getHuntingground(hg).hggroup.isFull())
            {
                p.closeInventory();
                HuntingGroundManager.getInstance().getHuntingground(hg).hggroup.addPlayer(p);
            }
            else
            {
                p.sendMessage("Group full! For: " + hg);
                e.setCancelled(true);
            }
        }
        else if (hg.equalsIgnoreCase("ready"))
        {
            HuntingGroundManager.getInstance().setPlayerReady(p);

            p.closeInventory();
        }
        else if (hg.equalsIgnoreCase("not ready"))
        {
            p.sendMessage("If you ready type: /hgplready");
            p.closeInventory();
        }
        else if (hg.equalsIgnoreCase("yes leave"))
        {
            HuntingGroundManager.getInstance().leavePlayer(p, false);
            p.closeInventory();
        }
        else if (hg.equalsIgnoreCase("no"))
        {
            p.closeInventory();
        }
        e.setCancelled(true);
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e)
    {
        if (e.getView().getTitle().contains("HG:"))
        {
            e.setCancelled(true);
        }
    }
}
