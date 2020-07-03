package main.java.command;

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
            if (!player.hasPermission("hg.leavehg"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {

                    if (HuntingGroundManager.getInstance().getHuntingground(args[0]) != null)
                    {
                        if (!HuntingGroundManager.getInstance().getHuntingground(args[0]).isinuse)
                        {
                            HuntingGroundManager.getInstance().getHuntingground(args[0]).hggroup.removePlayer(player);
                            commandSender.sendMessage("Group leaved! For: "+args[0]);
                        }
                        else
                        {
                            commandSender.sendMessage("Can not leave if Hunting ground is active");
                        }
                        return true;
                    }

                }
            }
        }
        return false;
    }
}