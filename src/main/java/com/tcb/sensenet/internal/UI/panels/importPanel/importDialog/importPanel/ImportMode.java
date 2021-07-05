package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.AbstractInteractionsImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.AifImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.CpptrajContactsImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.CpptrajHbondImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.DsspImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.PdbContactImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.PdbHbondImporterPanel;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;

public enum ImportMode {
	 AIF, CPPTRAJ_HBOND, CPPTRAJ_CONTACTS, PDB_HBOND, PDB_CONTACTS, DSSP;
	
	public String toString(){
		switch(this){
		case AIF:
			return "AIF file";
		case CPPTRAJ_HBOND:
			return "CPPTRAJ H-bonds";
		case CPPTRAJ_CONTACTS:
			return "CPPTRAJ nativecontacts";
		case PDB_HBOND:
			return "PDB structure H-bonds";
		case PDB_CONTACTS:
			return "PDB structure contacts";
		case DSSP:
			return "DSSP secondary structure";
		default:
			throw new RuntimeException("Unknown Import mode");
		}
	}
	
	public AbstractInteractionsImporterPanel createPanel(
			ImportNetworkDialog dialog, FileUtil fileUtil, AppProperties appProperties){
		switch(this){
		case AIF:
			return new AifImporterPanel(dialog, fileUtil,appProperties);
		case CPPTRAJ_HBOND:
			return new CpptrajHbondImporterPanel(dialog, fileUtil,appProperties);
		case CPPTRAJ_CONTACTS:
			return new CpptrajContactsImporterPanel(dialog, fileUtil,appProperties);
		case PDB_HBOND:
			return new PdbHbondImporterPanel(dialog, fileUtil, appProperties);
		case PDB_CONTACTS:
			return new PdbContactImporterPanel(dialog, fileUtil, appProperties);
		case DSSP:
			return new DsspImporterPanel(dialog,fileUtil,appProperties);
			
		default:
			throw new RuntimeException("Unknown Import mode");
		}
	}
	
	public static ImportMode getDefault(AppProperties appProperties){
		ImportMode importMode = appProperties.getEnumOrDefault(
				ImportMode.class, AppProperty.DEFAULT_INTERACTION_IMPORTER);
		return importMode;
	}
	
	public static int getDefaultIndex(AppProperties appProperties){
		ImportMode defaultMode = getDefault(appProperties);
		return defaultMode.ordinal();
	}
	
}
