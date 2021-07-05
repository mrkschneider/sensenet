package com.tcb.sensenet.internal.selection;

import java.time.LocalDateTime;
import java.util.Optional;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowSetRecord;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class UpdateSelectionTimeListener implements RowsSetListener {

	private AppGlobals appGlobals;
		
	private static final String selectedColumn = DefaultColumns.SELECTED.toString();

	public UpdateSelectionTimeListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(RowsSetEvent e) {
		if(!e.getColumns().contains(selectedColumn)) return;
		if(!appGlobals.state.metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		if(!isDefaultNodeOrEdgeTable(network, e.getSource())) return;
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		for(RowSetRecord r:e.getColumnRecords(selectedColumn)){
			Boolean selected = (Boolean)r.getValue();
			if(!selected) continue;
			Long suid = new CyRowAdapter(r.getRow()).get(DefaultColumns.SUID, Long.class);
			Optional<CyIdentifiable> cyId = getCyIdentifiable(suid,rootNetwork);
			if(!cyId.isPresent()) continue;
			CyRowAdapter hiddenRow = rootNetwork.getHiddenRow(cyId.get());
			LocalDateTime now = LocalDateTime.now();
			hiddenRow.set(AppColumns.SELECTION_TIME, now.toString());
		}
		
	}
	
	private Optional<CyIdentifiable> getCyIdentifiable(Long suid,CyNetworkAdapter network){
		CyIdentifiable result = null;
		result = network.getNode(suid);
		if(result!=null) return Optional.of(result);
		result = network.getEdge(suid);
		if(result!=null) return Optional.of(result);
		return Optional.empty();
	}
	
	private Boolean isDefaultNodeOrEdgeTable(CyNetworkAdapter network, CyTable table){
		CyTable nodeTable = network.getDefaultNodeTable().getAdaptedTable();
		CyTable edgeTable = network.getDefaultEdgeTable().getAdaptedTable();
		return table==nodeTable || table==edgeTable;
	}

}
