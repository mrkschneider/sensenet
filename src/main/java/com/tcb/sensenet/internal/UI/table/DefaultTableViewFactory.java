package com.tcb.sensenet.internal.UI.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class DefaultTableViewFactory {
		
	public CyIdentifiableTableView create(
			List<? extends CyIdentifiable> cyIds,
			List<String> additionalColumnNames,
			List<List<?>> additionalData,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			FileUtil fileUtil){
		List<Long> suids = cyIds.stream()
				.map(e -> e.getSUID())
				.collect(Collectors.toList());
		List<CyRowAdapter> rows = cyIds.stream()
				.map(e -> metaNetwork.getRow(e))
				.collect(Collectors.toList());
		List<Columns> baseColumns = getBaseColumns();
		List<String> columnNames = baseColumns.stream()
				.map(c -> c.toString())
				.collect(Collectors.toList());
		List<List<?>> values = 
				CyIdentifiableTableView.getValues(rows, baseColumns);
		columnNames.addAll(additionalColumnNames);
		values.addAll(additionalData);
		return new CyIdentifiableTableView(suids,columnNames,values,network,fileUtil);
	}
	
	private List<Columns> getBaseColumns(){
		return Arrays.asList(
				DefaultColumns.SHARED_NAME,
				AppColumns.LABEL);
	}
	
}
