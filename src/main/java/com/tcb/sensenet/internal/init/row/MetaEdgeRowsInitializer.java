package com.tcb.sensenet.internal.init.row;

import org.cytoscape.model.CyEdge;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.edge.EdgeType;
import com.tcb.sensenet.internal.meta.edge.EdgeTypeFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class MetaEdgeRowsInitializer {
	
	private CyRootNetworkAdapter rootNetwork;

	public MetaEdgeRowsInitializer(CyRootNetworkAdapter rootNetwork){
		this.rootNetwork = rootNetwork;
	}
		
	public void init(CyEdge edge){
		CyRowAdapter sourceNodeRow = rootNetwork.getRow(edge.getSource());
		CyRowAdapter targetNodeRow = rootNetwork.getRow(edge.getTarget());
		CyRowAdapter edgeRow = rootNetwork.getRow(edge);
		CyRowAdapter hiddenEdgeRow = rootNetwork.getHiddenRow(edge);
		
		EdgeType metaEdgeType = setEdgeType(edgeRow, edge);
		setSourceTargetNames(edgeRow,sourceNodeRow,targetNodeRow);
		setSharedName(edgeRow,sourceNodeRow,targetNodeRow,metaEdgeType);
		setGroupTag(edgeRow,sourceNodeRow,targetNodeRow);
		
		hiddenEdgeRow.set(AppColumns.IMPORTED, false);
	}
		
	private void setSourceTargetNames(CyRowAdapter edgeRow, CyRowAdapter sourceNodeRow,
			CyRowAdapter targetNodeRow){
		String sourceRowName = sourceNodeRow.get(DefaultColumns.SHARED_NAME, String.class);
		String targetRowName = targetNodeRow.get(DefaultColumns.SHARED_NAME, String.class);
		
		String sourceChain = sourceNodeRow.get(AppColumns.CHAIN, String.class);
		String targetChain = targetNodeRow.get(AppColumns.CHAIN, String.class);
		
		edgeRow.set(AppColumns.SOURCE_NODE_NAME,sourceRowName);
		edgeRow.set(AppColumns.TARGET_NODE_NAME,targetRowName);
		
		edgeRow.set(AppColumns.SOURCE_CHAIN, sourceChain);
		edgeRow.set(AppColumns.TARGET_CHAIN, targetChain);
	}

	private EdgeType setEdgeType(CyRowAdapter edgeRow, CyEdge edge){
		EdgeTypeFactory edgeTypeFactory = new EdgeTypeFactory(rootNetwork);
		EdgeType edgeType = edgeTypeFactory.getEdgeType(edge);
		edgeRow.set(DefaultColumns.TYPE, edgeType.toString());
		return edgeType;
	}
	
	private void setSharedName(CyRowAdapter edgeRow, CyRowAdapter sourceRow,
			CyRowAdapter targetRow, EdgeType metaEdgeType){
		String sourceName = sourceRow.get(DefaultColumns.SHARED_NAME, String.class);
		String targetName = targetRow.get(DefaultColumns.SHARED_NAME, String.class);
		String interactionType = edgeRow.get(DefaultColumns.SHARED_INTERACTION, String.class);
		String bridgeName = "";
		String sharedName = new StandardizedEdgeNameGenerator().getName(sourceName, targetName, interactionType, bridgeName);
		edgeRow.set(DefaultColumns.SHARED_NAME, sharedName);
	}
	
	private void setGroupTag(CyRowAdapter edgeRow, CyRowAdapter sourceRow,
			CyRowAdapter targetRow){
		String edgeGroupTag = new EdgeGroupTagFactory().create(sourceRow, targetRow);
		edgeRow.set(AppColumns.GROUP_TAG, edgeGroupTag);
	}
		
}
