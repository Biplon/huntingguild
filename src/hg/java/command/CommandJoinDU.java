package hg.java.command;

import hg.java.enums.HgGuis;
import hg.java.gui.GUIManager;
import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoinDU implements CommandExecutor
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
                if (HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(player) != null)
                {
                    commandSender.sendMessage("You been already in a group!");
                    return true;
                }
                if (args.length == 0)
                {
                    player.openInventory(GUIManager.getInstance().createGUI(HgGuis.dujoin));
                    return true;
                }
                else if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingground(args[0]).canJoin(player.getUniqueId()))
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
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("Visits per hour full!");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
