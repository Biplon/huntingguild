package main.java.gui;

import main.java.enums.HgGuiPages;
import main.java.enums.HgGuis;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HGGUI
{
    private Inventory inv;

    private final HgGuis mygui;

    private static final ItemStack defaultGuiItem = createGuiItem(Material.BLACK_STAINED_GLASS_PANE," ","");

    public HGGUI(HgGuis gui)
    {
        mygui = gui;
        createInventory(gui);
        initializeDecoItems(gui);
        initializeClickableItems(gui);
        if (mygui == HgGuis.hgeditor)
        {

        }
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
            case dujoin:
                inv = Bukkit.createInventory(null, 45, "HG: Join dungeon");
                break;
            case hgleave:
                inv = Bukkit.createInventory(null, 27, "HG: Leave hunting ground");
                break;
            case hggroupreadycheck:
                inv = Bukkit.createInventory(null, 27, "HG: Hunting ground ready check");
                break;
            case hgeditor:
                inv = Bukkit.createInventory(null, 45, "HG: Editor");
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
            case dujoin:
                setDu();
                break;
            case hgleave:
                setLeave();
                break;
            case hggroupreadycheck:
                setReady();
                break;
            case hgeditor:
                setCreateHG();
                break;
        }
    }

    private void initializeDecoItems(HgGuis gui)
    {
        switch (gui)
        {
            case hgjoin:
            case dujoin:
            case hgleave:
            case hggroupreadycheck:
            case hgeditor:
                for (int i = 0; i < 9; i++)
                {
                    inv.setItem(i, defaultGuiItem);
                    inv.setItem(i + 18, defaultGuiItem);
                }
                break;
        }

        switch (gui)
        {
            case hgjoin:
            case dujoin:
                for (int i = 0; i < 9; i++)
                {
                    inv.setItem(i + 36, defaultGuiItem);
                }
                for (int i = 9; i <= 17; i += 2)
                {
                    inv.setItem(i, defaultGuiItem);
                    inv.setItem(i + 18, defaultGuiItem);
                }
                break;
            case hgleave:
            case hggroupreadycheck:
                inv.setItem(13, defaultGuiItem);
                for (int i = 9; i < 12; i++)
                {
                    inv.setItem(i, defaultGuiItem);
                    inv.setItem(i + 6, defaultGuiItem);
                }
                break;
            case hgeditor:
                for (int i = 0; i < 9; i++)
                {
                    inv.setItem(i + 36, defaultGuiItem);
                }
                inv.setItem(13, defaultGuiItem);
                inv.setItem(31, defaultGuiItem);
                for (int i = 9; i <= 11; i += 1)
                {
                    inv.setItem(i, defaultGuiItem);
                    inv.setItem(i + 6, defaultGuiItem);
                    inv.setItem(i + 18, defaultGuiItem);
                    inv.setItem(i + 24,  defaultGuiItem);
                }
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
                if (!HuntingGroundManager.getInstance().getHuntingground(y).isDungeonMode())
                {
                    if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                    {
                        inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                    else
                    {
                        inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                }
                else
                {
                    i -=2;
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, " ", ""));
            }

        }
        for (int i = 28; i <= 34; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                if (!HuntingGroundManager.getInstance().getHuntingground(y).isDungeonMode())
                {
                    if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                    {
                        inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                    else
                    {
                        inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                }
                else
                {
                    i -=2;
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER," ",""));
            }
        }
    }

    private void setDu()
    {
        int y = 0;
        for (int i = 10; i <= 16; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                if (HuntingGroundManager.getInstance().getHuntingground(y).isDungeonMode())
                {
                    if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                    {
                        inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                    else
                    {
                        inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                }
                else
                {
                    i -=2;
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, " ", ""));
            }

        }
        for (int i = 28; i <= 34; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingground(y) != null)
            {
                if (HuntingGroundManager.getInstance().getHuntingground(y).isDungeonMode())
                {
                    if (!HuntingGroundManager.getInstance().getHuntingground(y).isinuse)
                    {
                        inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                    else
                    {
                        inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname, HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getHuntingground(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getHuntingground(y).playerowninventory));
                    }
                }
                else
                {
                    i -=2;
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER," ",""));
            }
        }
    }
    private void setHgtoedit()
    {
        int y = 0;
        for (int i = 10; i <= 16; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(y) != null)
            {
                inv.setItem(i, createGuiItem(Material.GREEN_DYE,"Edit: "+ HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname));
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, " ", ""));
            }

        }
        for (int i = 28; i <= 34; i += 2)
        {
            if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(y) != null)
            {
                inv.setItem(i, createGuiItem(Material.GREEN_DYE,"Edit: "+ HuntingGroundManager.getInstance().getHuntingground(y).huntinggroundname));
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER, " ", ""));
            }
        }
    }

    private void setLeave()
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WOOL, "Yes leave", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "No", ""));
    }

    private void setCreateHG()
    {
        inv.setItem(12, createGuiItem(Material.PAPER, "Create hunting ground", ""));
        inv.setItem(14, createGuiItem(Material.PAPER, "Create dungeon", "not implement"));
        inv.setItem(30, createGuiItem(Material.BOOK, "Edit hunting ground", ""));
        inv.setItem(32, createGuiItem(Material.BOOK, "Edit dungeon", "not implement"));
    }

    private void setReady()
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WOOL, "Ready", "", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "Not ready", "", ""));
    }

    public void changeMenuPage(HgGuiPages page)
    {
        switch (page)
        {
            case selecthgtoedit:
                initializeDecoItems(HgGuis.hgjoin);
                setHgtoedit();
                break;
            case edithgmainmenu:
                break;
            case changehgmodetoedit:
                break;
            case changehgmodetolive:
                break;
            case edithgtools:
                break;
            case removehgwave:
                break;
            case removehgspawnpointmonster:
                break;
            case removehgspawnpointplayer:
                break;
            case changehgspawnpointplayer:
                break;
            case edithggeneral:
                break;
        }

    }

    private static ItemStack createGuiItem(final Material material, final String name, final String... lore)
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
