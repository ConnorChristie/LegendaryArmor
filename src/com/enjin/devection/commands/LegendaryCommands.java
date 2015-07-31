package com.enjin.devection.commands;

import com.enjin.devection.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.enjin.devection.armor.LegendaryArmor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LegendaryCommands implements CommandExecutor
{
	private Main main;
	
	private String PREFIX = "[LegendaryArmor] ";
	private String PRETTY_PREFIX = ChatColor.GOLD + "[LegendaryArmor] " + ChatColor.AQUA;
	
	public LegendaryCommands()
	{
		main = Main.getInstance();
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("legend"))
		{
			if (args.length == 2)
			{
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
				LegendaryArmor armor = LegendaryArmor.armor(args[1]);
				
				if (player != null && player.hasPlayedBefore())
				{
					if (armor != null)
					{
						if (player.isOnline())
						{
							player.getPlayer().sendMessage(PRETTY_PREFIX + "To collect your legendary, do: " + ChatColor.BOLD + "/legend claim");
						} else
						{
						
						}
					} else
						sender.sendMessage(PREFIX + "Could not find specified armor");
				} else
					sender.sendMessage(PREFIX + "Could not find specified player");
			} else
				sender.sendMessage(PREFIX + "Usage: /legend <player> <legendary>");
				
			if (sender instanceof ConsoleCommandSender)
			{
				if (args.length == 2)
				{
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
					LegendaryArmor armor = LegendaryArmor.armor(args[1]);
					
					if (player.hasPlayedBefore() || player.isOnline())
					{
						if (armor != null)
						{
							if (player.isOnline())
							{
								sender.sendMessage(PREFIX + "You have a new legendary item to redeem!" + ChatColor.GOLD + " /legend redeem");
							} else
							{
								Map<UUID, List<LegendaryArmor>> data = main.getAwaitingLegendaries().getAwaiting();
								
								if (data.containsKey(player.getUniqueId()))
								{
									List<LegendaryArmor> previousValues = data.get(player.getUniqueId());
									previousValues.add(armor);
									data.put(player.getUniqueId(), previousValues);
								}
							}
						} else
							sender.sendMessage(PREFIX + "Could not find specified armor");
					} else
						sender.sendMessage(PREFIX + "Could not find specified player");
				} else
					sender.sendMessage(PREFIX + "Usage: /legend <player> <legendary> or /legend");
			} else if (sender instanceof Player)
			{
				if (args.length == 1)
				{
					if (args[0].equalsIgnoreCase("redeem"))
					{
						if (main.getAwaitingLegendaries().getAwaiting().containsKey(((Player) sender).getUniqueId()))
						{
							List<LegendaryArmor> toGive = main.getAwaitingLegendaries().getAwaiting().get(((Player) sender).getUniqueId());
							
							for (LegendaryArmor armor : toGive)
							{
								((Player) sender).getInventory().addItem(armor.getItem());
							}
							
							main.getAwaitingLegendaries().getAwaiting().remove(((Player) sender).getUniqueId());
							sender.sendMessage(PREFIX + "You received your items.");
						}
						sender.sendMessage(PREFIX + "You have no redeemable legendary items.");
					} else
						sender.sendMessage(PREFIX + "Usage: /legend redeem");
				}
			}
		}
		
		return true;
	}
}