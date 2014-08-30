package io.github.com.revenantgaze.duel.events;

import io.github.com.revenantgaze.duel.Main;
import io.github.com.revenantgaze.duel.DuelGameState.duelGameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDiedEvent implements Listener {
	
	public Main plugin;

	public PlayerDiedEvent(Main instance) {

		plugin = instance;

	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void playerDeathEvent(PlayerDeathEvent e) {
		
		Player eventPlayer = (Player) e.getEntity();
		
		if (plugin.pd.playersDueling.containsKey(eventPlayer)) {
			
			plugin.dgs.setGameState(duelGameState.ENDED);
			
			Player playerWon = plugin.pd.playersDueling.get(eventPlayer);
			Player playerLost = eventPlayer;
			
			String playerWonName = playerWon.getName();
			String playerLostName = playerLost.getName();
			
			Bukkit.getServer().broadcastMessage(ChatColor.RED + playerWonName + ChatColor.GOLD + " has bested " + ChatColor.RED + playerLostName + " in a duel!");
			
			playerWon.sendMessage(ChatColor.GOLD + "Congratulations! You won against " + ChatColor.RED + playerLostName + ChatColor.GOLD + "!");
			playerLost.sendMessage(ChatColor.GOLD + "Better luck next time! You lost against " + ChatColor.RED + playerWonName + ChatColor.GOLD + "!");
		
			plugin.pd.playersDueling.remove(playerWon);
			plugin.pd.playersDueling.remove(playerLost);
			plugin.dgs.setGameState(duelGameState.WAITING);
			
		}
		
	}
	
}
