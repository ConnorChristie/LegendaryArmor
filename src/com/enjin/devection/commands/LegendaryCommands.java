package com.enjin.devection.commands;

import com.enjin.devection.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.enjin.devection.armor.LegendaryArmor;
import com.enjin.devection.util.Utils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LegendaryCommands implements CommandExecutor
{
    Main main;
	private String PREFIX = "[LegendaryArmor] ";

    public LegendaryCommands(Main main) {
        this.main = main;
    }

    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("legend"))
		{
            if(sender instanceof ConsoleCommandSender) {
                if (args.length == 2) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    LegendaryArmor armor = LegendaryArmor.armor(args[1]);

                    if (player != null && player.hasPlayedBefore()) {
                        if (armor != null) {
                            if (player.isOnline()) {

                            } else {
                                Utils.addToOfflineInventory(player, armor.getItem());
                            }
                        } else
                            sender.sendMessage(PREFIX + "Could not find specified armor");
                    } else
                        sender.sendMessage(PREFIX + "Could not find specified player");
                } else sender.sendMessage(PREFIX + "Usage: /legend <player> <legendary> or /legend");
            }
            else if(sender instanceof Player)
            {
                if(args.length == 1)
                {
                    if(args[0].equalsIgnoreCase("redeem"))
                    {
                        if(main.getAwaitingLegendaries.contains(((Player) sender).getUniqueId()))
                        {
                            List<LegendaryArmor> toGive = main.getAwaitingLegendaries.get(((Player) sender).getUniqueId());
                            for(LegendaryArmor armor: toGive)
                            {
                                ((Player) sender).getInventory().addItem(armor.getItem());
                            }
                            main.getAwaitingLegendaries.remove(((Player) sender).getUniqueId());
                            sender.sendMessage(PREFIX + "You received your items.");
                        } sender.sendMessage(PREFIX + "You have no redeemable legendary items.");
                    } else sender.sendMessage(PREFIX + "Usage: /legend redeem");
                }
            }
		}
		return true;
	}
}