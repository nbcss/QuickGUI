package me.nbcss.quickGui.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Material;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Util {
	public static ItemStack createItem(int amount, short durability, Material material, String name, String... lores) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		item.setDurability(durability);
		ItemMeta meta = item.getItemMeta();
		if(name != null)
			meta.setDisplayName(name);
		if(lores != null)
			if(lores.length > 0){
				ArrayList<String> Lore = new ArrayList<String>();
				for(String lore : lores)
					Lore.add(lore);
				meta.setLore(Lore);
			}
		item.setItemMeta(meta);
		return item;
	}
	
	public static byte[] getItemStackByteArray(ItemStack item) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		Class<?> craft = ReflectManager.get().getCraftBukkitClass("inventory", "CraftItemStack");
		Object nmsItem = craft.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
		if((boolean) nmsItem.getClass().getMethod("hasTag").invoke(nmsItem))
			writeNBTCompound("", nmsItem.getClass().getMethod("getTag").invoke(nmsItem), out);
		else
			out.writeByte(0);
		return out.toByteArray();
	}
	public static void writeNBTByte(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(1);
			out.writeUTF(name);
		}
		out.writeByte((int) obj.getClass().getMethod("g").invoke(obj));
	}
	public static void writeNBTShort(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(2);
			out.writeUTF(name);
		}
		out.writeShort((int) obj.getClass().getMethod("f").invoke(obj));
	}
	public static void writeNBTInt(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(3);
			out.writeUTF(name);
		}
		out.writeInt((int) obj.getClass().getMethod("e").invoke(obj));
	}
	public static void writeNBTLong(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(4);
			out.writeUTF(name);
		}
		out.writeLong((long) obj.getClass().getMethod("d").invoke(obj));
	}
	public static void writeNBTFloat(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(5);
			out.writeUTF(name);
		}
		out.writeFloat((float) obj.getClass().getMethod("i").invoke(obj));
	}
	public static void writeNBTDouble(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(6);
			out.writeUTF(name);
		}
		out.writeDouble((double) obj.getClass().getMethod("asDouble").invoke(obj));
	}
	public static void writeNBTByteArray(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(7);
			out.writeUTF(name);
		}
		byte[] array = ((byte[]) obj.getClass().getMethod("c").invoke(obj));
		out.writeInt(array.length);
		out.write(array);
	}
	public static void writeNBTString(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(8);
			out.writeUTF(name);
		}
		out.writeUTF((String) obj.getClass().getMethod("c_").invoke(obj));
	}
	public static void writeNBTList(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(9);
			out.writeUTF(name);
		}
		int id = (int) obj.getClass().getMethod("g").invoke(obj);
		out.writeByte(id);
		int size = (int) obj.getClass().getMethod("size").invoke(obj);
		out.writeInt(size);
		for(int i = 0; i < size; i++){
			Object element = obj.getClass().getMethod("h", int.class).invoke(obj, i);
			switch(id){
			case 1:
				writeNBTByte(null, element, out);
				break;
			case 2:
				writeNBTShort(null, element, out);
				break;
			case 3:
				writeNBTInt(null, element, out);
				break;
			case 4:
				writeNBTLong(null, element, out);
				break;
			case 5:
				writeNBTFloat(null, element, out);
				break;
			case 6:
				writeNBTDouble(null, element, out);
				break;
			case 7:
				writeNBTByteArray(null, element, out);
				break;
			case 8:
				writeNBTString(null, element, out);
				break;
			case 9:
				writeNBTList(null, element, out);
				break;
			case 10:
				writeNBTCompound(null, element, out);
				break;
			case 11:
				writeNBTIntArray(null, element, out);
				break;
			default:
				throw new RuntimeException("Unknown nbt id!");
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void writeNBTCompound(String name, Object obj, ByteArrayDataOutput out) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(name != null){
			out.writeByte(10);
			out.writeUTF(name);
		}
		Set<String> set = (Set<String>) obj.getClass().getMethod("c").invoke(obj);
		for(String sub : set){
			Object element = obj.getClass().getMethod("get", String.class).invoke(obj, sub);
			byte id = (byte) element.getClass().getMethod("getTypeId").invoke(element);
			switch(id){
			case 1:
				writeNBTByte(sub, element, out);
				break;
			case 2:
				writeNBTShort(sub, element, out);
				break;
			case 3:
				writeNBTInt(sub, element, out);
				break;
			case 4:
				writeNBTLong(sub, element, out);
				break;
			case 5:
				writeNBTFloat(sub, element, out);
				break;
			case 6:
				writeNBTDouble(sub, element, out);
				break;
			case 7:
				writeNBTByteArray(sub, element, out);
				break;
			case 8:
				writeNBTString(sub, element, out);
				break;
			case 9:
				writeNBTList(sub, element, out);
				break;
			case 10:
				writeNBTCompound(sub, element, out);
				break;
			case 11:
				writeNBTIntArray(sub, element, out);
				break;
			default:
				throw new RuntimeException("Unknown nbt id!");
			}
		}
		out.writeByte(0);
	}
	public static void writeNBTIntArray(String name, Object obj, ByteArrayDataOutput out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(name != null){
			out.writeByte(11);
			out.writeUTF(name);
		}
		int[] array = ((int[]) obj.getClass().getMethod("d").invoke(obj));
		out.writeInt(array.length);
		for(int value : array)
			out.writeInt(value);
	}
}
