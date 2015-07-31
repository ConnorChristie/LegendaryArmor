package com.enjin.devection.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.awaiting.AwaitingLegendaries;
import com.enjin.devection.commands.LegendaryCommands;
import com.enjin.devection.events.ArmorEquipListener;
import com.enjin.devection.legendary.LegendaryItems;
import com.enjin.devection.listener.LegendaryListener;

public class Main extends JavaPlugin
{
	private static Main instance;
	
	private LegendaryItems legendaryItems;
	private AwaitingLegendaries awaitingLegendaries;
	
	public static String PREFIX = "[LegendaryArmor] ";
	public static String PRETTY_PREFIX = ChatColor.GOLD + "[LegendaryArmor] " + ChatColor.AQUA;
	
	public void onEnable()
	{
		instance = this;
		
		loadLegendaries();
		
		registerCommands();
		registerListeners();
	}
	
	public void onDisable()
	{
		awaitingLegendaries.save();
		awaitingLegendaries.clear();
	}
	
	public LegendaryItems getLegendaryItems()
	{
		return legendaryItems;
	}
	
	public AwaitingLegendaries getAwaitingLegendaries()
	{
		return awaitingLegendaries;
	}
	
	private void loadLegendaries()
	{
		legendaryItems = new LegendaryItems();
		awaitingLegendaries = new AwaitingLegendaries();
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