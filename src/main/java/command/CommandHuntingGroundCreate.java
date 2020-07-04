package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHuntingGroundCreate implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgcreate"))
            {
                return false;
            }
            else
            {
                if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) == null && HuntingGroundManager.getInstance().getHuntingground(args[0]) == null)
                    {
                        if (Bukkit.getServer().getWorld(args[1]) != null)
                        {
                            if (HuntingGroundManager.getInstance().createNewHuntingGround(args[0], args[1]))
                            {
                                commandSender.sendMessage("Hunting ground is created. " + args[0] + "|" + args[1]);
                            }
                            else
                            {
                                commandSender.sendMessage("Hunting ground can not create");
                            }
                            return true;
                        }
                        else
                        {
                            commandSender.sendMessage("Hunting ground can not create.World not found: " + args[1]);
                            return true;
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("Hunting ground with this name already exist." + args[0]);
                        return true;
                    }
                }
                else
                {
                    commandSender.sendMessage("No hunting ground can create");
                    return true;
                }
            }
        }
        return false;
    }
}
