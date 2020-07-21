package hg.java.command;

import hg.java.HuntingGuild;
import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandLoadHG implements CommandExecutor
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
                    if (HuntingGroundManager.getInstance().loadHuntingGround(args[0]))
                    {
                        player.sendMessage("HG load!");
                    }
                    else
                    {
                        player.sendMessage("can not load HG!");
                    }
                }
            }
        }
        return false;
    }
}