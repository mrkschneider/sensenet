package com.tcb.sensenet.internal.UI.table;

import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public interface CyIdentifiableTableViewFactory {
	public CyIdentifiableTableView create(
			List<? extends CyIdentifiable> cyIds,
			List<String> additionalColumnNames,
			List<List<?>> additionalData,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network,
			FileUtil fileUtil);
}
