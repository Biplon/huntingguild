package main.java.command;

import main.java.enums.HgGuis;
import main.java.gui.GUIManager;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetPlayerReady implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgplayer"))
            {
                return false;
            }
            else
            {
                if (HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(player) !=null)
                {
                    return true;
                }
                if (args.length == 0)
                {
                    player.openInventory(GUIManager.getInstance().createGUI(HgGuis.hggroupreadycheck));
                    return true;
                }
            }
        }
        return false;
    }
}