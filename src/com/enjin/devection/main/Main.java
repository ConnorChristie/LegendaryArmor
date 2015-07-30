package com.enjin.devection.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.commands.LegendaryCommands;
import com.enjin.devection.events.ArmorEquipListener;
import com.enjin.devection.listener.LegendaryListener;

public class Main extends JavaPlugin
{
	/*
		Apollos (Helmet) - Night Vision
		Aegis (Chestplate) - Strength 2
		Ethereals (Leggings) - Invisibility
		Hermes (Boots) - Speed 2
	 */
	
	public void onEnable()
	{
		registerCommands();
		registerListeners();
	}
	
	public void onDisable()
	{
		
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
}