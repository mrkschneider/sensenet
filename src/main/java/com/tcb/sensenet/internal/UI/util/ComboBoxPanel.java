package com.tcb.sensenet.internal.UI.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public abstract class ComboBoxPanel<T extends Enum<T>,U extends JPanel> extends JPanel implements ItemListener {

	protected abstract JDialog getParentDialog();
	protected abstract U updateContentPanel(ItemEvent e);
	protected abstract T[] getSelectableItems();
		
	private U contentPanel;
	private JComboBox<T> selectionBox;
	
	public ComboBoxPanel(U contentPanel, int defaultSelectedIndex){
		this.contentPanel = contentPanel;
		this.setLayout(new GridBagLayout());
		addSelectionBox(defaultSelectedIndex);
		addContentPanel(contentPanel);
	
	}
	
	private void addSelectionBox(int defaultSelectedIndex){
		selectionBox = new JComboBox<T>(getSelectableItems());
		
		GridBagConstraints c = createSelectionBoxConstraints();
		selectionBox.setSelectedIndex(defaultSelectedIndex);
		selectionBox.addItemListener(this);
		
		this.add(selectionBox,c);
	}
	
	protected GridBagConstraints createSelectionBoxConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0,0,5,0);
		return c;
	}
	
	private void addContentPanel(U contentPanel){
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		
		
		this.add(contentPanel,c);
	}
		
	
	public U getContentPanel() {
		return contentPanel;
	}
	
	public JComboBox<T> getSelectionBox() {
		return selectionBox;
	}
	
	protected void setSelectedIndex(int i) {
		selectionBox.setSelectedIndex(i);
	}
	
	@SuppressWarnings("unchecked")
	public T getSelected(){
		return (T) selectionBox.getSelectedItem();
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()!=ItemEvent.SELECTED) return;
		this.remove(contentPanel);
		
		contentPanel = updateContentPanel(e);
		
		contentPanel.setVisible(true);
		this.add(contentPanel);
		getParentDialog().pack();
		}
	
	
	
	
}
