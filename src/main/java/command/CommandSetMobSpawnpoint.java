package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import main.java.huntingground.struct.Spawnpoint;
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
            if (!player.hasPermission("hg.setmsp"))
            {
                return false;
            }
            else
            {
                if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        for (Spawnpoint item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getSpawnpoints())
                        {
                            if (item.spawnpointname.equals(args[1]))
                            {
                                commandSender.sendMessage("Spawnpoint info: " + item.toString());
                                return true;
                            }
                        }
                        commandSender.sendMessage("Spawnpoint not found");
                        return true;

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
                        for (Spawnpoint item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getSpawnpoints())
                        {
                            if (item.spawnpointname.equals(args[1]))
                            {

                                commandSender.sendMessage("Spawnpoint already exist: " + item.toString());
                                return true;
                            }
                        }
                        Spawnpoint sp = new Spawnpoint(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                        HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addSpawnpoint(sp);
                        commandSender.sendMessage("Spawnpoint create: " + sp.toString());
                        return true;
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
                else if (args.length == 6)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeSpawnpoint(args[2]))
                        {
                            commandSender.sendMessage("Spawnpoint deleted: "+ args[2]);
                            return true;
                        }
                        else
                        {
                            commandSender.sendMessage("Spawnpoint not found.");
                            return true;
                        }

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