package main.java.command;

import main.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHuntingGroundCommands implements CommandExecutor

{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.editgrouplive"))
            {
                return false;
            }
            else
            {
                if (args.length == 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (args[1].equals("win"))
                        {
                            for (String item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getHuntinggroundwincommands())
                            {
                                commandSender.sendMessage(item);
                            }
                            return true;
                        }
                        else if (args[1].equals("lose"))
                        {
                            for (String item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getHuntinggroundlosecommands())
                            {
                                commandSender.sendMessage(item);
                            }
                            return true;
                        }
                        else if (args[1].equals("start"))
                        {
                            for (String item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getEquipcommands())
                            {
                                commandSender.sendMessage(item);
                            }
                            return true;
                        }
                    }
                    else
                    {
                        commandSender.sendMessage("No hunting ground found:" + args[0] + " (hunting ground not exist or is not in build mode)");
                        return true;
                    }
                }
                else if (args.length == 3)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        if (args[1].equals("win"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addHuntinggroundwincommands(args[2]);
                            commandSender.sendMessage("Command added:" + args[2]);
                            return true;
                        }
                        else if (args[1].equals("lose"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addHuntinggroundlosecommands(args[2]);
                            commandSender.sendMessage("Command added:" + args[2]);
                            return true;
                        }
                        else if (args[1].equals("start"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addEquipcommands(args[2]);
                            commandSender.sendMessage("Command added:" + args[2]);
                            return true;
                        }
                        else
                        {
                            commandSender.sendMessage("Type not found:" + args[1]);
                            return false;
                        }
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
                        if (args[1].equals("win"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeHuntinggroundwincommands(args[2]);
                            commandSender.sendMessage("Command removed: "+ args[2]);
                            return true;
                        }
                        else if (args[1].equals("lose"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeHuntinggroundlosecommands(args[2]);
                            commandSender.sendMessage("Command removed: "+ args[2]);
                            return true;
                        }
                        else if (args[1].equals("start"))
                        {
                            HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeEquipcommands(args[2]);
                            commandSender.sendMessage("Command removed: "+ args[2]);
                            return true;
                        }
                        else
                        {
                            commandSender.sendMessage("Type not found:" + args[1]);
                            return false;
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

