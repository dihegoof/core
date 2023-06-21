package com.diamond.core.player.tags.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.diamond.core.player.tags.TagUtils;

public class TagListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		TagUtils.getInstance().updateTeamsToPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) { 
		TagUtils.getInstance().removeTag(event.getPlayer().getName());
	}
}
