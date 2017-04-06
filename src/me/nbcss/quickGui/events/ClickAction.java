package me.nbcss.quickGui.events;

import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick.InventoryClickType;

public enum ClickAction {
	LEFT_CLICK, RIGHT_CLICK, MID_CLICK, SHIFT_LEFT_CLICK, SHIFT_RIGHT_CLICK, DOUBLE_CLICK, UNKNOWN, 
	NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5, NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9;
	public static ClickAction fromInventoryAction(int button, InventoryClickType mode){
		switch(mode){
		case PICKUP:
			if(button == 0)
				return LEFT_CLICK;
			else
				return RIGHT_CLICK;
		case QUICK_MOVE:
			if(button == 0)
				return SHIFT_LEFT_CLICK;
			else
				return SHIFT_RIGHT_CLICK;
		case SWAP:
			switch(button){
			case 0:
				return NUMBER_1;
			case 1:
				return NUMBER_2;
			case 2:
				return NUMBER_3;
			case 3:
				return NUMBER_4;
			case 4:
				return NUMBER_5;
			case 5:
				return NUMBER_6;
			case 6:
				return NUMBER_7;
			case 7:
				return NUMBER_8;
			case 8:
				return NUMBER_9;
			default:
				return UNKNOWN;
			}
		case CLONE:
			return MID_CLICK;
		case PICKUP_ALL:
			return DOUBLE_CLICK;
		default:
			return UNKNOWN;
		}
	}
}
