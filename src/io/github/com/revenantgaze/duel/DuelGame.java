package io.github.com.revenantgaze.duel;

import io.github.com.revenantgaze.duel.Main;
import io.github.com.revenantgaze.duel.DuelGameState.duelGameState;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelGame {

	public Main plugin;
	
	UUID playerDuelistOneUID = plugin.dp.duelPlayers.get(1); 
	//Get UUID of duelist 1 from duelPlayers
	UUID playerDuelistTwoUID = plugin.dp.duelPlayers.get(2); 
	//Get UUID of duelist 2 from duelPlayers
	
	Player playerDuelistOne = Bukkit.getServer().getPlayer(playerDuelistOneUID);
	Player playerDuelistTwo = Bukkit.getServer().getPlayer(playerDuelistTwoUID);
	
	public BukkitRunnable duelGameTimer = (BukkitRunnable) new BukkitRunnable() {

		public void run() {

		int timerCountdown = plugin.getConfig().getInt("timer.duelstart"); 
		//Set timer length
		
		plugin.dgs.setGameState(duelGameState.STARTING); 
		//Change game state to STARTING
				
			if (timerCountdown > 0) { 
			//Countdown is not finished
				
				playerDuelistOne.sendMessage(ChatColor.AQUA + "Duel is starting in " + ChatColor.GREEN + timerCountdown + ChatColor.AQUA + " seconds!");	
				playerDuelistTwo.sendMessage(ChatColor.AQUA + "Duel is starting in " + ChatColor.GREEN + timerCountdown + ChatColor.AQUA + " seconds!");
				
				timerCountdown--; 
				//Subtract 1 from timerCountdown
					
			}
			
			else { 
			//Countdown is finished
				
				playerDuelistOne.sendMessage(ChatColor.RED + "FIGHT!");	
				playerDuelistTwo.sendMessage(ChatColor.RED + "FIGHT!");	
				
				plugin.dgs.setGameState(duelGameState.INGAME); 
				//Change game state to INGAME
				plugin.dp.duelPlayers.remove(1);
				plugin.dp.duelPlayers.remove(2);
				plugin.pd.playersDueling.put(playerDuelistOne, playerDuelistTwo);
				plugin.pd.playersDueling.put(playerDuelistTwo, playerDuelistOne);
				
				plugin.dgs.setGameState(duelGameState.INGAME); 
				//Change game state to INGAME
				
			}
				
		}
			
	}.runTaskTimer(plugin, 20, 20); 
	//Run once each second

}
