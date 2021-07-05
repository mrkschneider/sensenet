package com.tcb.sensenet.internal.init.row;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class HiddenSubNodeRowInitializer {

	private Long headSuid;

	public HiddenSubNodeRowInitializer(Long headSuid){
		this.headSuid = headSuid;
	}
	
	public void init(CyRowAdapter hiddenRow){
		hiddenRow.set(AppColumns.METANODE_SUID, headSuid);
	}
}
