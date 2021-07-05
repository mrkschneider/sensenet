package com.tcb.sensenet.internal.meta.network;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.tree.tree.Tree;




public interface MetaNetwork extends Serializable {
	public UUID getUuid();
	public CyRootNetworkAdapter getRootNetwork();
	public List<CyNetworkAdapter> getSubNetworks();
	public Tree getTree();
		
	public TimelineType getTimelineType();
	
	public CyNode getNode(Long suid);
	public CyEdge getEdge(Long suid);
	
	public List<CyNode> getNodes();
	public List<CyEdge> getEdges();
	
	public List<CyNode> getMetanodes();
	public List<CyEdge> getMetaedges();
	public List<CyNode> getSubnodes(CyNode n);
	public List<CyEdge> getSubedges(CyEdge e);
	
	public List<CyRowAdapter> getSubRows(CyNode n);
	public List<CyRowAdapter> getSubRows(CyEdge e);
	public List<CyRowAdapter> getHiddenSubRows(CyNode n);
	public List<CyRowAdapter> getHiddenSubRows(CyEdge e);
	
	public CyRowAdapter getRow(CyIdentifiable c);
	public CyRowAdapter getHiddenRow(CyIdentifiable c);
	
	public CyRowAdapter getHiddenDataRow();
	public CyRowAdapter getSharedDataRow();
	public Long getSUID();
	public String getName();
	}
