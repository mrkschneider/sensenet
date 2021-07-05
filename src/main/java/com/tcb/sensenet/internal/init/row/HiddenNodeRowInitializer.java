package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class HiddenNodeRowInitializer {

	public void init(CyRowAdapter hiddenRow){
		hiddenRow.set(AppColumns.IS_METANODE, false);
		hiddenRow.set(AppColumns.IMPORTED, true);
	}
}
