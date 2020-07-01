package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import main.java.huntingground.struct.Spawnpoint;
import main.java.huntingground.struct.Wave;
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
            if (!player.hasPermission("hg.setwave"))
            {
                return false;
            }
            else
            {
                if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        for (Wave item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getWaves())
                        {
                            if (item.waveid.equals(args[1]))
                            {
                                commandSender.sendMessage("Wave info: " + item.toString());
                                return true;
                            }
                        }
                        commandSender.sendMessage("Wave not found");
                        return true;

                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
                else if (args.length == 4)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addWave(args[1], args[2],Boolean.parseBoolean(args[3])))
                        {
                            commandSender.sendMessage("Wave created:"+ args[1] +"|"+ args[2]);
                            return true;
                        }
                        else
                        {
                            commandSender.sendMessage("Can not create wave");
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
                        HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeWave(args[1]);
                        commandSender.sendMessage("Wave removed: " + args[1]);
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