package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHuntingGroundGroupLive implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgeditcommands"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        commandSender.sendMessage(args[0] + ": " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getGrouplifes());
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
                else if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        try
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).setGrouplifes(Integer.parseInt(args[1]));
                            commandSender.sendMessage(args[0] + ": " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getGrouplifes());
                            return true;
                        }
                        catch (NumberFormatException ex)
                        {
                            commandSender.sendMessage("Group lives can not set: " + args[0] + ": " + args[1]);
                            return true;
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}