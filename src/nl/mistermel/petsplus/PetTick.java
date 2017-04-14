package nl.mistermel.petsplus;

import org.bukkit.scheduler.BukkitRunnable;

public class PetTick extends BukkitRunnable {
	
	private Main main;
	
	public PetTick(Main main) {
		this.main = main;
	}
	
	public void run() {
		for(Pet p : this.main.getPets()) {
			p.tick();
		}
	}
	
}
