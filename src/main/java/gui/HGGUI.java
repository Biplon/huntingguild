package main.java.gui;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HGGUI
{
    private final Inventory inv;

    public HGGUI(String name,int size)
    {
        inv = Bukkit.createInventory(null, size, name);
    }

    public Inventory getInventory()
    {
        return inv;
    }

    public void initializeItems()
    {
        for (int i = 0; i < 9; i++)
        {
            inv.setItem(i,new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            inv.setItem(i+18,new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            inv.setItem(i+36,new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        for (int i = 9; i <= 17; i+=2)
        {
            inv.setItem(i,new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            inv.setItem(i+18,new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

    }

    public void sethg()
    {
        int y = 0;
        for (int i = 10; i <= 16; i+=2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                inv.setItem(i, createGuiItem(Material.GREEN_DYE,HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname,HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots()+"/"+HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() +  "Player" ,"Own equip: "+HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER,"","",""));
            }

        }
        for (int i = 28; i <= 34; i+=2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                inv.setItem(i, createGuiItem(Material.GREEN_DYE,HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname,HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots()+"/"+HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() +  "Player" ,"Own equip: "+HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER,i+"","",""));
            }

        }
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
