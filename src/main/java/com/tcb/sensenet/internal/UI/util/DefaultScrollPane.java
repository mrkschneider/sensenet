package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class DefaultScrollPane extends JScrollPane {

	public DefaultScrollPane(Component view, int vsbPolicy, int hspPolicy){
		super(view,vsbPolicy,hspPolicy);
	}
	
	public DefaultScrollPane(int vsbPolicy, int hspPolicy){
		super(vsbPolicy,hspPolicy);
	}
	
	@Override
	public Dimension getMinimumSize(){
		return getPreferredSize();
	}
		
	@Override
	public void setEnabled(boolean value){
		// Do nothing
	}
	
}
