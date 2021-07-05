package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.NetworkImportNameGenerator;
import com.tcb.sensenet.internal.properties.AppProperties;

public class ImportPanelMaster extends JPanel {
	
	private ImportPanel importPanelMaster;
	private DifferenceNetworkPanel differenceNetworkPanel;
	
	public ImportPanelMaster(ImportNetworkDialog dialog, AppGlobals appGlobals){
		AppProperties appProperties = appGlobals.appProperties;
		this.importPanelMaster = new ImportPanel("Import networks", 
				dialog, appGlobals, appProperties);
		this.differenceNetworkPanel =
				new DifferenceNetworkPanel(dialog,
						appGlobals);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(importPanelMaster);
		this.add(differenceNetworkPanel);
	}
	
	public ImportPanel getMainImportPanel(){
		return importPanelMaster;
	}
	
	public DifferenceNetworkPanel getDifferenceNetworkPanel(){
		return differenceNetworkPanel;
	}
	
	public String getImportNetworkName(){
		NetworkImportNameGenerator namer = new NetworkImportNameGenerator();
		if(differenceNetworkPanel.isChecked()){
			return namer.generateName(importPanelMaster.getMainFileNames(),
					differenceNetworkPanel.getMainFileNames());
		} else {
			return namer.generateName(importPanelMaster.getMainFileNames());
		}
	}
	
	
}
