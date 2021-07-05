package com.tcb.sensenet.internal.plot;

import java.util.Map;

public class ClusterTimelinePlot extends AbstractTimelinePlot {
	
	private String name;
	private Map<Integer, ? extends Number> timeline;

	public ClusterTimelinePlot(Map<Integer,? extends Number> timeline, String name){
		this.timeline = timeline;
		this.name = name;
		super.init();
		
		this.chart.removeLegend();
	}
	
	@Override
	public String getYLabel(){
		return "Cluster index";
	}
	
	protected Map<Integer,? extends Number> createTimeline(){
		return timeline;
	}

	@Override
	protected String getLineName() {
		return "";
	}

	@Override
	public String getPlotTitle() {
		return name;
	}

	@Override
	public String getPlotSubTitle() {
		return "";
	}

	@Override
	public String getXLabel() {
		return "Frame";
	}

}
