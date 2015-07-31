package com.enjin.devection.awaiting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import com.enjin.devection.armor.LegendaryArmor;
import com.enjin.devection.main.Main;

public class AwaitingLegendaries
{
	private File awaitingFile;
	private YamlConfiguration awaitingConfig;
	
	private Map<UUID, List<LegendaryArmor>> awaitingLegendaries = new HashMap<UUID, List<LegendaryArmor>>();
	
	public AwaitingLegendaries()
	{
		awaitingFile = new File(Main.getInstance().getDataFolder(), "awaiting.yml");
		awaitingFile.getParentFile().mkdirs();
		
		awaitingConfig = YamlConfiguration.loadConfiguration(awaitingFile);
		
		Object awaitingMap = awaitingConfig.get("awaiting_legendaries");
		
		if (awaitingMap instanceof Map)
		{
			deserialize(awaitingMap);
		}
	}
	
	public Map<UUID, List<LegendaryArmor>> getAwaiting()
	{
		return awaitingLegendaries;
	}
	
	public void save()
	{
		awaitingConfig.set("awaiting_legendaries", serialize());
		
		try
		{
			awaitingConfig.save(awaitingFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		awaitingLegendaries.clear();
	}
	
	private Map<String, List<String>> serialize()
	{
		Map<String, List<String>> awaitingLegendariesConfig = new HashMap<String, List<String>>();
		
		for (Map.Entry<UUID, List<LegendaryArmor>> awaiting : awaitingLegendaries.entrySet())
		{
			List<String> list = new ArrayList<String>();
			
			for (LegendaryArmor armor : awaiting.getValue())
			{
				list.add(armor.name());
			}
			
			awaitingLegendariesConfig.put(awaiting.getKey().toString(), list);
		}
		
		return awaitingLegendariesConfig;
	}
	
	@SuppressWarnings("unchecked")
	private void deserialize(Object awaitingMap)
	{
		Map<String, List<String>> awaitingLegendariesConfig = (Map<String, List<String>>) awaitingMap;
		
		for (Map.Entry<String, List<String>> awaiting : awaitingLegendariesConfig.entrySet())
		{
			List<LegendaryArmor> list = new ArrayList<LegendaryArmor>();
			
			for (String armor : awaiting.getValue())
			{
				list.add(LegendaryArmor.valueOf(armor));
			}
			
			awaitingLegendaries.put(UUID.fromString(awaiting.getKey()), list);
		}
	}
}