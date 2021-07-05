package com.tcb.sensenet.internal.task.labeling;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;
import com.tcb.cytoscape.cyLib.util.Range;

public class SetCustomResidueIndicesTask extends AbstractTask {

	private CyApplicationManagerAdapter applicationManager;
	private CyRootNetworkManagerAdapter rootNetworkManager;
	private SetCustomResidueIndicesTaskConfig config;
	
	private static final AppColumns baseResIndexColumn = AppColumns.RESINDEX;
	private static final AppColumns resIndexColumn = AppColumns.RESINDEX_LABEL;
	private static final AppColumns resInsertColumn = AppColumns.RESINSERT_LABEL;
	private static final AppColumns chainColumn = AppColumns.CHAIN;

	public SetCustomResidueIndicesTask(
			SetCustomResidueIndicesTaskConfig config,
			CyApplicationManagerAdapter applicationManager,
			CyRootNetworkManagerAdapter rootNetworkManager){
		this.config = config;
		this.applicationManager = applicationManager;
		this.rootNetworkManager = rootNetworkManager;
	}
		

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = rootNetworkManager.getRootNetwork(network);
		CyTableAdapter table = rootNetwork.getSharedNodeTable();
		List<CyRowAdapter> rowsInRange = getRowsInChainAndRange(table);
		for(CyRowAdapter row:rowsInRange){
			Integer oldResIndex = row.get(baseResIndexColumn, Integer.class);
			Integer newResIndex =  oldResIndex + config.getOffset();
			row.set(resIndexColumn, newResIndex);
			
			if(config.getResInsert()!=null){
				row.set(resInsertColumn, config.getResInsert());
			}
		}
	}
				
	private Integer getLastResId(List<CyRowAdapter> rowsInChain, Integer lastResIdInput) 
			throws InvalidColumnException{
		if(lastResIdInput>=0){
			return lastResIdInput;
		}else{
			// Example: Highest resId 30 and last resId Input is -1
			// last resId to modify = 30 + (-1) + 1 = 30
			return getHighestResId(rowsInChain) + lastResIdInput + 1;
		}
	}
	
	private Integer getHighestResId(List<CyRowAdapter> rowsInChain) {
		List<Integer> resIds = rowsInChain.stream()
				.map(r -> r.get(baseResIndexColumn, Integer.class))
				.collect(Collectors.toList());
		return Collections.max(resIds);
	}
	
	private List<CyRowAdapter> getRowsInChainAndRange(CyTableAdapter table){
		Integer firstResIndex = config.getFirstResIndex();
		Integer lastResIndex = config.getLastResIndex();
		
		String chain = config.getChain();
		List<CyRowAdapter> rowsInChain = getRowsInChain(table,chain);
		
		// Range is excluding final border, so +1
		List<Integer> rangeValues = 
				new Range(firstResIndex,getLastResId(rowsInChain,lastResIndex)+1).getRange();
		List<CyRowAdapter> rowsInRange = rowsInChain.stream()
				.filter(r -> rangeValues.contains(r.get(baseResIndexColumn, Integer.class)))
				.collect(Collectors.toList());
		
		return rowsInRange;
	}
	
	private List<CyRowAdapter> getRowsInChain(CyTableAdapter table, String chain){
		List<CyRowAdapter> rows = table.getAllRows();
		if(chain.equals("*")){
			return rows;
		} else {
			return rows.stream()
					.filter(r -> r.get(chainColumn, String.class).equals(chain))
					.collect(Collectors.toList());
		}				
	}
	
	
}
