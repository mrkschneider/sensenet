package com.tcb.sensenet.internal.task.structureViewer;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.NodeResidueFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.netmap.structureViewer.StructureViewer;
import com.tcb.common.util.SafeMap;


public class TransferResidueColorsTask extends AbstractTask {

	private AppGlobals appGlobals;
	
	public TransferResidueColorsTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		StructureModel model = appGlobals.structureViewerManager.getModels().get(metaNetwork);
				
		Map<Color,List<Residue>> colorMappings = createColorMappings(metaNetwork, network);
		
		model.resetColors(viewer);
		for(Entry<Color,List<Residue>> e:colorMappings.entrySet()){
			Color color = e.getKey();
			List<Residue> residues = e.getValue();
			model.colorResidues(viewer, residues, color);
		}
							
	}
	
	private Map<Color,List<Residue>> createColorMappings(
			MetaNetwork metaNetwork, CyNetworkAdapter network){
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
		CyNetworkViewAdapter networkView = appGlobals.applicationManager.getCurrentNetworkView();
		
		Map<Color,List<Residue>> colorMappings = new SafeMap<>();
		Set<Residue> residues = new HashSet<>();
		NodeResidueFactory residueFactory = new NodeResidueFactory();
		
		for(CyNode n:metaNetwork.getMetanodes()){
			Optional<Color> colorOpt = getColor(n,networkView,metaNetwork);
			if(!colorOpt.isPresent()) continue;
			Color color = colorOpt.get();
			if(!colorMappings.containsKey(color)) colorMappings.put(color, new ArrayList<>());
			Residue residue = residueFactory.create(n, rootNetwork);
			if(!residues.contains(residue)){
				colorMappings.get(color).add(residue);
				residues.add(residue);
			}
		}
		return colorMappings;
	}
	
	
	private Optional<Color> getColor(CyNode n, CyNetworkViewAdapter networkView, MetaNetwork metaNetwork){
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
		View<CyNode> nodeView = networkView.getNodeView(n);
		if(nodeView==null) {
			return getMappedColor(rootNetwork,n);
		} else {
			Color color = (Color)nodeView.getVisualProperty(BasicVisualLexicon.NODE_FILL_COLOR);
			return Optional.of(color);
		}
	}
	
	private Optional<Color> getMappedColor(CyRootNetworkAdapter rootNetwork, CyNode n){
		try {
			VisualStyle style = appGlobals.visualMappingManager.getCurrentVisualStyle();
			VisualMappingFunction<?,Paint> f = style.getVisualMappingFunction(BasicVisualLexicon.NODE_FILL_COLOR);
			CyRowAdapter row = rootNetwork.getRow(n);
			Color color = (Color)f.getMappedValue(row.getAdaptedRow());
			return Optional.of(color);
		} catch(Exception e){
			e.printStackTrace();
			return Optional.empty();
		}
		
	}

	
	
	

}
