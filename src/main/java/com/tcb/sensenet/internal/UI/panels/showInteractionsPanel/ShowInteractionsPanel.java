package com.tcb.sensenet.internal.UI.panels.showInteractionsPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.listeners.ActionChangeDisplayedInteractionTypesListener;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class ShowInteractionsPanel extends DefaultPanel {
	
	private AppGlobals appGlobals;
	private Map<String,JCheckBox> checkboxes;
	private JPanel checkboxPanel;
	private JPanel defaultLabelPanel;



	public ShowInteractionsPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.checkboxes = new SafeMap<String,JCheckBox>();
						
		addDefaultLabelPanel();
		addCheckboxPanel();
		
		addDummyPanel();
		
		appGlobals.stateManagers.showInteractionsPanelStateManager.register(this);
		adjustSize();
	}
	
	private JLabel createDefaultLabel(){
		JLabel label = new JLabel("No sensenet network found");
		Font font = new Font(
				label.getFont().getName(),Font.ITALIC
				,label.getFont().getSize());
		label.setFont(font);
		return label;
	}
	
	private void addDefaultLabelPanel(){
		JPanel p = new JPanel();
		p.add(createDefaultLabel());
						
		this.add(p);
		this.defaultLabelPanel = p;
	}
	
	private void adjustSize(){
		int minWidth = this.getMinimumSize().width;
		Dimension d = new Dimension(
				minWidth,
				(int)getPreferredSize().getHeight());
		this.setMaximumSize(d);
	}
	
			
	private void addCheckboxPanel(){
		checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 1.0;
		
		this.add(checkboxPanel, c);
	}
	
	
	
	public synchronized void addCheckbox(String interactionType){
		if(checkboxes.containsKey(interactionType)){
			return;
		}
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText(interactionType.toString());
		checkBox.addActionListener(
				new ActionChangeDisplayedInteractionTypesListener(
						interactionType,
						appGlobals));
		checkboxes.put(interactionType, checkBox);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,2,0);
		
		defaultLabelPanel.setVisible(false);
		checkboxPanel.add(checkBox, c);
		adjustSize();
	}
	
	public synchronized void removeCheckbox(String interactionType){
		if(!checkboxes.containsKey(interactionType)){
			return;
		}
		JCheckBox checkBox = checkboxes.get(interactionType);
		checkboxes.remove(interactionType);
		checkboxPanel.remove(checkBox);
		
		if(checkboxes.isEmpty()){
			defaultLabelPanel.setVisible(true);
		}
	}
	
	public synchronized void removeCheckboxes(){
		List<String> keys = new ArrayList<String>(checkboxes.keySet());
		for(String k: keys){
			removeCheckbox(k);
		}
	}
	
	public synchronized void resetCheckboxSelections(){
		checkboxes.values().forEach(v -> v.setSelected(false));
	}
		
	public synchronized void setSelectedInteractionTypes(List<String> interactionTypes){
		resetCheckboxSelections();
		interactionTypes.forEach(t -> checkboxes.get(t).setSelected(true));
	}
	
	public synchronized void setDisplayedInteractionTypes(List<String> interactionTypes){
		removeCheckboxes();
		interactionTypes.forEach(t -> addCheckbox(t));
	}
	
	
	
		

}
