package hg.java.command;

import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreateGroupForHG implements CommandExecutor
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
                if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        commandSender.sendMessage("Group size: " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getGroupinhuntingground());
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

                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).setGroupinhuntingground(args[1],args[2],Boolean.getBoolean(args[3])))
                        {

                            commandSender.sendMessage("Group created. Player size: " + args[1]);
                        }
                        else
                        {
                            commandSender.sendMessage("Group can not created");
                        }
                        return true;
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