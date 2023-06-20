package com.diamond.core.player.group.perms;

import lombok.Getter;

@Getter
public class Permission {
	
	String permission, info;

	public Permission(String permission, String info) {
		this.permission = permission;
		this.info = info;
	}
}
