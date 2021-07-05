package com.tcb.sensenet.internal.util;

import javax.swing.JRadioButton;


public class KeyRadioButton extends JRadioButton {

	private String key;

	public KeyRadioButton(String name, String key){
		super(name);
		this.key = key;
	}
		
	public String getKey() {
		return key;
	}

	

}
