package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class HiddenMetaNodeRowInitializer {

	public void init(CyRowAdapter hiddenRow){
		hiddenRow.set(AppColumns.IS_METANODE, true);
		hiddenRow.set(AppColumns.IMPORTED, false);
	}
}
