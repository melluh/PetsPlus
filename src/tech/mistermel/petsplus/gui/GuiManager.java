package tech.mistermel.petsplus.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GuiManager {
	
	private Map<Class<? extends Gui>, Gui> guis = new HashMap<Class<? extends Gui>, Gui>();
	
	public void registerGui(Class<? extends Gui> clazz, Gui gui) {
		guis.put(clazz, gui);
	}
	
	public Gui getGui(Class<? extends Gui> clazz) {
		return guis.get(clazz);
	}
	
	public Collection<Gui> getGuis() {
		return guis.values();
	}
	
}
