package io.github.com.revenantgaze.duel.runnables;

import io.github.com.revenantgaze.duel.Main;
import io.github.com.revenantgaze.duel.DuelGameState.duelGameState;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChallengeRespondTimer {

	public Main plugin;
	
	public BukkitRunnable respondTimer = (BukkitRunnable) new BukkitRunnable() {
		
		public void run() {
			
			int timerCountdown = plugin.getConfig().getInt("timer.respondtime");
			
			duelGameState dgs = plugin.dgs.gameState;
			
			if (timerCountdown > 0) { 
			//Countdown is not finished
				
				if (!(dgs == duelGameState.STARTING || dgs == duelGameState.INGAME)) {
	
					timerCountdown--; 
					//Subtract 1 from timerCountdown
						
				}
					
				else {
					
					plugin.pc.challengedPlayer.remove(1);
						
					respondTimer.cancel();
						
				}
						
			}
				
			else { 
			//Countdown is finished
					
				if (!(dgs == duelGameState.STARTING || dgs == duelGameState.INGAME)) {
						
					Player playerChallenged = plugin.pc.challengedPlayer.get(1);
						
					playerChallenged.sendMessage(ChatColor.DARK_RED + "The duel request has timed out!");
					
					plugin.pc.challengedPlayer.remove(1);
						
				}
					
			}
			
		}
		
	}.runTaskTimer(plugin, 20, 20); 
	
}
