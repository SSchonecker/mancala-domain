package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Pit> pitList = new ArrayList<Pit>();
	private Kalaha myKalaha;

	public void giveKalaha(Kalaha myKalaha) {
		this.myKalaha = myKalaha;
		this.myKalaha.myOwner = this;
	}
	
}