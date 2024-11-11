package com.sa1f.spigotlearning;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

	// /message <player> <message>
	private Spigotlearning main;

	public MessageCommand(Spigotlearning main) {
		this.main = main;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (args.length < 2) {
			player.sendMessage("You need to provide a message!");
			return false;
		}

		if (args.length >= 2) {
			Player target = main.getServer().getPlayer(args[0]);
			if (target == null) {
				player.sendMessage("Player not found!");
				return false;
			}
			StringBuilder message = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				message.append(args[i]).append(" ");
			}
			target.sendMessage(player.getName() + " -> " + message);
			player.sendMessage("You -> " + message);
			main.recentmessages.put(target.getUniqueId(), player.getUniqueId());
			return true;
		}

		return false;
	}

}
