package com.diamond.core.player.tags;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;

import lombok.Getter;

public class TagManager {

	@Getter
	static TagManager instance = new TagManager();
	static List<Tag> storageTags = new ArrayList<>();
	
	public void add(Tag tag) { 
		if(!storageTags.contains(tag)) { 
			storageTags.add(tag);
		}
	}
	
	public void remove(Tag tag) { 
		if(storageTags.contains(tag)) { 
			storageTags.remove(tag);
		}
	}
	
	public void load() {
		int amount = 0;
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_SELECT_ALL.getQuery());;
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) { 
				add(new Tag(rs.getString("tagname"), rs.getString("tagprefix"), rs.getString("tagpermission"), rs.getInt("tagorder"), rs.getBoolean("tagexclusive")));
				amount++;
			}
			if(amount > 0) 
				Main.debug("Carregado " + amount + " tag(s)");
		} catch (Exception e) {
			Main.debug("Ocorreu um erro ao carregar as tags!");
		}
	}
	
	public void save() { 
		for(Tag ta : storageTags) { 
			ta.save();
		}
	}

	public Tag get(String name) {
		for(Tag ta : storageTags) { 
			if(ta.getName().equals(name)) { 
				return ta;
			}
		}
		return null;
	}
	
	public List<Tag> getTags() { 
		return storageTags;
	}
}
