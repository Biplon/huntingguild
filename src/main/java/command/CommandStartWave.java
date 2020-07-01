package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStartWave implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.startwave"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {

                    if (HuntingGroundManager.getInstance().getHuntinground(args[0]) != null)
                    {
                        HuntingGroundManager.getInstance().getHuntinground(args[0]).startWave();
                    }
                    return true;

                }
            }
        }
        return false;
    }
}
