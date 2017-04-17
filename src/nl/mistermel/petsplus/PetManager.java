package nl.mistermel.petsplus;

import java.util.ArrayList;

public class PetManager {
	
	private ArrayList<PetBase> petBases = new ArrayList<PetBase>();
	
	public void registerPet(PetBase pet) {
		petBases.add(pet);
	}
	
	public void unregisterPet(PetBase pet) {
		petBases.remove(pet);
	}
	
	public ArrayList<PetBase> getPets() {
		return petBases;
	}
}
