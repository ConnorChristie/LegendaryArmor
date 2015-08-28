package com.enjin.devection.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
		
		loadConfig();
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
	
	private void loadConfig()
	{
		saveDefaultConfig();
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
	
	/*
	 * Putting in legendary_data.yml to log username/UUID with legendary item
	 * type and 8-digit hex code for each instance of the /legend <player>
	 * <type> command.
	 */
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	public void reloadCustomConfig()
	{
		try
		{
			if (customConfigFile == null)
			{
				customConfigFile = new File(getDataFolder(), "legendary_log.yml");
			}
			
			if (!customConfigFile.exists())
			{
				customConfigFile.getParentFile().mkdirs();
				customConfigFile.createNewFile();
			}
			
			customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
			
			//Dont need that
			//Delete that, reply when done
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCustomConfig()
	{
		if (customConfig == null)
		{
			reloadCustomConfig();
		}
		
		return customConfig;
	}
	
	public void saveCustomConfig()
	{
		if (customConfig == null || customConfigFile == null)
		{
			return;
		}
		try
		{
			getCustomConfig().save(customConfigFile);
		} catch (IOException ex)
		{
			getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
		}
	}
}