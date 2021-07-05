package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.nodes;

public enum NodeAutoStyle {
	COLOR, SIZE;
	
	public String toString(){
		switch(this){
		case COLOR: return "Color";
		case SIZE: return "Size";
		default: throw new IllegalArgumentException();
		}
	}
}
