package com.enjin.devection.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.awaiting.AwaitingLegendaries;
import com.enjin.devection.commands.LegendaryCommands;
import com.enjin.devection.events.ArmorEquipListener;
import com.enjin.devection.legendary.LegendaryItem;
import com.enjin.devection.legendary.LegendaryType;
import com.enjin.devection.listener.LegendaryListener;

public class Main extends JavaPlugin
{
	private static Main instance;
	
	private AwaitingLegendaries awaitingLegendaries;
	
	public static String PREFIX = "[LegendaryArmor] ";
	public static String PRETTY_PREFIX = ChatColor.GOLD + "[LegendaryArmor] " + ChatColor.AQUA;
	
	public void onEnable()
	{
		instance = this;
		
		awaitingLegendaries = new AwaitingLegendaries();
		
		registerCommands();
		registerListeners();
	}
	
	public void onDisable()
	{
		awaitingLegendaries.save();
		awaitingLegendaries.clear();
	}
	
	public boolean hasLegendary(Player player, LegendaryItem legendary)
	{
		if (legendary.getType() == LegendaryType.ITEM)
		{
			for (ItemStack item : player.getInventory().getContents())
			{
				if (item != null)
				{
					if (legendary == LegendaryItem.fromItemStack(item))
					{
						return true;
					}
				}
			}
		} else if (legendary.getType() == LegendaryType.ARMOR)
		{
			ItemStack item = legendary.getInventoryItem(player);
			
			if (item != null)
			{
				if (legendary == LegendaryItem.fromItemStack(item))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public AwaitingLegendaries getAwaitingLegendaries()
	{
		return awaitingLegendaries;
	}
	
	private void registerCommands()
	{
		getCommand("legend").setExecutor(new LegendaryCommands());
	}
	
	private void registerListeners()
	{
		getServer().getPluginManager().registerEvents(new LegendaryListener(), this);
		getServer().getPluginManager().registerEvents(new ArmorEquipListener(), this);
	}
	
	public static Main getInstance()
	{
		return instance;
	}
}