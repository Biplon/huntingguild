package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSaveHG implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgsave"))
            {
                return false;
            }
            else
            {
                if (args.length == 1)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        commandSender.sendMessage("Save: " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).saveHuntingGround());
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                }
                else
                {
                    commandSender.sendMessage("No hunting ground found: none  (hunting ground not exist or is not in build mode)");
                }
                return true;
            }
        }
        return false;
    }
}
