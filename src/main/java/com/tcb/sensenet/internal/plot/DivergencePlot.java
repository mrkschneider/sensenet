package com.tcb.sensenet.internal.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.RangeUtil;
import com.tcb.sensenet.internal.util.ZipUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;
import com.tcb.mdAnalysis.statistics.regression.Regression;
import com.tcb.aifgen.importer.TimelineType;



public class DivergencePlot extends AbstractTimelinePlot {
	
	private List<Double> divergences;
	private Regression regression;
	private String subTitle;

	public DivergencePlot(
			List<Double> divergences,
			Regression regression,
			String subTitle){
		this.divergences = divergences;
		this.regression = regression;
		this.subTitle = subTitle;
		super.init();
		
		this.chart.removeLegend();
	}
			
	@Override
	public String getPlotTitle() {
		return "";
	}

	@Override
	public String getXLabel() {
		return "Replica frames";
	}

	@Override
	public String getYLabel() {
		return "Divergence";
	}
	
	@Override
	protected String getLineName() {
		return "";
	}

	@Override
	public String getPlotSubTitle() {
		return subTitle;
	}

	@Override
	protected Map<Integer, ? extends Number> createTimeline() {
		return ZipUtil.zipMapIndex(divergences);
	}
	
	@Override
	protected Double getYTickInterval() {
		return 0.1;
	}
	
	@Override
	protected Double getLowerBound() {
		return 0.0;
	}
	
	@Override
	public void plot() throws InvalidColumnException {
		super.plot();
		plotValues(
				RangeUtil.doubleRange(0, divergences.size()),
				regression.regressionLine(0,divergences.size()),
				Color.blue,
				getDashedStroke(),
				String.format("Exp. fit"));
	}
		
	@Override
	protected XYLineAndShapeRenderer createRenderer(){
		XYLineAndShapeRenderer renderer = super.createRenderer();
		renderer.setBaseShapesVisible(false);
		return renderer;
	}
	
	

}
