package main.java.gui;

import main.java.enums.HgGuis;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HGGUI
{
    private Inventory inv;

    private final HgGuis mygui;

    public HGGUI(HgGuis gui)
    {
        mygui = gui;
        createInventory(gui);
        initializeDecoItems(gui);
        initializeClickableItems(gui);
    }

    public Inventory getInventory()
    {
        return inv;
    }

    public HgGuis getMyGuiType()
    {
        return mygui;
    }

    private void createInventory(HgGuis gui)
    {
        switch (gui)
        {
            case hgjoin:
                inv = Bukkit.createInventory(null, 45, "HG: Join hunting ground");
                break;
            case hgleave:
                inv = Bukkit.createInventory(null, 27, "HG: Leave hunting ground");
                break;
            case hggroupreadycheck:
                inv = Bukkit.createInventory(null, 27, "HG: Hunting ground ready check");
                break;
            case hgeditor:
                inv = Bukkit.createInventory(null, 54, "HG: Hunting ground editor");
                break;
        }
    }

    private void initializeClickableItems(HgGuis gui)
    {
        switch (gui)
        {
            case hgjoin:
                setHg();
                break;
            case hgleave:
                setLeave();
                break;
            case hggroupreadycheck:
                setReady();
                break;
            case hgeditor:
                break;
        }
    }

    private void initializeDecoItems(HgGuis gui)
    {
        switch (gui)
        {
            case hgjoin:
            case hgleave:
            case hggroupreadycheck:
                for (int i = 0; i < 9; i++)
                {
                    inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                    inv.setItem(i + 18, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
            case hgeditor:
                break;
        }

        switch (gui)
        {
            case hgjoin:
                for (int i = 0; i < 9; i++)
                {
                    inv.setItem(i + 36, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
                for (int i = 9; i <= 17; i += 2)
                {
                    inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                    inv.setItem(i + 18, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
                break;
            case hgleave:
            case hggroupreadycheck:
                inv.setItem(13, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                for (int i = 9; i < 12; i++)
                {
                    inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                    inv.setItem(i + 6, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
                break;
            case hgeditor:
                break;
        }
    }

    private void setHg()
    {
        int y = 0;
        for (int i = 10; i <= 16; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                {
                    inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                }
                else
                {
                    inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, "", "", ""));
            }

        }
        for (int i = 28; i <= 34; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                {
                    inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                }
                else
                {
                    inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, "", "", ""));
            }
        }
    }

    private void setLeave()
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WALL_BANNER, "Yes leave", "", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "No", "", ""));
    }

    private void setReady()
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WALL_BANNER, "Ready", "", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "Not ready", "", ""));
    }

    private ItemStack createGuiItem(final Material material, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}
