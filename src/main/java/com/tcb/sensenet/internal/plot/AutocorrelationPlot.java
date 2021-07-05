package com.tcb.sensenet.internal.plot;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.AutocorrelationAggregator;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.checks.MetaNetworkChecks;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.sensenet.internal.util.RangeUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.mdAnalysis.statistics.regression.Regression;


public class AutocorrelationPlot extends MetaEdgePlot {

	private AppGlobals appGlobals;
	private Integer blocks;

	public AutocorrelationPlot(
			CyEdge metaEdge,
			MetaNetwork metaNetwork,
			FrameWeightMethod weightMethod,
			Integer blocks,
			AppGlobals appGlobals){
		super(metaEdge,metaNetwork, weightMethod);
		super.init();
		this.appGlobals = appGlobals;
		this.blocks = blocks;
	}
	
	@Override
	public String getPlotTitle() {
		return "Autocorrelation"; 
	}

	@Override
	public void plot() throws InvalidColumnException {
		MetaNetworkChecks.disallowDifferenceNetworks(metaNetwork);
		TimelineType timelineType = metaNetwork.getTimelineType();
		NetworkMetaTimelineFactory metaTimelineFactory = 
				new MetaTimelineFactoryManager(appGlobals.state.timelineManager).getFactory(weightMethod, timelineType);
		MetaTimeline metaTimeline = metaTimelineFactory
				.create(metaEdge, metaNetwork);
		
		AutocorrelationTimeWeightStrategy mergeStrategy = new MaxAutocorrelationTimeWeightStrategy();
		ObjMap analysis = new AutocorrelationAggregator(blocks, mergeStrategy)
				.aggregate(metaTimeline);
		@SuppressWarnings("unchecked")
		List<AutocorrelationAnalysisAdapter> autocorrelations = analysis.get("autocorrelations",List.class);
				
		List<Double> X = RangeUtil.doubleRange(0, autocorrelations.get(0).getAutocorrelations().size());
		Double autocorrelationTime = analysis.get("autocorrelationTime",Double.class);
				
		if(autocorrelations.size() > colors.length) throw new IllegalArgumentException("Too many datasets to plot");
		
		for(int i=0;i<autocorrelations.size();i++){
			Paint color = colors[i];
			AutocorrelationAnalysisAdapter autocorrelation = autocorrelations.get(i);
			List<Double> Y = autocorrelation.getAutocorrelations();
			plotValues(X, Y,
					color, 
					getDefaultStroke(),
					String.format("block %d", i));
			Regression regression = autocorrelation.getRegression();
			plotValues(X, regression.regressionLine(0,Y.size()),
					color,
					getDashedStroke(),
					String.format("block %d (Exp. fit)",i));
		}
		
		addVline(autocorrelationTime, Color.black, getDashedStroke());
		addHline(0., Color.darkGray, getPlot().getRenderer().getBaseStroke());
		
		addToLegend("Max autocorrelation time", 
				((XYLineAndShapeRenderer)getPlot().getRenderer()).getLegendLine(), Color.black,  getDashedStroke());
	}
	
	@Override
	public String getXLabel() {
		return "k";
	}

	@Override
	public String getYLabel() {
		return "C(k)";
	}
	
	
	

	
	
}

