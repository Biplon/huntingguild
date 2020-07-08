package main.java.command;

import main.java.enums.HgGuis;
import main.java.gui.GUIManager;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeaveHG implements CommandExecutor
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
                if (args.length == 0)
                {
                    player.openInventory(GUIManager.getInstance().createGUI(HgGuis.hgleave));
                    return true;
                }
                else if (args.length == 1)
                {
                    HuntingGroundManager.getInstance().leavePlayer(player, false);
                    return true;
                }
            }
        }
        return false;
    }
}