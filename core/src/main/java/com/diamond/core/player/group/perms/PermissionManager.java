package com.diamond.core.player.group.perms;

import java.util.ArrayList;
import java.util.List;

import com.diamond.core.player.BukkitPlayerManager;

import lombok.Getter;

public class PermissionManager {

	@Getter
	static BukkitPlayerManager instance = new BukkitPlayerManager();
	static List<Permission> storagePermissions = new ArrayList<>();
	
	public void add(Permission permission) { 
		if(!storagePermissions.contains(permission)) { 
			storagePermissions.add(permission);
		}
	}
	
	public void remove(Permission permission) { 
		if(storagePermissions.contains(permission)) { 
			storagePermissions.remove(permission);
		}
	}
	
	public void load() {
		add(new Permission("core.cmd.varconta", "Comando para utilizar o var conta"));
		add(new Permission("core.cmd.varcontagrupo", "Comando para utilizar o var conta para setar grupos"));
		add(new Permission("core.cmd.vartag", "Comando para utilizar o var tag em geral"));
		add(new Permission("core.cmd.vargrupo", "Comando para utilizar o var grupo em geral"));
	}
}
