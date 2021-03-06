package com.enjin.devection.commands;

import com.enjin.devection.legendary.LegendaryItem;
import com.enjin.devection.main.Main;
import com.enjin.devection.util.Utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LegendaryCommands implements CommandExecutor
{
	private Main main;
	
	public LegendaryCommands()
	{
		main = Main.getInstance();
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("legend"))
		{
			if (args[0].equalsIgnoreCase("redeem"))
			{
				if (sender instanceof Player)
				{
					Player player = (Player) sender;
					
					if (args.length == 1)
					{
						main.getAwaitingLegendaries().redeemLegendaries(player);
					} else
						sender.sendMessage(Main.PRETTY_PREFIX + "Usage: /legend redeem");
				}
			} else
			{
				if (sender instanceof ConsoleCommandSender || (sender instanceof Player && sender.hasPermission("legendary.give")))
				{
					if (args.length == 2)
					{
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
						LegendaryItem legendary = main.getLegendaryItems().getLegendary(args[1]);
						
						if (player.hasPlayedBefore() || player.isOnline())
						{
							if (legendary != null)
							{
								legendary.setCode(Utils.getRandomHexString(8).toLowerCase());
								
								main.getAwaitingLegendaries().addAwaiting(player.getUniqueId(), legendary);
								
								if (player.isOnline())
								{
									main.getAwaitingLegendaries().redeemLegendaries(player.getPlayer());
									
									//player.getPlayer().sendMessage(Main.PRETTY_PREFIX + "You have a new legendary item to redeem!");
									//player.getPlayer().sendMessage(Main.PRETTY_PREFIX + "To redeem, type: /legend redeem");
								}
							} else
								sender.sendMessage(Main.PREFIX + "Could not find specified armor");
						} else
							sender.sendMessage(Main.PREFIX + "Could not find specified player");
					} else
						sender.sendMessage(Main.PREFIX + "Usage: /legend <player> <legendary>");
				}
			}
		}
		
		return true;
	}
}
