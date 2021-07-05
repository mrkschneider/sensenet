package com.tcb.sensenet.internal.task;


import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.util.ProgressTicker;

public class ProgressUpdater {
	
	private static final long intervalMillis = 100;
	private long lastUpdate;
	private ProgressTicker ticker;

	public static ProgressUpdater create(long steps){
		return new ProgressUpdater(new ProgressTicker(steps));
	}
	
	private ProgressUpdater(ProgressTicker ticker){
		this.ticker = ticker;
		this.lastUpdate = getTime();
	}
	
	public void update(TaskMonitor taskMonitor){
		long time = getTime();
		if(time - lastUpdate > intervalMillis){
			lastUpdate = time;
			taskMonitor.setProgress(ticker.get());
		}
	}
	
	public void incr(){
		ticker.incr();
	}
	
	private static long getTime(){
		return System.currentTimeMillis();
	}	
}
