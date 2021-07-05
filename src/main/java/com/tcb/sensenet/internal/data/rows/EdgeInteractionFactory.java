package com.tcb.sensenet.internal.data.rows;

import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.atoms.interactions.Timeline;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;




public class EdgeInteractionFactory {
	
	private TimelineManager timelineManager;
	
	public EdgeInteractionFactory(TimelineManager timelineManager){
		this.timelineManager = timelineManager;
	}
	
	public Interaction create(CyEdge e, MetaNetwork metaNetwork, boolean copyTimeline){
		CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
		
		CyNode source = e.getSource();
		CyNode target = e.getTarget();
		NodeAtomFactory atomFactory = new NodeAtomFactory();
		Atom sourceAtom = atomFactory.create(source, rootNetwork);
		Atom targetAtom = atomFactory.create(target, rootNetwork);
				
		
		double[] timeline = getTimeline(e,metaNetwork);
		if(copyTimeline) timeline = Arrays.copyOf(timeline, timeline.length);
		
		String interactionType = rootNetwork.getRow(e).get(
				DefaultColumns.SHARED_INTERACTION, String.class);
		List<Atom> bridge = createBridgeAtoms(e,sourceAtom.getResidue(),rootNetwork);
		Interaction interaction = Interaction.create(
				sourceAtom,targetAtom,bridge,
				Timeline.create(timeline),interactionType);
		return interaction;
	}
	
	public Interaction create(CyEdge e, MetaNetwork metaNetwork){
		return create(e,metaNetwork,true);
	}
	
	private List<Atom> createBridgeAtoms(CyEdge e,
			Residue residue, CyRootNetworkAdapter rootNetwork){
		List<String> bridgeNames =  rootNetwork.getRow(e)
				.getList(AppColumns.BRIDGE_NAME, String.class);
		List<Atom> atoms = bridgeNames.stream()
				.map(s -> Atom.create(s, residue))
				.collect(Collectors.toList());
		return atoms;
	}
	
	private double[] getTimeline(CyEdge e, MetaNetwork metaNetwork){
		TimelineStore timelineStore = timelineManager.get(metaNetwork);
		Integer size = metaNetwork.getHiddenDataRow().get(AppColumns.TIMELINE_LENGTH, Integer.class);
		if(!timelineStore.containsKey(e)) return new double[size];
		return timelineStore.get(e).getData();
	}
	
}
