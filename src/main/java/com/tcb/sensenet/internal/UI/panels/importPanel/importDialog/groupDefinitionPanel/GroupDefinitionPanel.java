package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class GroupDefinitionPanel extends DefaultPanel {

	private ComboBoxCardPanel<GroupInputMode, AbstractGroupDefinitionInputPanel> groupDefinitionPanel;

	private AppGlobals appGlobals;
		
	private static final String panelName = "Metanode definition";
	private static final AppProperty defaultGroupDefinitionProperty = AppProperty.DEFAULT_GROUP_DEFINITION;
	
	public GroupDefinitionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGroupDefinitionPanel();
		
		JPanelUtil.setBorders(this, panelName);
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void addGroupDefinitionPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		GroupInputMode defaultGroupInputMode = appGlobals.appProperties.getEnumOrDefault(
				GroupInputMode.class, defaultGroupDefinitionProperty);
		JComboBox<GroupInputMode> groupDefinitionBox = p.addChoosableParameter(
				"Group definition", GroupInputMode.values(), defaultGroupInputMode);
		AbstractGroupDefinitionInputPanel aminoAcidGroupPanel = new AminoAcidGroupDefinitionInputPanel();
		AbstractGroupDefinitionInputPanel backboneSidechainGroupPanel = 
				new BackboneSidechainGroupDefinitionInputPanel(appGlobals);
		ComboBoxCardPanel<GroupInputMode,AbstractGroupDefinitionInputPanel> groupCards = 
				new ComboBoxCardPanel<>(groupDefinitionBox);
		groupCards.addCard(GroupInputMode.AMINO_ACID, aminoAcidGroupPanel);
		groupCards.addCard(GroupInputMode.BACKBONE_SIDECHAIN, backboneSidechainGroupPanel);
		groupDefinitionPanel = groupCards;
		
		p.add(groupDefinitionPanel);
						
		this.add(p);
	}
	
	
		
	public NodeGroupDefinition getGroupDefinition(){
		GroupInputMode selectedInputMode = groupDefinitionPanel.getActiveSelection();
		appGlobals.appProperties.set(defaultGroupDefinitionProperty, selectedInputMode.name());
		return groupDefinitionPanel.getActiveCard().getGroupDefinition();
	}
	
}
