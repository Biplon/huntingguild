package hg.java.command;

import hg.java.huntingground.HuntingGroundManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStartReadyCheck implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (!player.hasPermission("hg.hgplayer"))
            {
                return false;
            }
            else
            {
                if (HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(player) !=null)
                {
                    if (HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(player).hggroup.groupMinSizeReached())
                    {
                        HuntingGroundManager.getInstance().getHuntingGroundOfPlayer(player).hggroup.readyCheck();
                    }
                    else
                    {
                        player.sendMessage("Cant start readycheck");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}