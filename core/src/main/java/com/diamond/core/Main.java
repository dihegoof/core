package com.diamond.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.diamond.core.mysql.MySql;
import com.diamond.core.player.BukkitPlayerManager;

import lombok.Getter;

/**
 *
 * @author Dihego
 */

public class Main extends JavaPlugin {
	
	@Getter
	static Plugin plugin = null;
	@Getter
	static MySql mysql = null;
	static boolean debug = true;
	static String[] listeners = { 
			"player.listener"};
	static String[] commands = { "commands" };
	
    @Override
    public void onEnable() {
    	plugin = this;
    	debug("Plugin ativado com sucesso!");
    	mysql = new MySql("127.0.0.1", "root", "", "server");
    	mysql.open();
    	mysql.create();
    	BukkitPlayerManager.getInstance().load();
    	for(String loaderListener : listeners) { 
			ClassGetter.getInstance().events("com.diamond.core." + loaderListener);
		}
		for(String loaderCommands : commands) { 
			ClassGetter.getInstance().commands("com.diamond.core." + loaderCommands);
		}
    }

    @Override
    
    public void onDisable() {
    	debug("Plugin desativado com sucesso!");
    	plugin = null;
    }
    
    public static void debug(String... messages) { 
    	if(!debug) return;
    	for(String lines : messages) {
    		Bukkit.getConsoleSender().sendMessage(lines);
    	}
    }
}
