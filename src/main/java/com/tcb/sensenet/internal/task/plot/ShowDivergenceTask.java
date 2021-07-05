package com.tcb.sensenet.internal.task.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.CyIdentifiableTableView;
import com.tcb.sensenet.internal.UI.table.DefaultTableViewFactory;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.TimelineCorrelationAnalysis;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.plot.DivergencePlot;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.mdAnalysis.statistics.regression.Regression;
import com.tcb.aifgen.importer.TimelineType;

public class ShowDivergenceTask extends AbstractResultTask {

	private AppGlobals appGlobals;

	public ShowDivergenceTask(ObjMap results, AppGlobals appGlobals){
		super(results);
		this.appGlobals = appGlobals;
	}
	
	@Override
	public ObjMap start(TaskMonitor tskMon) throws Exception {
				
		List<Double> networkDivergences = results.getList("networkDivergences",Double.class);
		Regression networkDivergencesRegression = results.get("networkDivergencesRegression",
				Regression.class);
		String divergenceLabel = results.get("divergenceLabel",String.class);
				
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();

		Plot plot = new DivergencePlot(networkDivergences,networkDivergencesRegression,
				divergenceLabel);

		panel.showPlot(plot, "Divergences");
							
		return results;
	}
	
}
