package com.tcb.sensenet.internal.UI.util;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextComboBox<T> extends JPanel {
	private JTextField field;
	private JComboBox<T> comboBox;
	
	public TextComboBox(T[] options){
		field = new JTextField();
		comboBox = new JComboBox<T>(options);
		
		this.add(field);
		this.add(comboBox);
		
		this.setLayout(new GridLayout(0,2));
		//this.setLayout(new GridBagLayout());
	}
	
	public JTextField getField(){
		return field;
	}
	
	public JComboBox<T> getComboBox(){
		return comboBox;
	}
}
