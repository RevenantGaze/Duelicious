package io.github.com.revenantgaze.duel.executors;

import io.github.com.revenantgaze.duel.Main;
import io.github.com.revenantgaze.duel.DuelGameState.duelGameState;

import java.util.UUID;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptDuelCommand implements CommandExecutor {
	
	public Main plugin;
	
	public AcceptDuelCommand(Main instance) {
		
		plugin = instance;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("acceptduel")) {
			
			if (sender instanceof Player) {
				
				Player playerChallenged = (Player) sender;
				
				UUID playerChallengedUID = playerChallenged.getUniqueId();
				
				duelGameState dgs = plugin.dgs.gameState;
				
				if (dgs == duelGameState.WAITING) {
				
					if (plugin.cp.challengedPlayers.containsKey(playerChallengedUID)) {
						
						UUID playerChallengerUID = plugin.cp.challengedPlayers.get(playerChallengedUID);
						
						Player playerChallenger = Bukkit.getPlayer(playerChallengerUID);
						
						String playerChallengedName = playerChallenged.getName();
						
						playerChallenger.sendMessage(ChatColor.RED + playerChallengedName + ChatColor.AQUA + " has accepted your challenge! Prepare to fight for your life!");
						
						plugin.cp.challengedPlayers.remove(playerChallengedUID);		
						plugin.dp.duelPlayers.put(1, playerChallengedUID);
						plugin.dp.duelPlayers.put(2, playerChallengerUID);					
						plugin.dg.duelGameTimer.run();
						
						return true;
						
					}
					
					else {
						
						playerChallenged.sendMessage(ChatColor.DARK_RED + "You have not been challenged to a duel!");
						playerChallenged.sendMessage(ChatColor.DARK_RED + "Challenge a player now with " + ChatColor.GREEN + "/duel <player>" + ChatColor.RED + "!");
						
						return true;
						
					}
				
				}
				
				else if (dgs == duelGameState.STARTING || dgs == duelGameState.INGAME || dgs == duelGameState.ENDED){
					
					playerChallenged.sendMessage(ChatColor.DARK_RED + "There is a duel in progress, please try again later!");
					
					return true;
					
				}
				
				else {
					
					playerChallenged.sendMessage(ChatColor.DARK_RED + "Duels are currently disabled!");
					
					return true;
					
				}
				
			}
			
			else {
				
				sender.sendMessage("This command can not be run from the console!");
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
