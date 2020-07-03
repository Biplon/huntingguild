package main.java.command;

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
            if (!player.hasPermission("hg.plready"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingground(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingground(args[0]).hggroup.setPlayerready(player))
                        {
                            if (!HuntingGroundManager.getInstance().getHuntingground(args[0]).isinuse)
                            {
                                HuntingGroundManager.getInstance().getHuntingground(args[0]).starthuntingground();
                            }
                        }
                        return true;
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
                else
                {
                    commandSender.sendMessage("No hunting ground joint");
                    return true;
                }
            }
        }
        return false;
    }
}