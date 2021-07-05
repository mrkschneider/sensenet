package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.groups.nodes.BackboneGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;

public class BackboneSidechainGroupDefinitionInputPanel extends AbstractGroupDefinitionInputPanel {

	private static final AppProperty defaultBackbonePropertyName = AppProperty.DEFAULT_BACKBONE_NAMES;
	
	private AppGlobals appGlobals;
	private JTextField backboneNamesField;

	public BackboneSidechainGroupDefinitionInputPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		this.addBackboneNamesPanel();
	};
	
	private void addBackboneNamesPanel(){
		String defaultBackboneNames = appGlobals.appProperties.getOrDefault(defaultBackbonePropertyName);
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		backboneNamesField = p.addTextParameter("Backbone atom names", defaultBackboneNames);
				
		this.add(p);
	}
	
	private String[] splitTextField(){
		return Optional.ofNullable(backboneNamesField.getText())
				.orElseThrow( () -> new IllegalArgumentException("Backbone atom names may not be empty"))
				.split(",");
	}
	
	private Set<String> getBackboneAtomNames(){
		Set<String> atomNames = new HashSet<String>(Arrays.asList(splitTextField()));
		if(atomNames.isEmpty()) {
			throw new IllegalArgumentException("Backbone atom names may not be empty");
		} else {
			appGlobals.appProperties.set(defaultBackbonePropertyName, backboneNamesField.getText());
			return atomNames;
		}
	}
		
	@Override
	public NodeGroupDefinition getGroupDefinition() {
		Set<String> backboneAtomNames = getBackboneAtomNames();
		return new BackboneGroupDefinition(backboneAtomNames);
	}

}
