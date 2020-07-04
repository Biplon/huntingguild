package main.java.command;

import main.java.enums.HgGuis;
import main.java.gui.GUIManager;
import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoinHG implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgjoin"))
            {
                return false;
            }
            else
            {
                if (args.length == 0)
                {
                    player.openInventory(GUIManager.getInstance().getGUIInstance(HgGuis.hgjoin).getInventory());
                    return true;
                }
                else if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingground(args[0]) != null)
                    {
                        if (!HuntingGroundManager.getInstance().getHuntingground(args[0]).hggroup.isFull())
                        {
                            HuntingGroundManager.getInstance().getHuntingground(args[0]).hggroup.addPlayer(player);
                            commandSender.sendMessage("Group joined! For: " + args[0]);
                        }
                        else
                        {
                            commandSender.sendMessage("Group full! For: " + args[0]);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
