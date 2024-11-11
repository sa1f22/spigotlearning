package com.sa1f.spigotlearning;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.UUID;

public class Togglelistener implements Listener {
	HashMap<UUID, Boolean> playerchatStatus = new HashMap<>(); //key is the UUID, which is mapped on to value Boolean (true or false).
	//The player's UUID either has a true or false value.

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (e.getHand().equals(EquipmentSlot.HAND)) {
			if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR)) {
				UUID playerId = player.getUniqueId();
				boolean isEnabled = playerchatStatus.getOrDefault(playerId, true);

				if (isEnabled) {
					playerchatStatus.put(playerId, false);
					player.sendMessage("Chat disabled");
				} else {
					playerchatStatus.put(playerId, true);
					player.sendMessage("Chat enabled");
				}
			}

		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		UUID playerId = player.getUniqueId();
		boolean isEnabled = playerchatStatus.getOrDefault(playerId, true);
		if (!isEnabled) {
			e.setCancelled(true);
		}
	}
}
