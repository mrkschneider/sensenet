package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import javax.swing.JDialog;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.netmap.fileFormat.FormatCollection;



public class TopologyImportPanel extends SingleStructureImportPanel {

	public TopologyImportPanel(JDialog dialog, StructureViewerManager viewerManager, FileUtil fileUtil) {
		super(dialog, viewerManager, fileUtil);
	}
	
	@Override
	protected FormatCollection getFileFormatCollection(ViewerType viewerType){
		return viewerType.getTopologyFormatCollection();
	}

}
