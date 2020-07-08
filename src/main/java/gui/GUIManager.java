package main.java.gui;

import main.java.enums.HgGuiPages;
import main.java.enums.HgGuis;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIManager
{
    public static GUIManager instance;

    public GUIManager()
    {
        instance = this;

        pagesdungeons = (double) HuntingGroundManager.getInstance().getDungeons() / 8;

        pageshuntingrounds =  (double)HuntingGroundManager.getInstance().getHuntingrounds() / 8;
    }

    public static GUIManager getInstance()
    {
        return instance;
    }

    private double pagesdungeons;
    private double pageshuntingrounds;

    public Inventory changeGUIJoinElements(HgGuis gui, Inventory inv,boolean next)
    {
        if (gui == HgGuis.dujoin || gui == HgGuis.hgjoin)
        {
            if (next)
            {
                initializeClickableItems(gui,inv,inv.getItem(44).getAmount());
            }
            else
            {
                initializeClickableItems(gui,inv,inv.getItem(36).getAmount());
            }

        }
        return inv;
    }

    private static final ItemStack defaultGuiItem = createGuiItem(Material.BLACK_STAINED_GLASS_PANE," ","");

    public Inventory createGUI(HgGuis gui)
    {
        Inventory inv = createInventory(gui);
        initializeDecoItems(gui,inv);
        initializeClickableItems(gui,inv,0);
        return inv;
    }

    private Inventory createInventory(HgGuis gui)
    {
        switch (gui)
        {
            case hgjoin:
                return Bukkit.createInventory(null, 45, "HG: Join hunting ground");
            case dujoin:
                return Bukkit.createInventory(null, 45, "HG: Join dungeon");
            case hgleave:
                return Bukkit.createInventory(null, 27, "HG: Leave hunting ground");
            case hggroupreadycheck:
                return Bukkit.createInventory(null, 27, "HG: Hunting ground ready check");
            case hgeditor:
                return Bukkit.createInventory(null, 45, "HG: Editor");
        }
        return null;
    }

    private void initializeClickableItems(HgGuis gui,Inventory inv,int page)
    {
        switch (gui)
        {
            case hgjoin:
                setHg(inv,page == 1 ? 0 : page);
                if (pageshuntingrounds > 1 && page <= pageshuntingrounds)
                {
                    inv.setItem(44, createGuiItem(Material.BOOK,page == 0 ? 2 : page+1 ,"Next page",""));
                }
                else
                {
                    inv.setItem(44,defaultGuiItem);
                }
                if (page >0)
                {
                    inv.setItem(36, createGuiItem(Material.BOOK,page == 2 ? 1 : page-1,"Previous page",""));
                }
                else
                {
                    inv.setItem(36,defaultGuiItem);
                }
                break;
            case dujoin:
                setDu(inv,page == 1 ? 0 : page);
                if (pagesdungeons > 1 && page <= pageshuntingrounds)
                {
                    inv.setItem(44, createGuiItem(Material.BOOK,page == 0 ? 2 : page+1,"Next page",""));
                }
                else
                {
                    inv.setItem(44,defaultGuiItem);
                }
                if (page > 0)
                {
                    inv.setItem(36, createGuiItem(Material.BOOK,page == 2 ? 1 : page-1,"Previous page",""));
                }
                else
                {
                    inv.setItem(36,defaultGuiItem);
                }
                break;
            case hgleave:
                setLeave(inv);
                break;
            case hggroupreadycheck:
                setReady(inv);
                break;
            case hgeditor:
                setCreateHG(inv);
                break;
        }

    }

    private void initializeDecoItems(HgGuis gui,Inventory inv)
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

    private void setHg(Inventory inv,int page)
    {
        int y = 0;
        if (page > 1)
        {
            y = 8 * (page-1) ;
        }
        if (y > 7)
        {
            y--;
        }
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
                inv.setItem(i, createGuiItem(Material.BARRIER, " ", ""));
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
                inv.setItem(i, createGuiItem(Material.BARRIER," ",""));
            }
        }
    }

    private void setDu(Inventory inv,int page)
    {
        int y = 0;
        if (page > 1)
        {
            y = 8 * (page-1) ;
        }
        if (y > 7)
        {
            y--;
        }
        for (int i = 10; i <= 16; i += 2)
        {
            if (HuntingGroundManager.getInstance().getDungeon(y) != null)
            {
                if (!HuntingGroundManager.getInstance().getDungeon(y).isinuse)
                {
                    inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getDungeon(y).huntinggroundname, HuntingGroundManager.getInstance().getDungeon(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getDungeon(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getDungeon(y).playerowninventory));
                }
                else
                {
                    inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getDungeon(y).huntinggroundname, HuntingGroundManager.getInstance().getDungeon(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getDungeon(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getDungeon(y).playerowninventory));
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
            if (HuntingGroundManager.getInstance().getDungeon(y) != null)
            {
                if (!HuntingGroundManager.getInstance().getDungeon(y).isinuse)
                {
                    inv.setItem(i, createGuiItem(Material.GREEN_DYE, HuntingGroundManager.getInstance().getDungeon(y).huntinggroundname, HuntingGroundManager.getInstance().getDungeon(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getDungeon(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getDungeon(y).playerowninventory));
                }
                else
                {
                    inv.setItem(i, createGuiItem(Material.RED_DYE, HuntingGroundManager.getInstance().getDungeon(y).huntinggroundname, HuntingGroundManager.getInstance().getDungeon(y).hggroup.getFullSlots() + "/" + HuntingGroundManager.getInstance().getDungeon(y).hggroup.getGroupSize() + "Player", "Own equip: " + HuntingGroundManager.getInstance().getDungeon(y).playerowninventory));
                }
                y++;
            }
            else
            {
                inv.setItem(i, createGuiItem(Material.BARRIER," ",""));
            }
        }
    }
    private void setHgtoedit(Inventory inv)
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

    private void setLeave(Inventory inv)
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WOOL, "Yes leave", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "No", ""));
    }

    private void setCreateHG(Inventory inv)
    {
        inv.setItem(12, createGuiItem(Material.PAPER, "Create hunting ground", ""));
        inv.setItem(14, createGuiItem(Material.PAPER, "Create dungeon", "not implement"));
        inv.setItem(30, createGuiItem(Material.BOOK, "Edit hunting ground", ""));
        inv.setItem(32, createGuiItem(Material.BOOK, "Edit dungeon", "not implement"));
    }

    private void setReady(Inventory inv)
    {
        inv.setItem(12, createGuiItem(Material.GREEN_WOOL, "Ready", "", ""));
        inv.setItem(14, createGuiItem(Material.RED_WOOL, "Not ready", "", ""));
    }

    public void changeMenuPage(HgGuiPages page)
    {
        switch (page)
        {
            case selecthgtoedit:
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

    private static ItemStack createGuiItem(final Material material,int amount, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}
