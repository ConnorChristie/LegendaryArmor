package com.enjin.devection.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.commands.LegendaryCommands;
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
		getCommand("legend").setExecutor(new LegendaryCommands());
		getServer().getPluginManager().registerEvents(new LegendaryListener(), this);
	}
	
	public void onDisable()
	{
		
	}
}