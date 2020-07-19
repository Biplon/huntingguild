package hg.java.command;

import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChangeHGMode implements CommandExecutor
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
                    if (HuntingGroundManager.getInstance().changeHuntingGroundMode(args[0]))
                    {
                        commandSender.sendMessage("Hunting ground mode changed: " + args[0]);
                    }
                    else
                    {
                        commandSender.sendMessage("Hunting ground mode can not changed: " + args[0] + " (is a Group in it ?)");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
