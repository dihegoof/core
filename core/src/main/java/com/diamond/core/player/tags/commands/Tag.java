package com.diamond.core.player.tags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.player.tags.TagManager;
import com.diamond.core.util.Utils;

public class Tag extends Utils implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender;
			BukkitPlayer bpSender = BukkitPlayerManager.getInstance().get(player.getName());
			if(bpSender == null) {
				player.kickPlayer("§cVocê foi §lKICKADO§c!\n\n§cSua conta encontra-se com problemas!");
				return true;
			}
			if(args.length == 0) { 
				StringBuilder stringBuilder = null;
				if(!bpSender.getTags().isEmpty()) {
					stringBuilder = new StringBuilder();
					for(com.diamond.core.player.tags.Tag ta : bpSender.getTags()) { 
						stringBuilder.append(ta.getName() + ", ");
					}
				}
				sendMessage(player, false, "§aTags disponíveis: §f" + (stringBuilder != null ? stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2) : "Nenhuma tag") + "§a");
				return true;
			} else if(args.length == 1) { 
				com.diamond.core.player.tags.Tag tag = TagManager.getInstance().get(args[0]);
				if(tag == null) {
					sendMessage(player, false, "§cEsta tag não foi encontrada!");
					return true;
				}
				if(!bpSender.getTags().contains(tag)) { 
					sendMessage(player, false, "§cVocê não tem permissão para utilizar esta tag!");
					return true;
				}
				tag.applyTag(bpSender);
				sendMessage(player, false, "§aTag atualizada para §7" + tag.getName() + "§a!");
				return true;
			}
		} else { 
			Main.debug("Console tentou digitar o comando '/var'!");
		}
		return false;
	}
}
