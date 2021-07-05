package com.tcb.sensenet.internal.task.aggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.CyIdentifiableTableView;
import com.tcb.sensenet.internal.UI.table.DefaultTableViewFactory;
import com.tcb.sensenet.internal.UI.table.TableStatistics;
import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.UI.util.TextPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.histogram.HistogramPlot;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class ShowColumnsTask extends AbstractTask {

	private List<String> additionalColumns;
	private AppGlobals appGlobals;
	private TaskLogType taskLogType;
	private TableType tableType;
	
	public ShowColumnsTask(
			TableType tableType,
			List<String> columns,
			TaskLogType taskLogType,
			AppGlobals appGlobals){
		this.additionalColumns = columns;
		this.appGlobals = appGlobals;
		this.taskLogType = taskLogType;
		this.tableType = tableType;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();

		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		panel.clear();
		
		List<? extends CyIdentifiable> cyIds = getCyIds(metaNetwork);
		
		List<List<?>> values = getValues(cyIds,metaNetwork,network);

		DefaultTableViewFactory tableViewFactory = new DefaultTableViewFactory();
		CyIdentifiableTableView table = tableViewFactory.create(
					cyIds, additionalColumns, values, metaNetwork, network, appGlobals.fileUtil);
			
		LogBuilder log = appGlobals.state.logManager.get(metaNetwork).get(taskLogType);
		
		panel.addTopPanel(log);
		panel.showTable(table, "Analysis results");
		showStatistics(panel, table);		
	}
	
	private List<? extends CyIdentifiable> getCyIds(MetaNetwork metaNetwork){
		switch(tableType){
		case NODE: return metaNetwork.getNodes();
		case EDGE: return metaNetwork.getEdges();
		default: throw new UnsupportedOperationException();
		}
	}
	
	private List<List<?>> getValues(
			List<? extends CyIdentifiable> cyIds,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){

		List<List<?>> values = new ArrayList<>();
		for(String column:additionalColumns){
			List<Object> lst = new ArrayList<>();
			for(CyIdentifiable cyId:cyIds){
				CyRowAdapter row = metaNetwork.getRow(cyId);
				if( (!row.contains(column)) && network.contains(cyId.getSUID())){
					row = network.getRow(cyId);
				}
				Object v = row.getRawMaybe(column, Object.class)
						.orElse(null);
				lst.add(v);
			}
			values.add(lst);
		}
		return values;
	}
	
	private void showStatistics(
			ResultPanel panel,
			TableView table){
		TableView statTable = new TableStatistics(appGlobals).createStatisticsTable(table);
		panel.showTable(statTable, "Column statistics");
	}
	
}
