package com.diamond.core.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;
import com.diamond.core.player.ranking.Rank;
import com.diamond.core.player.tags.Tag;

import lombok.Getter;

public class BukkitPlayerManager {
	
	@Getter
	static BukkitPlayerManager instance = new BukkitPlayerManager();
	static List<BukkitPlayer> storageBukkitPlayers = new ArrayList<>();
	
	public void add(BukkitPlayer bp) { 
		if(!storageBukkitPlayers.contains(bp)) { 
			storageBukkitPlayers.add(bp);
		}
	}
	
	public void remove(BukkitPlayer bp) { 
		if(storageBukkitPlayers.contains(bp)) { 
			storageBukkitPlayers.remove(bp);
		}
	}
	
	public void load() { 
		int amount = 0;
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_SELECT_ALL.getQuery());;
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) { 
				add(new BukkitPlayer(UUID.fromString(rs.getString("uniqueid")), rs.getString("nickname"), rs.getString("address"), Group.valueOf(rs.getString("group")), rs.getLong("timegroup"), rs.getLong("joinin"), rs.getLong("lastsee"), Tag.valueOf(rs.getString("tag")), Rank.valueOf(rs.getString("rank")), Arrays.asList(rs.getString("permissions")), rs.getInt("xp"), rs.getInt("coins")));
				amount++;
			}
			if(amount > 0) 
				Main.debug("Carregado " + amount + " conta(s)");
		} catch (Exception e) {
			Main.debug("Ocorreu um erro ao carregar as contas!");
		}
	}
	
	public BukkitPlayer get(UUID uniqueId) { 
		for(BukkitPlayer bps : storageBukkitPlayers) { 
			if(bps.getUniqueId().equals(uniqueId)) { 
				return bps;
			}
		}
		return null;
	}
	
	public BukkitPlayer get(String nickname) { 
		for(BukkitPlayer bps : storageBukkitPlayers) { 
			if(bps.getName().equals(nickname)) { 
				return bps;
			}
		}
		return null;
	}
}
