package com.diamond.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.player.Group;
import com.diamond.core.util.Utils;

public class Var extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender;
			BukkitPlayer bpSender = BukkitPlayerManager.getInstance().get(player.getName());
			if(bpSender == null) {
				player.kickPlayer("§cVocê foi §lKICKADO§c!\n\n§cSua conta encontra-se com problemas!");
				return true;
			}
			switch (args[0]) {
			case "conta":
				if(args.length == 2) { 
					BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
					if(bp == null) {
						sendMessage(player, false, "§cEsta conta não foi encontrada!");
						return true;
					}
					Main.debug("Conta: " + bp.getName() + ", Rank: " + bp.getRank().getName() + ", Xp: " + bp.getXp(), " Entrou há: " + compareSimpleTime(bp.getJoinIn(), System.currentTimeMillis()));
					return true;
				} else if(args.length == 4) { 
					BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
					if(bp == null) {
						sendMessage(player, false, "§cEsta conta não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("grupo")) {
						Group group = Group.valueOf(args[3]);
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						bp.setGroup(group);
						sendMessage(player, false, "§aGrupo de §7" + bp.getName() + " §aalterado para §7" + group.getName() + "§a!");
					}
					return true;
				} else { 
					help(label, "conta", sender);
				}
				break;
			default:
				player.sendMessage("§cEste argumento não foi encontrado!");
				break;
			}
		} else { 
			Main.debug("Console tentou digitar o comando '/var'!");
		}
		return false;
	}
	
	void help(String label, String session, CommandSender sender) {
		if(session.equalsIgnoreCase("conta")) { 
			sintaxCommand(sender, 
					"§c/" + label + " conta <jogador>",
					"§c/" + label + " conta <jogador> grupo <nome do grupo>");
		}
	}
}
