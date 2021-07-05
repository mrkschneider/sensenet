package com.tcb.sensenet.internal.UI.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.cytoscape.cyLib.UI.panel.WrapPanel;

public class TextFieldPanel extends JPanel {
	private WrapPanel<JTextField> field;
	private WrapPanel<JLabel> label;
	private String defaultValue;
	private String labelText;

	public TextFieldPanel(String labelText, String defaultValue){
		this.labelText = labelText;
		this.defaultValue = defaultValue;

		this.setLayout(new GridBagLayout());
						
		addLine();		
				
	}
	
	public JTextField getField(){
		return field.getWrappedElement();
	}
	
	public String getFieldValue(){
		return field.getWrappedElement().getText();
	}
	
	public void setFieldValue(String value){
		field.getWrappedElement().setText(value);
	}
	
	public void addActionListener(ActionListener listener){
		field.getWrappedElement().addActionListener(listener);
	}
	
	private void addLine(){
		this.field = new WrapPanel<JTextField>(new JTextField(defaultValue));
		this.label = new WrapPanel<JLabel>(new JLabel(labelText));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		c.ipady = 5;
		c.ipadx = 5;
		
		this.add(label, c);
		this.add(field, c);
	}
	
	
	
	
}
