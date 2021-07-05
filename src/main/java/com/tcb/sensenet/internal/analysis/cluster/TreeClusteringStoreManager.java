package com.tcb.sensenet.internal.analysis.cluster;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.serialization.SuidUpdater;
import com.tcb.sensenet.internal.util.BasicMapManager;
import com.tcb.sensenet.internal.util.BasicMetaNetworkManager;
import com.tcb.sensenet.internal.util.BasicNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;

public class TreeClusteringStoreManager extends BasicMetaNetworkManager<List<List<Cluster>>> implements Serializable {
	private static final long serialVersionUID = 1L;

}
