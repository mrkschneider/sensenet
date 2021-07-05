package com.tcb.sensenet.internal.selection;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class SelectionTimeComparator implements Comparator<CyIdentifiable> {
	private CyRootNetworkAdapter network;

	public SelectionTimeComparator(CyRootNetworkAdapter network){
		this.network = network;
	}

	@Override
	public int compare(CyIdentifiable a, CyIdentifiable b) {
		CyRowAdapter aRow = network.getHiddenRow(a);
		CyRowAdapter bRow = network.getHiddenRow(b);
		LocalDateTime aTime = getSelectionTime(aRow);
		LocalDateTime bTime = getSelectionTime(bRow);
		return aTime.compareTo(bTime);
	}
	
	private LocalDateTime getSelectionTime(CyRowAdapter row){
		String content = row.get(AppColumns.SELECTION_TIME, String.class);
		try {
			return LocalDateTime.parse(content);
		} catch(Exception e){
			return LocalDateTime.MIN;
		}
	}
	
	
}
