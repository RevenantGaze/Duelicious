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

public class DuelCommand implements CommandExecutor {
	
	public Main plugin;
	
	public DuelCommand(Main instance) {
		
		plugin = instance;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("duel")) { 
		//Check if the player executes /duel
			
			if (sender instanceof Player) {
			
				Player playerChallenger = (Player) sender; 
				//Get the command-executing player
				Player playerChallenged = null; 
				//Prepare to get the challenged player
				
				if (args.length == 1) { 
				//Command equals /duel <string>
					
					String playerChallengedNameNoCase = args[0]; 
					//Get the name of the challenged player (ignore case)
					
					for (Player p : Bukkit.getServer().getOnlinePlayers()) { 
					//Loop through the online players
						
						if(p.getName().equalsIgnoreCase(playerChallengedNameNoCase)) { 
						//Check if there is an online player with the given name
							
							playerChallenged = p; 
							//Found the challenged player
							
						}
						
					}
					
					if (playerChallenged != null) {
					//Challenged player is online
						
						duelGameState dgs = plugin.dgs.gameState;
						
						if (dgs == duelGameState.WAITING) {
							
							int respondTime = plugin.getConfig().getInt("timer.respondtime"); 
						
							String playerChallengerName = playerChallenger.getName(); 
							//The challenging player's name to string
							
							UUID playerChallengerUID = playerChallenger.getUniqueId();
							//UUID of the challenging player
							UUID playerChallengedUID = playerChallenged.getUniqueId();
							//UUID of the challenged player
							
							playerChallenged.sendMessage(ChatColor.AQUA + "You have been challenged to a duel by " + ChatColor.RED + playerChallengerName);
							//Let the player know he/she has been challenged to a duel
							playerChallenged.sendMessage(ChatColor.AQUA + "To accept: " + ChatColor.GREEN + "/acceptduel");
							//Player has to do /acceptduel to accept the duel
							playerChallenged.sendMessage(ChatColor.AQUA + "To decline " + ChatColor.GREEN + "/declineduel");
							//Player has to do /declineduel to decline the duel
							playerChallenged.sendMessage(ChatColor.AQUA + "This challenge will timeout after " + ChatColor.GRAY + respondTime + ChatColor.AQUA + " seconds!");
							//How long time the player can respond
							
							plugin.cp.challengedPlayers.put(playerChallengedUID, playerChallengerUID);
							plugin.pc.challengedPlayer.put(1, playerChallenged);
							plugin.crt.respondTimer.run();
							
							return true;
							
						}
						
						else if (dgs == duelGameState.STARTING || dgs == duelGameState.INGAME || dgs == duelGameState.ENDED){
							
							playerChallenger.sendMessage(ChatColor.DARK_RED + "There is a duel in progress, please try again later!");
							
							return true;
							
						}
						
						else {
							
							playerChallenger.sendMessage(ChatColor.DARK_RED + "Duels are currently disabled!");
							
							return true;
							
						}
						
					}
					
					else {
					//Challenged player is not online
						
						playerChallenger.sendMessage(ChatColor.DARK_RED + "The player you are trying to duel is not online!");
						//Send error message
						
						return true;
						
					}
					
				}
				
				else {
				//Incorrect command usage
					
					playerChallenger.sendMessage(ChatColor.DARK_RED + "Incorrect usage! /duel <player>");
					//Send error message
					
					return true;
					
				}
				
			}
			
			else {
			//Command is run from the console
				
				sender.sendMessage("This command can not be run from the console!");
				//Send error message
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
