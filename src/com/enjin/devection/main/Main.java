package com.enjin.devection.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.awaiting.AwaitingLegendaries;
import com.enjin.devection.commands.LegendaryCommands;
import com.enjin.devection.events.ArmorEquipListener;
import com.enjin.devection.listener.LegendaryListener;

public class Main extends JavaPlugin
{
	private static Main instance;
	
	private AwaitingLegendaries awaitingLegendaries;
	
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