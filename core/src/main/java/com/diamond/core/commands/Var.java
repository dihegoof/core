package com.diamond.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.player.group.Group;
import com.diamond.core.player.group.GroupManager;
import com.diamond.core.player.tags.Tag;
import com.diamond.core.player.tags.TagManager;
import com.diamond.core.util.TimeManager;
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
			if(!bpSender.hasPermission("core.cmd.var")) {
				sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
				return true;
			}
			if(args.length == 0) {
				help(label, "", sender);
				return true;
			}
			switch (args[0]) {
			case "conta":
				if(!bpSender.hasPermission("core.cmd.varconta")) {
					sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
					return true;
				}
				if(args.length == 1) { 
					help(label, "conta", sender);
					return true;
				} else if(args.length == 3) { 
					BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
					if(bp == null) { 
						sendMessage(player, false, "§cEsta conta não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("info")) { 
						//abrir inventário da conta
						try {
							bp.save();
							sendMessage(player, false, "§aSalvo!");
						} catch (Exception e) {
							sendMessage(player, false, "§cErro!");
						}
						
					}
					return true;
				} else if(args.length == 4) { 
					BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
					if(bp == null) { 
						sendMessage(player, false, "§cEsta conta não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("grupo")) { 
						if(!bpSender.hasPermission("core.cmd.varcontagrupo")) {
							sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
							return true;
						}
						Group group = GroupManager.getInstance().get(args[3]);
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						if(group.isTemporary()) {
							sendMessage(player, false, "§cEste grupo é temporário, é preciso definir com um tempo de expiração!");
							return true;
						}
 						bp.setGroup(group.getName());
						if(bp.isOnline()) { 
							sendMessage(bp.getPlayer(), true, "§aSeu grupo foi alterado para §7" + group.getName() + "§a!");
						} else { 
							bp.save();
						}
						sendMessage(player, false, "§aGrupo de §7" + bp.getName() + " §aalterado para §f" + group.getName() + "§a!");
					}
					return true;
				} else if(args.length == 5) { 
					BukkitPlayer bp = BukkitPlayerManager.getInstance().get(args[1]);
					if(bp == null) { 
						sendMessage(player, false, "§cEsta conta não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("grupo")) { 
						if(!bpSender.hasPermission("core.cmd.varcontagrupo")) {
							sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
							return true;
						}
						Group group = GroupManager.getInstance().get(args[3]);
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						if(!group.isTemporary()) {
							sendMessage(player, false, "§cEste grupo não é temporário!");
							return true;
						}
						if(bp.isGroupPermanent()) { 
							bp.setLastGroup(bp.getGroup().getName());
						}
						long timeGroup = TimeManager.getInstance().getTime(args[4]);
						if(timeGroup <= 0) {
							sendMessage(sender, false, "§cTempo inválido!");
							return true;
						}
						bp.setGroup(group.getName());
						bp.setTimeGroup(timeGroup);
						if(bp.isOnline()) { 
							sendMessage(bp.getPlayer(), true, "§aSeu grupo foi alterado para §7" + group.getName() + "§a com duração de §f" + TimeManager.getInstance().getTime(timeGroup) + "§a!");
						} else { 
							bp.save();
						}
						sendMessage(player, false, "§aGrupo de §7" + bp.getName() + " §aalterado para §7" + group.getName() + "§a com duração de §f" + TimeManager.getInstance().getTime(timeGroup) + "§a!");
					} else if(args[2].equalsIgnoreCase("perm")) { 
						List<String> permissions = bp.getPermissions();
						if(args[3].equalsIgnoreCase("add")) { 
							if(permissions.contains(args[4])) {
								sendMessage(player, false, "§cEsta permissão já contém na lista!");
								return true;
							}
							permissions.add(args[4]);
						} else if(args[3].equalsIgnoreCase("remover")) { 
							if(!permissions.contains(args[4])) {
								sendMessage(player, false, "§cEsta permissão não contém na lista!");
								return true;
							}
							permissions.remove(args[4]);
						}
						bp.setPermissions(permissions);
						sendMessage(player, false, "§aLista de permissão de §7" + bp.getName() + " §aalterada!");
					}
					return true;
				} else { 
					help(label, "conta", sender);
				}
				break;
			case "tag":
				if(!bpSender.hasPermission("core.cmd.vartag")) {
					sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
					return true;
				}
				if(args.length == 1) { 
					help(label, "tag", sender);
					return true;
				} else if(args.length == 3) { 
					Tag tag = TagManager.getInstance().get(args[1]);
					if(args[2].equalsIgnoreCase("info")) { 
						if(tag == null) {
							sendMessage(player, false, "§cEsta tag não foi encontrada!");
							return true;
						}
						sendMessage(player, true, 
								"§aInfo da tag " + tag.getName() + "§a:",
								"§aOrdem: §7" + tag.getOrder(),
								"§aPrefixo: " + tag.getPrefix(),
								"§aPermissão: " + tag.getPermission(),
								"§aExclusiva? §7" + (tag.isExclusive() ? "Sim" : "Não"));
					} else if(args[2].equalsIgnoreCase("criar")) { 
						if(tag != null) { 
							sendMessage(player, false, "§cJá existe uma tag com este nome!");
							return true;
						}
						tag = new Tag(args[1], "NRE", "tag." + args[1], 1, false);
						TagManager.getInstance().add(tag);
						sendMessage(player, false, "§aTag §7" + tag.getName() + " §acriada com sucesso!");
					} else if(args[2].equalsIgnoreCase("deletar")) { 
						if(tag == null) {
							sendMessage(player, false, "§cEsta tag não foi encontrada!");
							return true;
						}
						sendMessage(player, false, "§cTag §7" + tag.getName() + " §cdeletada com sucesso!");
						tag.delete();
						TagManager.getInstance().remove(tag);
					} else if(args[2].equalsIgnoreCase("definirexclusiva")) { 
						if(tag == null) {
							sendMessage(player, false, "§cEsta tag não foi encontrada!");
							return true;
						}
						tag.setExclusive(tag.isExclusive() ? false : true);
						sendMessage(player, false, "§aExclusividade da tag §7" + tag.getName() + " §aalterada!");
					}
					return true;
				} else if(args.length == 4) { 
					Tag tag = TagManager.getInstance().get(args[1]);
					if(tag == null) {
						sendMessage(player, false, "§cEsta tag não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("editarordem")) { 
						if(isInteger(args[3])) { 
							tag.setOrder(Integer.valueOf(args[3]));
							sendMessage(player, false, "§aOrdem da tag §7" + tag.getName() + " §aalterada para §f" + tag.getOrder() + "§a!");
						} else { 
							sendMessage(player, false, "§cNúmero inválido!");
						}
					} else if(args[2].equalsIgnoreCase("editarperm")) { 
						tag.setPermission(args[3]);
						sendMessage(player, false, "§aPermissão da tag §7" + tag.getName() + " §aalterada para §f" + tag.getPermission() + "§a!");
					}
					return true;
				} else if(args.length >= 3) { 
					Tag tag = TagManager.getInstance().get(args[1]);
					if(tag == null) {
						sendMessage(player, false, "§cEsta tag não foi encontrada!");
						return true;
					}
					if(args[2].equalsIgnoreCase("editarprefixo")) { 
						String prefix = createArgs(3, args, label, true);
						tag.setPrefix(prefix);
						sendMessage(player, false, "§aPrefixo da tag §7" + tag.getName() + " §aalterado com sucesso!");
					}
					return true;
				} else { 
					help(label, "tag", sender);
				}
				break;
			case "grupo":
				if(!bpSender.hasPermission("core.cmd.vargrupo")) {
					sendMessage(player, false, "§cVocê não tem permissão para fazer isso!");
					return true;
				}
				if(args.length == 1) { 
					help(label, "grupo", sender);
					return true;
				} else if(args.length == 3) { 
					Group group = GroupManager.getInstance().get(args[1]);
					if(args[2].equalsIgnoreCase("info")) { 
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						StringBuilder stringBuilder = null;
						if(!group.getPermissions().isEmpty()) {
							stringBuilder = new StringBuilder();
							for(String st : group.getPermissions()) { 
								stringBuilder.append(st + ", ");
							}
						}
						sendMessage(player, true, 
								"§aInfo do grupo " + group.getName() + "§a:",
								"§aPermissões: §7" + stringBuilder != null ? stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2) : "Nenhuma permissão adicional",
								"§aExclusivo? §7" + (group.isTemporary() ? "Sim" : "Não"),
								"§aPadrão? §7" + (group.isDefaulted() ? "Sim" : "Não")
								);
					} else if(args[2].equalsIgnoreCase("criar")) { 
						if(group != null) { 
							sendMessage(player, false, "§cJá existe um grupo com este nome!");
							return true;
						}
						group = new Group(args[1], new ArrayList<>(), false, false);
						GroupManager.getInstance().add(group);
						sendMessage(player, false, "§aGrupo §7" + group.getName() + " §acriado com sucesso!");
						TagManager.getInstance().add(new Tag(group.getName(), "NRE", "tag." + group.getName(), 1, false));
					} else if(args[2].equalsIgnoreCase("deletar")) { 
						if(group == null) {
							sendMessage(player, false, "§cEsta grupo não foi encontrado!");
							return true;
						}
						sendMessage(player, false, "§cGrupo " + group.getName() + " §cdeletado com sucesso!");
						group.delete();
						GroupManager.getInstance().remove(group);
					} else if(args[2].equalsIgnoreCase("definirtemporario")) { 
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						group.setTemporary(group.isTemporary() ? false : true);
						sendMessage(player, false, "§aTemporariedado do grupo §7" + group.getName() + " §aalterado!");
					} else if(args[2].equalsIgnoreCase("definirpadrao")) { 
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						group.setDefaulted(group.isDefaulted() ? false : true);
						sendMessage(player, false, "§aPadronização do grupo §7" + group.getName() + " §aalterado!");
					}
					return true;
				} else if(args.length == 5) { 
					if(args[2].equalsIgnoreCase("perm")) {
						Group group = GroupManager.getInstance().get(args[1]);
						if(group == null) {
							sendMessage(player, false, "§cEste grupo não foi encontrado!");
							return true;
						}
						List<String> permissions = group.getPermissions();
						if(args[3].equalsIgnoreCase("add")) { 
							if(permissions.contains(args[4])) {
								sendMessage(player, false, "§cEsta permissão já contém na lista!");
								return true;
							}
							permissions.add(args[4]);
						} else if(args[3].equalsIgnoreCase("remove")) { 
							if(!permissions.contains(args[4])) {
								sendMessage(player, false, "§cEsta permissão não contém na lista!");
								return true;
							}
							permissions.remove(args[4]);
						}
						group.setPermissions(permissions);
						sendMessage(player, false, "§aLista de permissão §7" + group.getName() + " §aalterada!");
					}
					return true;
				}
				break;
			default: 
				sendMessage(player, false, "§cEste argumento não foi encontrado!");
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
					"§c/" + label + " conta <jogador> info", 
					"§c/" + label + " conta <jogador> grupo <nome do grupo>",
					"§c/" + label + " conta <jogador> grupo <nome do grupo> <tempo>",
					"§c/" + label + " conta <jogador> perm <add, remover> <permissão>"
					);
		} else if(session.equalsIgnoreCase("tag")) { 
			sintaxCommand(sender, 
					"§c/" + label + " tag <nome da tag> info",
					"§c/" + label + " tag <nome da tag> criar",
					"§c/" + label + " tag <nome da tag> deletar",
					"§c/" + label + " tag <nome da tag> definirexclusiva",
					"§c/" + label + " tag <nome da tag> editarordem <nova ordem>",
					"§c/" + label + " tag <nome da tag> editarprefixo <nova prefixo>",
					"§c/" + label + " tag <nome da tag> editarperm <nova permissão>"
					);
		} else if(session.equalsIgnoreCase("grupo")) { 
			sintaxCommand(sender, 
					"§c/" + label + " grupo <nome do grupo> info",
					"§c/" + label + " grupo <nome do grupo> criar",
					"§c/" + label + " grupo <nome do grupo> deletar",
					"§c/" + label + " grupo <nome do grupo> definirtemporario",
					"§c/" + label + " grupo <nome do grupo> definirpadrao",
					"§c/" + label + " grupo <nome da grupo> perm <add, remover> <permissão>"
					);
		} else { 
			sintaxCommand(sender, 
					"§c/" + label + " conta",
					"§c/" + label + " tag",
					"§c/" + label + " grupo");
		}
	}
}
