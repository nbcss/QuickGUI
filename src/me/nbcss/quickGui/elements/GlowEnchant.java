package me.nbcss.quickGui.elements;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class GlowEnchant extends EnchantmentWrapper {
	@Override
	public boolean canEnchantItem(ItemStack item) {
		return false;
	}
	@Override
	public boolean conflictsWith(Enchantment other) {
		return false;
	}
	@Override
	public Enchantment getEnchantment() {
		return this;
	}
	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}
	@Override
	public int getMaxLevel() {
		return 0;
	}
	@Override
	public String getName() {
		return null;
	}
	@Override
	public int getStartLevel() {
		return 0;
	}
	private static final int ID = 99;
	public GlowEnchant() {
		super(ID);
		
	}

}
