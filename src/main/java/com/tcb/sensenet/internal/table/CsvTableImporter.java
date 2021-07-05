package com.tcb.sensenet.internal.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.data.RowAutoTypeSetter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.util.NumericTypeGuesser;
import com.tcb.csv.CSV;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class CsvTableImporter {

	public void load(CSV csv, MetaNetwork metaNetwork,
			String keyColumn, TableType tableType){
		RowAutoTypeSetter setter = new RowAutoTypeSetter();
		List<String> header = new ArrayList<>(csv.getHeader());
		header.remove(keyColumn);
		if(header.contains(keyColumn)) 
			throw new IllegalArgumentException("Duplicate key column");
		List<String> keys = csv.getColumnByName(keyColumn);
		for(String h:header){
			List<String> col = csv.getColumnByName(h);
			CyTableAdapter table = getTable(metaNetwork,tableType,h);
			if(table==null) continue;
			Map<String,CyRowAdapter> rows = getGroupedRows(table,keyColumn);
			for(int i=0;i<col.size();i++){
				String k = keys.get(i);
				String v = col.get(i);
				CyRowAdapter r = rows.get(k);
				setter.set(r, h, v);
			}
		}
	}
	
	private CyTableAdapter getTable(MetaNetwork metaNetwork, TableType type, String column){
		CyTableAdapter table = null;
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
		switch(type){
		case NODE: table = rootNetwork.getSharedNodeTable(); break;
		case EDGE: table = rootNetwork.getSharedEdgeTable(); break;
		default: throw new UnsupportedOperationException();
		}
		if(table.columnExists(column)) return table;
		else return null;
	}
	
	private Map<String,CyRowAdapter> getGroupedRows(CyTableAdapter table, String keyColumn){
		SafeMap<String,CyRowAdapter> result = new SafeMap<>();
		for(CyRowAdapter row:table.getAllRows()){
			String key = row.getRaw(keyColumn, String.class);
			result.put(key, row);
		}
		return result;
	}
	
	
	
}
