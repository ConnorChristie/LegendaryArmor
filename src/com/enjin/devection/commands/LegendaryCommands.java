package com.enjin.devection.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.enjin.devection.armor.LegendaryArmor;

public class LegendaryCommands implements CommandExecutor
{
	private String PREFIX = "[LegendaryArmor] ";
	private String PRETTY_PREFIX = ChatColor.GOLD + "[LegendaryArmor] " + ChatColor.AQUA;
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("legend"))
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
		}
		
		return true;
	}
}