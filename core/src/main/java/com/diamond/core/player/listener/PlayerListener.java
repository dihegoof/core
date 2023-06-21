package com.diamond.core.player.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) { 
		BukkitPlayer bp = BukkitPlayerManager.getInstance().get(event.getPlayer().getUniqueId());
		if(bp == null) { 
			bp = new BukkitPlayer(event.getPlayer());
			BukkitPlayerManager.getInstance().add(bp);
		}
		bp.setOnline(true);
		bp.setPlayer(event.getPlayer());
		bp.check();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) { 
		BukkitPlayer bp = BukkitPlayerManager.getInstance().get(event.getPlayer().getUniqueId());
		if(bp == null) return;
		bp.save();
		bp.setOnline(false);
		bp.setPlayer(null);
		bp.setLastSee(System.currentTimeMillis());
	}
}
