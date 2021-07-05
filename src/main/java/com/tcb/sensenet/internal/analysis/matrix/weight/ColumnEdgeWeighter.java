package com.tcb.sensenet.internal.analysis.matrix.weight;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.data.Columns;

public class ColumnEdgeWeighter implements EdgeWeighter {

	private String column;
	private MetaNetwork metaNetwork;

	public ColumnEdgeWeighter(String column, MetaNetwork metaNetwork){
		this.column = column;
		this.metaNetwork = metaNetwork;
	}
	
	@Override
	public Double weight(CyEdge edge) {
		return metaNetwork.getRow(edge).getRawMaybe(column, Double.class)
				.orElse(Double.NaN);
	}

}
