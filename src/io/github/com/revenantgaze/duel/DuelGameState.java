package io.github.com.revenantgaze.duel;

public class DuelGameState {
	
	public enum duelGameState {
		
		DISABLED, WAITING, STARTING, INGAME, ENDED;
		
	}
	
	public duelGameState gameState;
	
	public duelGameState getGameState() {
		
		return gameState;
		
	}
	
	public void setGameState(duelGameState gs) {
		
		gameState = gs;
		
	}

}
