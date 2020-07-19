package hg.java.command;

import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetVisitsPerHour implements CommandExecutor
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
            else if (args.length == 2)
            {
                if (HuntingGroundManager.getInstance().getHuntingground(args[0]) != null)
                {
                    HuntingGroundManager.getInstance().getHuntingground(args[0]).visitsPerHour = Integer.parseInt(args[1]);
                }
                return true;
            }
        }
        return false;
    }
}