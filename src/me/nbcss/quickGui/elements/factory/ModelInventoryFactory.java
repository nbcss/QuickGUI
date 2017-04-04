<<<<<<< HEAD
package me.nbcss.quickGui.elements.factory;

import java.util.HashMap;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;

public abstract class ModelInventoryFactory implements InventoryFactory {
	private static HashMap<ModelInventoryFactory, AbstractInventory> MAP = new HashMap<ModelInventoryFactory, AbstractInventory>();
	protected ModelInventoryFactory(AbstractInventory model){
		MAP.put(this, model);
	}
	@Override
	public final AbstractInventory createInventory() {
		return MAP.get(this).clone();
	}
	
}
=======
package me.nbcss.quickGui.elements.factory;

import java.util.HashMap;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;

public abstract class ModelInventoryFactory implements InventoryFactory {
	private static HashMap<ModelInventoryFactory, AbstractInventory> MAP = new HashMap<ModelInventoryFactory, AbstractInventory>();
	protected ModelInventoryFactory(AbstractInventory model){
		MAP.put(this, model);
	}
	@Override
	public final AbstractInventory createInventory() {
		return MAP.get(this).clone();
	}
	
}
>>>>>>> origin/master
