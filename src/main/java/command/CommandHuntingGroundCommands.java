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
                        switch (args[1])
                        {
                            case "win":
                                for (String item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getHuntinggroundwincommands())
                                {
                                    commandSender.sendMessage(item);
                                }
                                return true;
                            case "lose":
                                for (String item : HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).getHuntinggroundlosecommands())
                                {
                                    commandSender.sendMessage(item);
                                }
                                return true;
                            case "start":
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
                else if (args.length > 2)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]) != null)
                    {
                        String commandvalue = "";
                        if (args[args.length - 1].equalsIgnoreCase("remove"))
                        {
                            for (int i = 2; i < args.length - 1; i++)
                            {
                                commandvalue += args[i] + " ";
                            }
                            switch (args[1])
                            {
                                case "win":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeHuntinggroundwincommands(commandvalue);
                                    commandSender.sendMessage("Command removed: " + commandvalue);
                                    return true;
                                case "lose":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeHuntinggroundlosecommands(commandvalue);
                                    commandSender.sendMessage("Command removed: " + commandvalue);
                                    return true;
                                case "start":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).removeEquipcommands(commandvalue);
                                    commandSender.sendMessage("Command removed: " + commandvalue);
                                    return true;
                                default:
                                    commandSender.sendMessage("Type not found:" + args[1]);
                                    return false;
                            }
                        }
                        else
                        {
                            for (int i = 1; i < args.length; i++)
                            {
                                commandvalue += args[i] + " ";
                            }
                            switch (args[1])
                            {
                                case "win":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addHuntinggroundwincommands(commandvalue);
                                    commandSender.sendMessage("Command added:" + commandvalue);
                                    return true;
                                case "lose":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addHuntinggroundlosecommands(commandvalue);
                                    commandSender.sendMessage("Command added:" + commandvalue);
                                    return true;
                                case "start":
                                    HuntingGroundManager.getInstance().getHuntingGroundBuilder(args[0]).addEquipcommands(commandvalue);
                                    commandSender.sendMessage("Command added:" + commandvalue);
                                    return true;
                                default:
                                    commandSender.sendMessage("Type not found:" + args[1]);
                                    return false;
                            }
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

