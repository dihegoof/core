package com.diamond.core.commands.essential;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.util.Utils;

public class Gamemode extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender;
			BukkitPlayer bpSender = BukkitPlayerManager.getInstance().get(player.getName());
			if(bpSender == null) {
				player.kickPlayer("§cVocê foi §lKICKADO§c!\n\n§cSua conta encontra-se com problemas!");
				return true;
			}
			if(!bpSender.hasPermission("core.cmd.gm")) {
				sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
				return true;
			}
			if(args.length == 0) { 
				sintaxCommand(player, "§c/" + label + " <modo de jogo>",
									  "§c/" + label + " <modo de jogo> <jogador>");
				return true;
			} else if(args.length == 1) { 
				switch(args[0]) {
				case "0":
					player.setGameMode(GameMode.SURVIVAL);
					sendMessage(player, false, "§aModo de jogo atualziado para sobrevivência!");
					break;
				case "1":
					player.setGameMode(GameMode.CREATIVE);
					sendMessage(player, false, "§aModo de jogo atualziado para criativo!");
					break;
				default:
					sendMessage(player, false, "§cModo de jogo não encontrado!");
					break;
				}
				return true;
			} else if(args.length == 2) { 
				BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
				if(bp == null || !bp.isOnline()) {
					sendMessage(player, false, "§cEste jogador não foi encontrado!");
					return true;
				}
				switch(args[0]) {
				case "0":
					bp.getPlayer().setGameMode(GameMode.SURVIVAL);
					sendMessage(bp.getPlayer(), false, "§aModo de jogo atualizado para sobrevivência!");
					sendMessage(player, false, "§aModo de jogo alterado para §7" + bp.getName() + "§a!");
					break;
				case "1":
					bp.getPlayer().setGameMode(GameMode.CREATIVE);
					sendMessage(bp.getPlayer(), false, "§aModo de jogo atualizado para criativo!");
					sendMessage(player, false, "§aModo de jogo alterado para §7" + bp.getName() + "§a!");
					break;
				default:
					sendMessage(player, false, "§cModo de jogo não encontrado!");
					break;
				}
				return true;
			}
		} else { 
			Main.debug("Console tentou digitar o comando '/gm'!");
		}
		return false;
	}
}
