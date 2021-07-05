package com.tcb.sensenet.internal.UI.panels.weightPanel;

import javax.swing.JSlider;

public class SelectFrameSlider extends JSlider {

	private volatile boolean silenced = false;
	
	public SelectFrameSlider(int min, int max, int value){
		super(min,max,value);
	}
	
	public Boolean isSilent(){
		return silenced;
	}
	
	public void silence(){
		silenced = true;
	}
	
	public void unsilence(){
		silenced = false;
	}
	

}
