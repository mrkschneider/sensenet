package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.util.JPanelUtil;

public class DefaultPanel extends JPanel {
	
	private GridBagConstraints defaultConstraints;

	public DefaultPanel(){
		this.defaultConstraints = createDefaultConstraints();
		init();
	}
	
	public DefaultPanel(GridBagConstraints defaultConstraints){
		this.defaultConstraints = defaultConstraints;
		init();
	}
	
	private void init(){
		this.setLayout(new GridBagLayout());
		
	}
	
	public void setBorders(String title){
		JPanelUtil.setBorders(this, title);
	}
	
	@Override
	public Component add(Component c){
		super.add(c, getDefaultConstraints());
		return c;
	}
	
	@Override
	public Dimension getMinimumSize(){
		return getPreferredSize();
	}
	
	protected GridBagConstraints getDefaultConstraints(){
		return new GridBagConstraints(
				defaultConstraints.gridx,
				defaultConstraints.gridy,
				defaultConstraints.gridwidth,
				defaultConstraints.gridheight,
				defaultConstraints.weightx,
				defaultConstraints.weighty,
				defaultConstraints.anchor,
				defaultConstraints.fill,
				defaultConstraints.insets,
				defaultConstraints.ipadx,
				defaultConstraints.ipady);
	}
	
	private GridBagConstraints createDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		return c;
	}
	
	protected void addDummyPanel(){
		JPanel dummy = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = GridBagConstraints.REMAINDER;
		c.gridy = 0;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		this.add(dummy,c);
	}
	
}
