package com.tcb.sensenet.internal.UI.panels.stylePanel.auto.edges;

public enum EdgeAutoStyle {
	COLOR, WIDTH;
	
	public String toString(){
		switch(this){
		case COLOR: return "Color";
		case WIDTH: return "Width";
		default: throw new IllegalArgumentException();
		}
	}
}

