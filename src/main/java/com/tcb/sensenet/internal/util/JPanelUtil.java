package com.tcb.sensenet.internal.util;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class JPanelUtil {		
	public static void setBorders(JComponent parent, String header){
		setBorders(parent,header,1,1,1,1);
	}
	
	public static void setBorders(JComponent parent, String header, int top, int left, int bottom, int right){
		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), header);
		Border emptyBorder = BorderFactory.createEmptyBorder(top, left, bottom, right);
		parent.setBorder(new CompoundBorder(titledBorder,emptyBorder));
	}

	public static JButton addButton(Container row, String buttonName, ActionListener listener){
		JButton button = new JButton(buttonName);
		addButton(row,button,listener);
		return button;
	}
	
	public static KeyRadioButton addRadioButton(JPanel row, String buttonName, String buttonKey, ActionListener listener){
		KeyRadioButton button = new KeyRadioButton(buttonName, buttonKey);
		addButton(row,button,listener);
		return button;
	}
	
	private static void addButton(Container row, AbstractButton button, ActionListener listener){
		button.addActionListener(listener);
		row.add(button);
	}
	
	public static JPanel addRow(Container parent){
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		parent.add(row);
		return row;
	}
	
	public static void addGridBagFillPanel(Container parent){
		JPanel fillPanel = new JPanel();
		GridBagConstraints fillConstraints = new GridBagConstraints();
		fillConstraints.weightx = 1.0;
		fillConstraints.weighty = 1.0;
				
		parent.add(fillPanel, fillConstraints);
	}
}
