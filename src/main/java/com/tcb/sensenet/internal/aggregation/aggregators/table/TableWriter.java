package com.tcb.sensenet.internal.aggregation.aggregators.table;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public class TableWriter {
	
	public void writeRows(List<? extends CyIdentifiable> cyIds,
			List<ObjMap> values,
			RowWriter writer,
			MetaNetwork metaNetwork){
		
		for(int i=0;i<cyIds.size();i++){		
			CyIdentifiable cyId = cyIds.get(i);
			ObjMap aggCol = values.get(i);
			CyRowAdapter headRow = metaNetwork.getRow(cyId);
			writer.write(headRow, aggCol);
		}
	}
}
