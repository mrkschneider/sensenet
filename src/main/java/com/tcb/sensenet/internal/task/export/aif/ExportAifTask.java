package com.tcb.sensenet.internal.task.export.aif;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.EdgeInteractionFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.aifgen.importer.aifImporter.AifWriter;

public class ExportAifTask extends AbstractTask {

	private AppGlobals appGlobals;
	private ExportAifTaskConfig config;

	public ExportAifTask(ExportAifTaskConfig config, AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		EdgeInteractionFactory fac = new EdgeInteractionFactory(appGlobals.state.timelineManager);
		List<Interaction> interactions = getImportedEdges(metaNetwork).stream()
				.map(e -> fac.create(e, metaNetwork))
				.sorted(new InteractionComparator())
				.collect(Collectors.toList());
		AifWriter writer = new AifWriter(interactions, metaNetwork.getTimelineType());
		writer.write(config.getPath());
	}
	
	private List<CyEdge> getImportedEdges(MetaNetwork metaNetwork){
		List<CyEdge> nonMetaEdges = metaNetwork.getEdges().stream()
				.filter(e -> metaNetwork.getHiddenRow(e)
						.get(AppColumns.IMPORTED, Boolean.class))
				.collect(Collectors.toList());
		return nonMetaEdges;
	}
	
	private class InteractionComparator implements Comparator<Interaction> {
			@Override
			public int compare(Interaction a, Interaction b) {
				return a.getType().compareTo(b.getType());
			}
	}
	
	
	
}
