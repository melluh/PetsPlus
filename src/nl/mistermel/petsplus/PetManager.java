package nl.mistermel.petsplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PetManager {
	
	private List<PetBase> petBases = new ArrayList<>();
	
	private Map<UUID, Pet> pets = new HashMap<>();
	private Map<Entity, Pet> petEntities = new HashMap<>();
	
	public void tick() {
		for(Pet pet : pets.values()) {
			pet.tick();
		}
	}
	
	public void registerPetBase(PetBase pet) {
		petBases.add(pet);
	}
	
	public void registerPet(Pet pet) {
		pets.put(pet.getOwner().getUniqueId(), pet);
		petEntities.put(pet.getEntity(), pet);
	}
	
	public void removePet(Player player) {
		this.removePet(this.getPet(player.getUniqueId()));
	}
	
	public void removePet(Pet pet) {
		pets.remove(pet.getOwner().getUniqueId());
		petEntities.remove(pet.getEntity());
	}
	
	public void removeAll() {
		for(Pet pet : pets.values()) {
			pet.remove();
		}
		pets.clear();
		petEntities.clear();
	}
	
	public Pet getPet(UUID owner) {
		return pets.get(owner);
	}
	
	public Pet getPet(Entity entity) {
		return petEntities.get(entity);
	}
	
	public List<PetBase> getPets() {
		return petBases;
	}
}
