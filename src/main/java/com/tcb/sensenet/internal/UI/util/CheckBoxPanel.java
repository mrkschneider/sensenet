package com.tcb.sensenet.internal.UI.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.util.JPanelUtil;

public abstract class CheckBoxPanel<U extends JPanel> extends JPanel implements ItemListener {

	protected abstract JDialog getParentDialog();
	protected abstract Optional<U> updateContentPanel(ItemEvent e);
	
	private Optional<U> contentPanel;
	private JCheckBox selectionBox;
	
	public CheckBoxPanel(String panelName, String checkBoxTitle){
		// Layout
		this.setLayout(new GridBagLayout());
				
		// Create
		this.selectionBox = addSelectionBox(checkBoxTitle);
		this.contentPanel = Optional.empty();
		
				
		// Style
		JPanelUtil.setBorders(this, panelName);
}
	
	private GridBagConstraints getContentPanelConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		return c;
	}
	
	private JCheckBox addSelectionBox(String title){
		JCheckBox selectionBox = new JCheckBox(title);
		
		
		selectionBox.addItemListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		
		this.add(selectionBox, c);
		
		JPanel fillPanel = new JPanel();
		GridBagConstraints fillC = new GridBagConstraints();
		fillC.gridx = 1;
		fillC.gridy = 0;
		fillC.weightx = 1.0;
		this.add(fillPanel, fillC);
				
		return selectionBox;
	}
		
	
	public U getContentPanel() {
		return contentPanel.get();
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(contentPanel.isPresent()) {
			this.remove(getContentPanel());
			contentPanel = Optional.empty();
		}
				
		Optional<U> newContentPanel = updateContentPanel(e);
		if(newContentPanel.isPresent()){
			contentPanel = Optional.of(newContentPanel.get());
			this.add(getContentPanel(), getContentPanelConstraints());
		}
		getParentDialog().pack();
	}
	
	public boolean isChecked(){
		return selectionBox.isSelected();
	}
		
	
}
