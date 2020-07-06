package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWave implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgbuilder"))
            {
                return false;
            }
            else
            {
                if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getWaves().size() >= Integer.parseInt(args[1]))
                        {
                            commandSender.sendMessage("Wave info: " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getWaves().get(Integer.parseInt(args[1])).toString());
                            return true;
                        }
                        commandSender.sendMessage("Wave not found");
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
                else if (args.length == 3)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addWave(HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getWaves().size(), args[1], Boolean.parseBoolean(args[2])))
                        {
                            commandSender.sendMessage("Wave created:" + args[1] + "|" + args[2]);
                        }
                        else
                        {
                            commandSender.sendMessage("Can not create wave");
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
                else if (args.length == 4)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeWave(Integer.parseInt(args[1]));
                        commandSender.sendMessage("Wave removed: " + args[1]);
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}