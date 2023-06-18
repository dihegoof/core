package com.diamond.core.player;

import com.diamond.core.player.tags.Tag;

import lombok.Getter;

@Getter
public enum Group {
	
	MEMBRO("Membro", 1, false),
	VIP("Vip", 2, true),
	LIGHT("Light", 3, true),
	PREMIUM("Premium", 4, true),
	ULTRA("Ultra", 5, true),
	YOUTUBER("Youtuber", 6, true),
	STREAMER("Streamer", 6, true),
	HELPER("Ajudante", 7, false),
	YOUTUBERPLUS("YoutuberPlus", 8, true),
	TRIAL("Trial", 9, false),
	MOD("Moderador", 10, false),
	ADMIN("Admin", 11, false);
	
	String name;
	int power;
	boolean temporary;
	
	private Group(String name, int power, boolean temporary) {
		this.name = name;
		this.power = power;
		this.temporary = temporary;
	}
	
	public Tag getTag() { 
		return Tag.valueOf(this.toString());
	}
}
