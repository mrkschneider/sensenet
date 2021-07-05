package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.OldMatImporter;


public class OldMatImporterPanel extends AifImporterPanel {
	
	public OldMatImporterPanel(ImportNetworkDialog parentDialog, FileUtil fileUtil, AppProperties appProperties){
		super(parentDialog, fileUtil, appProperties);
	}
		
	protected InteractionImporter createInteractionImporter() throws IOException,IllegalArgumentException {
		File matFile = openFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No .mat file chosen"));
		return new OldMatImporter(
				matFile.toPath());
	}
			
}
