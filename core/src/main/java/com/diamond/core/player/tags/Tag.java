package com.diamond.core.player.tags;

import lombok.Getter;

@Getter
public enum Tag {
	
	MEMBRO("Membro", "§7", "§7", 100, false),
	VIP("Vip", "§a", "§a§lVIP", 810, false),
	LIGHT("Light", "§9", "§9§lLIGHT", 830, false),
	PREMIUM("Premium", "§6", "§6§lPREMIUM", 850, false),
	ULTRA("Ultra", "§d", "§d§lULTRA", 899, false),
	YOUTUBER("Youtuber", "§b", "§b§lYT", 910, false),
	STREAMER("Streamer", "§2", "§2§lSTREAMER", 930, false),
	HELPER("Ajudante", "§e", "§e§lHELPER", 950, false),
	YOUTUBERPLUS("YoutuberPlus", "§3", "§3§lYTPLUS", 980, false),
	TRIAL("Trial", "§d", "§d§lTRIAL", 990, false),
	MOD("Moderador", "§5", "§5§lMOD", 995, false),
	ADMIN("Admin", "§c", "§c§lADMIN", 999, false);
	
	String name, color, prefix;
	int order;
	boolean exclusive;
	
	private Tag(String name, String color, String prefix, int order, boolean exclusive) {
		this.name = name;
		this.color = color;
		this.prefix = prefix;
		this.order = order;
		this.exclusive = false;
	}

	
}
