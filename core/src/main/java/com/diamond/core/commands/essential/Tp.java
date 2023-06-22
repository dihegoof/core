package com.diamond.core.commands.essential;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.util.Utils;

public class Tp extends Utils implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender;
			BukkitPlayer bpSender = BukkitPlayerManager.getInstance().get(player.getName());
			if(bpSender == null) {
				player.kickPlayer("§cVocê foi §lKICKADO§c!\n\n§cSua conta encontra-se com problemas!");
				return true;
			}
			if(!bpSender.hasPermission("core.cmd.tp")) {
				sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
				return true;
			}
			if(args.length == 0) { 
				sintaxCommand(player, "§c/" + label + " <x> <y> <z>",
								      "§c/" + label + " <jogador> <x> <y> <z>");
				return true;
			} else if(args.length == 3) {
				if(isInteger(args[0]) && isInteger(args[1]) && isInteger(args[2])) { 
					player.teleport(new Location(player.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2])));
					sendMessage(player, false, "§aTeleportado à §7" + args[0] + "§a, §7" + args[1] + "§a, §7" + args[2] + "§a!");
				} else { 
					sendMessage(player, false, "§cCoordenada inválida!");
				}
				return true;
			} else if(args.length == 4) {
				BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[0]);
				if(bp == null || !bp.isOnline()) {
					sendMessage(player, false, "§cEste jogador não foi encontrado!");
					return true;
				}
				if(isInteger(args[1]) && isInteger(args[2]) && isInteger(args[3])) { 
					bp.getPlayer().teleport(new Location(player.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3])));
					sendMessage(bp.getPlayer(), false, "§aTeleportado à §7" + args[1] + "§a, §7" + args[2] + "§a, §7" + args[3] + "§a!");
					sendMessage(player, false, "§7" + bp.getName() + " §ateleportado à §7" + args[1] + "§a, §7" + args[2] + "§a, §7" + args[3] + "§a!");
				} else { 
					sendMessage(player, false, "§cCoordenada inválida!");
				}
				return true;
			}
		} else { 
			Main.debug("Console tentou digitar o comando '/tp'!");
		}
		return false;
	}
}
