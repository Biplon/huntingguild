package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import main.java.struct.Spawnpoint;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetMobSpawnpoint implements CommandExecutor
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
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getMobspawnpoints().size() >= Integer.parseInt(args[1]))
                        {
                            commandSender.sendMessage("Spawnpoint info: " + HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getMobspawnpoints().get(Integer.parseInt(args[1])).toString());
                            return true;
                        }
                        commandSender.sendMessage("Spawnpoint not found");
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                    }
                    return true;
                }
                else if (args.length == 5)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        Spawnpoint sp = new Spawnpoint(HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getMobspawnpoints().size(), Bukkit.getWorld(HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).world), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                        HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addSpawnpoint(sp);
                        commandSender.sendMessage("Spawnpoint create: " + sp.toString());
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
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeMobSpawnpoint(Integer.parseInt(args[1])))
                        {
                            commandSender.sendMessage("Spawnpoint deleted: " + args[1]);
                        }
                        else
                        {
                            commandSender.sendMessage("Spawnpoint not found.");
                        }
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