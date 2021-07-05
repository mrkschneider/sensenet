package com.tcb.sensenet.internal.map.node;

import java.util.Map;
import java.util.Optional;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.labeling.LabelSettings;
import com.tcb.sensenet.internal.labeling.NodeLabelFactory;
import com.tcb.sensenet.internal.labeling.NodeLabeler;
import com.tcb.sensenet.internal.labeling.ShortNodeLabelFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.util.MapUtil;

public class NodeNameMapper implements NodeMapper {

	private NodeLabelFactory nodeLabelFac;
	private Map<String,CyNode> refLabelMap;

	public static NodeNameMapper create(MetaNetwork refMetaNetwork, LabelSettings labelSettings) {
		NodeLabelFactory nodeLabelFac = new ShortNodeLabelFactory(labelSettings);
		NodeLabeler nodeLabeler = getNodeLabeler(refMetaNetwork, nodeLabelFac);
		Map<String,CyNode> refLabelMap = MapUtil.createMap(
						refMetaNetwork.getNodes(),
						n -> nodeLabeler.generateLabel(n));
		return new NodeNameMapper(nodeLabelFac,refLabelMap);
	}
	
	private NodeNameMapper(
			NodeLabelFactory nodeLabelFac,
			Map<String,CyNode> refLabelMap) {
		this.nodeLabelFac = nodeLabelFac;
		this.refLabelMap = refLabelMap;
	}
	
	
	@Override
	public Optional<CyNode> getMapped(CyNode node, MetaNetwork metaNetwork) {
		NodeLabeler nodeLabeler = getNodeLabeler(metaNetwork, nodeLabelFac);
		String label = nodeLabeler.generateLabel(node);
		if(refLabelMap.containsKey(label))
			return Optional.of(refLabelMap.get(label));
		else
			return Optional.empty();
	}
	
	private static NodeLabeler getNodeLabeler(MetaNetwork metaNetwork, NodeLabelFactory nodeLabelFac) {
		return new NodeLabeler(metaNetwork.getRootNetwork(),nodeLabelFac);
	}

}
