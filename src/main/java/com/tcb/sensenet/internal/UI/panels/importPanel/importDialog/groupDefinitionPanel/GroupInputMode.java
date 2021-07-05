package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel;

import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;



public enum GroupInputMode {
	AMINO_ACID,BACKBONE_SIDECHAIN;
	
	public String toString(){
		switch(this){
		case AMINO_ACID: return "Amino acids";
		case BACKBONE_SIDECHAIN: return "Backbone/Sidechain";
		default: throw new IllegalArgumentException("Unknown GroupDefinitionMode");
		}
	}
		
}
