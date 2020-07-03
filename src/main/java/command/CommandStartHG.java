package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStartHG implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.starthg"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingground(args[0]) != null)
                    {
                        if (!HuntingGroundManager.getInstance().getHuntingground(args[0]).starthuntingground())
                        {
                            commandSender.sendMessage("Hunting ground can not start. Group not full!");
                        }
                    }
                    return true;

                }
            }
        }
        return false;
    }
}
