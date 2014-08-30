package io.github.com.revenantgaze.duel;

import java.io.File;

import io.github.com.revenantgaze.duel.DuelGame;
import io.github.com.revenantgaze.duel.DuelGameState;
import io.github.com.revenantgaze.duel.DuelGameState.duelGameState;
import io.github.com.revenantgaze.duel.events.PlayerDiedEvent;
import io.github.com.revenantgaze.duel.events.PlayerQuittedEvent;
import io.github.com.revenantgaze.duel.executors.AcceptDuelCommand;
import io.github.com.revenantgaze.duel.executors.DuelCommand;
import io.github.com.revenantgaze.duel.hashmaps.ChallengedPlayers;
import io.github.com.revenantgaze.duel.hashmaps.DuelPlayers;
import io.github.com.revenantgaze.duel.hashmaps.PlayerChallenged;
import io.github.com.revenantgaze.duel.hashmaps.PlayersDueling;
import io.github.com.revenantgaze.duel.runnables.ChallengeRespondTimer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Plugin instance;
	public DuelGame dg;
	public DuelGameState dgs;
	public AcceptDuelCommand adCMD;
	public ChallengedPlayers cp;
	public DuelPlayers dp;
	public PlayerChallenged  pc;
	public PlayersDueling pd;
	public ChallengeRespondTimer crt;
	
	PluginManager pm = this.getServer().getPluginManager();
	
	File defaultConfigFile = new File(this.getDataFolder(), "config.yml");

	FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigFile);
	
	@Override
	public void onEnable() {
		
		if (!defaultConfigFile.exists()) {

			getLogger().info("Generating default config file...");

			this.saveResource("config.yml", false);			
			this.saveDefaultConfig();
			this.getConfig().options().header("Default config file for Emotes " + this.getDescription().getVersion());

			saveConfig();

			getLogger().info("Config.yml has been generated!");

		}

		else {

			getLogger().info("Found config.yml");

		}
		
		pm.registerEvents(new PlayerDiedEvent(this), this);
		pm.registerEvents(new PlayerQuittedEvent(this), this);
		
		this.getCommand("acceptduel").setExecutor(new AcceptDuelCommand(this));
		this.getCommand("duel").setExecutor(new DuelCommand(this));
		
		dgs.setGameState(duelGameState.WAITING);
		
		getLogger().info("Duelicious v" + getDescription().getVersion() + " is now enabled!");
		
	}
	
	@Override
	public void onDisable() {
		
		getLogger().info("Duelicious is now disabled!");
		
	}
	
	public FileConfiguration getConfig() {

		if (defaultConfig == null) {

			this.reloadConfig();

		}

		return defaultConfig;

	}

}
