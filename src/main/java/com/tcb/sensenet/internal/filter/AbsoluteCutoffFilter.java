package com.tcb.sensenet.internal.filter;

import java.util.Optional;

import org.cytoscape.model.CyIdentifiable;

import com.tcb.sensenet.internal.util.AbsoluteCutoffLimit;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.common.util.Predicate;

public class AbsoluteCutoffFilter<T extends CyIdentifiable> implements Predicate<T> {

	private CyRootNetworkAdapter rootNetwork;
	private Columns cutoffColumn;
	private AbsoluteCutoffLimit cutoffLimit;

	public AbsoluteCutoffFilter(CyRootNetworkAdapter rootNetwork, Columns cutoffColumn, Double cutoffValue){
		this.rootNetwork = rootNetwork;
		this.cutoffColumn = cutoffColumn;
		this.cutoffLimit = new AbsoluteCutoffLimit(cutoffValue,Double.MAX_VALUE);
	}
		
	@Override
	public boolean test(CyIdentifiable cyId){
		Optional<Double> val = rootNetwork.getRow(cyId).getMaybe(cutoffColumn, Double.class);
		if(val.isPresent()){
			return cutoffLimit.test(val.get());
		} else {
			return false;
		}
	}

}
