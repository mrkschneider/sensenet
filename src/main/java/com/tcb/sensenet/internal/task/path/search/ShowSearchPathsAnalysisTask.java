package com.tcb.sensenet.internal.task.path.search;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.PathTableView;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;


public class ShowSearchPathsAnalysisTask extends AbstractResultTask {
	
	private SearchPathsTaskConfig config;
	private AppGlobals appGlobals;
	private SearchPathsTask task;
	
	protected volatile boolean cancelled;
	
	public ShowSearchPathsAnalysisTask(
			ObjMap results,
			SearchPathsTaskConfig config,
			AppGlobals appGlobals
			){
		super(results);
		this.config = config;
		this.appGlobals = appGlobals;
	}

	@Override
	public ObjMap start(TaskMonitor tskMon) throws Exception {
		TableView table = createTable(config.getNetwork(), results);
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		panel.clear();
		panel.showTable(table, "Paths");
		return results;
	}
		
	private TableView createTable(CyNetworkAdapter network, ObjMap results){	
		TableView table = new PathTableView(network,results,appGlobals.fileUtil);
		return table;
	}
	
	@Override
	public void cancel(){
		if(task!=null) task.cancel();
		cancelled=true;
	}
	

	
	

}
