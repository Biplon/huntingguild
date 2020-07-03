package main.java.event;

import main.java.gui.GUIManager;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener
{
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e)
    {
        if (e.getInventory() != GUIManager.getInstance().joingui.getInventory() || e.getInventory() != GUIManager.getInstance().joingui.getInventory() || e.getInventory() != GUIManager.getInstance().joingui.getInventory())
        {
            return;
        }
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
        {
            return;
        }


        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("test2"))
        {
            if (!HuntingGroundManager.getInstance().getHuntingground("test2").hggroup.isFull())
            {
                HuntingGroundManager.getInstance().getHuntingground("test2").hggroup.addPlayer(p);
                p.sendMessage("Group joined! For: test2");
            }
            else
            {
                p.sendMessage("Group full! For: test2");
            }
        }
        p.closeInventory();
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == GUIManager.getInstance().joingui.getInventory() || e.getInventory() == GUIManager.getInstance().joingui.getInventory() || e.getInventory() == GUIManager.getInstance().joingui.getInventory())
        {
            e.setCancelled(true);
        }
    }
}
