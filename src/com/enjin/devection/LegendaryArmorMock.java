package com.enjin.devection;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LegendaryArmorMock extends JavaPlugin
{
	public void onEnable()
	{
		getLogger().info("Legendary Armor has been enabled successfully!");
	}
	
	public void onDisable()
	{
		getLogger().info("Legendary Armor has been disabled successfully!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		
		// Display Name: Apollos Crest (Bold Red)
		// Enchantments:
		// Protection 5
		// Fire Protection 4
		// Unbreaking 3
		// Respiration 3
		// Aqua Affinity 1
		// Looting 3
		// Design image: http://puu.sh/jiXxX/9d23ea1741.png
		ItemStack ApolloHelm = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta ApolloHelmMeta = (ItemMeta) ApolloHelm.getItemMeta();
		
		ApolloHelmMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Apollos Crest");
		ApolloHelmMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, false);
		ApolloHelmMeta.addEnchant(Enchantment.PROTECTION_FIRE, 4, false);
		ApolloHelmMeta.addEnchant(Enchantment.DURABILITY, 3, false);
		ApolloHelmMeta.addEnchant(Enchantment.OXYGEN, 3, false);
		ApolloHelmMeta.addEnchant(Enchantment.WATER_WORKER, 1, false);
		ApolloHelmMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, false);
		
		List<String> ApolloHelmLore = new ArrayList<String>();
		
		ApolloHelmLore.add(ChatColor.DARK_AQUA + "Legend Helmet");
		ApolloHelmLore.add(ChatColor.DARK_PURPLE + "#" + player.getDisplayName());
		ApolloHelmLore.add(ChatColor.DARK_GREEN + "Infused with Night Vision");
		// ApolloHelmLore.add(ChatColor.GREEN + );
		ApolloHelmMeta.setLore(ApolloHelmLore);
		ApolloHelm.setItemMeta(ApolloHelmMeta);
		
		ItemStack AegisChestPlate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta AegisChestMeta = (ItemMeta) AegisChestPlate.getItemMeta();
		
		AegisChestMeta.setDisplayName(ChatColor.DARK_GREEN + "Aegis");
		AegisChestPlate.setItemMeta(AegisChestMeta);
		
		ItemStack EtherealsLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta EtherealsLeggingsMeta = (ItemMeta) EtherealsLeggings.getItemMeta();
		
		EtherealsLeggingsMeta.setDisplayName(ChatColor.DARK_GRAY + "Ethereals");
		EtherealsLeggings.setItemMeta(EtherealsLeggingsMeta);
		
		ItemStack HermesBoots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta HermesBootsMeta = (ItemMeta) HermesBoots.getItemMeta();
		
		HermesBootsMeta.setDisplayName(ChatColor.GOLD + "Hermes");
		HermesBoots.setItemMeta(HermesBootsMeta);
		
		if (cmd.getName().equalsIgnoreCase("legend"))
		{
			if (args.length == 0)
			{
				sender.sendMessage("Please specify what kit you want - Apollos/Aegis/Ethereals/Hermes");
			} else if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("Apollos"))
					player.getInventory().addItem(ApolloHelm);
				
				if (args[0].equalsIgnoreCase("Aegis"))
					player.getInventory().addItem(AegisChestPlate);
				
				if (args[0].equalsIgnoreCase("Ethereals"))
					player.getInventory().addItem(EtherealsLeggings);
				
				if (args[0].equalsIgnoreCase("Hermes"))
					player.getInventory().addItem(HermesBoots);
				
				player.updateInventory();
			}
		}
		
		return true;
	}
	
	@EventHandler
	public void armorcheck(InventoryClickEvent event)
	{
		// Reference for EventHandler
		
		ItemStack ApolloHelm = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta ApolloHelmMeta = (ItemMeta) ApolloHelm.getItemMeta();
		ApolloHelmMeta.setDisplayName(ChatColor.DARK_RED + "Apollos Crest");
		ApolloHelm.setItemMeta(ApolloHelmMeta);
		
		ItemStack AegisChestPlate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta AegisChestMeta = (ItemMeta) AegisChestPlate.getItemMeta();
		AegisChestMeta.setDisplayName(ChatColor.DARK_GREEN + "Aegis");
		AegisChestPlate.setItemMeta(AegisChestMeta);
		
		ItemStack EtherealsLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta EtherealsLeggingsMeta = (ItemMeta) EtherealsLeggings.getItemMeta();
		EtherealsLeggingsMeta.setDisplayName(ChatColor.DARK_GRAY + "Ethereals");
		EtherealsLeggings.setItemMeta(EtherealsLeggingsMeta);
		
		ItemStack HermesBoots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta HermesBootsMeta = (ItemMeta) HermesBoots.getItemMeta();
		HermesBootsMeta.setDisplayName(ChatColor.GOLD + "Hermes");
		HermesBoots.setItemMeta(HermesBootsMeta);
		
		Player player = (Player) event.getWhoClicked();
		
		if (player.getInventory().getHelmet() == null)
		{
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		} else if (player.getInventory().getHelmet().equals(ApolloHelm))
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
		} else
		{
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		
		if (player.getInventory().getChestplate() == null)
		{
			player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		} else if (player.getInventory().getChestplate().equals(AegisChestPlate))
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
		} else
		{
			player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		}
		
		if (player.getInventory().getLeggings() == null)
		{
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		} else if (player.getInventory().getLeggings().equals(EtherealsLeggings))
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		} else
		{
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		
		if (player.getInventory().getBoots() == null)
		{
			player.removePotionEffect(PotionEffectType.SPEED);
		} else if (player.getInventory().getBoots().equals(HermesBoots))
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
		} else
		{
			player.removePotionEffect(PotionEffectType.SPEED);
		}
		
	}
}