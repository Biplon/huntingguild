package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWaveMonster implements CommandExecutor
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
                        String[] items = HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getWavemonsterfromWave(Integer.parseInt(args[1]));
                        for (String item : items)
                        {
                            commandSender.sendMessage("Wavemonster info: " + item);
                            return true;
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
                else if (args.length == 5)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addWaveMonstertoWave(Integer.parseInt(args[1]), args[3], args[4], Integer.parseInt(args[2])))
                        {
                            commandSender.sendMessage("Wavemonster created: " + args[1] + "|" + args[3] + "|" + args[4] + "|" + args[2]);
                        }
                        else
                        {
                            commandSender.sendMessage("Can not create wavemonster");
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
                else if (args.length == 6)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeWaveMonsterfromWave(Integer.parseInt(args[1]), args[3]);
                        commandSender.sendMessage("Wavemonster removed: " + args[1] + "|" + args[3]);
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