package me.nbcss.quickGui.elements;

import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class TradePage {
	private ItemStack leftInputItem;
	private ItemStack rightInputItem;
	private ItemStack outputItem;
	private boolean disabled;
	public TradePage(){
		leftInputItem = null;
		rightInputItem = null;
		outputItem = null;
		disabled = false;
	}
	public TradePage(ItemStack input, ItemStack output){
		leftInputItem = input;
		rightInputItem = null;
		outputItem = output;
		disabled = false;
	}
	public TradePage(ItemStack leftInput, ItemStack rightInput, ItemStack output){
		leftInputItem = leftInput;
		rightInputItem = rightInput;
		outputItem = output;
		disabled = false;
	}
	public ItemStack getLeftInputItem() {
		return leftInputItem;
	}
	public void setLeftInputItem(ItemStack leftInputItem) {
		this.leftInputItem = leftInputItem;
	}
	public ItemStack getRightInputItem() {
		return rightInputItem;
	}
	public void setRightInputItem(ItemStack rightInputItem) {
		this.rightInputItem = rightInputItem;
	}
	public ItemStack getOutputItem() {
		return outputItem;
	}
	public void setOutputItem(ItemStack outputItem) {
		this.outputItem = outputItem;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	@SuppressWarnings("deprecation")
	public byte[] toByteArray(){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		int id = -1, id2 = -1;
		if(leftInputItem != null)
			id = leftInputItem.getTypeId();
		out.writeShort(id);
		if(id != -1){
			out.writeByte(leftInputItem.getAmount());
			out.writeShort(leftInputItem.getDurability());
			out.writeByte(0);
		}
		if(outputItem != null)
			id2 = outputItem.getTypeId();
		out.writeShort(id2);
		if(id2 != -1){
			out.writeByte(outputItem.getAmount());
			out.writeShort(outputItem.getDurability());
			out.writeByte(0);
		}
		if(rightInputItem != null){
			out.writeBoolean(true);
			int id3 = rightInputItem.getTypeId();
			out.writeShort(id3);
			if(id3 != -1){
				out.writeByte(rightInputItem.getAmount());
				out.writeShort(rightInputItem.getDurability());
				out.writeByte(0);
			}
		}else
			out.writeBoolean(false);
		out.writeBoolean(disabled);
		out.writeInt(0);
		out.writeInt(9999);
		return out.toByteArray();
	}
}
