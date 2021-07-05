package com.tcb.sensenet.internal.init.row;


import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;


public class HiddenEdgeRowInitializer {
	private Interaction interaction;

	public HiddenEdgeRowInitializer(Interaction interaction){
		this.interaction = interaction;
	}
	
	public void init(CyRowAdapter hiddenEdgeRow){
		hiddenEdgeRow.set(AppColumns.IS_METAEDGE, false);
		hiddenEdgeRow.set(AppColumns.IMPORTED, true);
	}
}
