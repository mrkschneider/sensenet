package com.tcb.sensenet.internal.task.table;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalkMode;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.table.CsvTableImporter;
import com.tcb.sensenet.internal.task.diffusion.RandomWalkTask.Config;
import com.tcb.sensenet.internal.util.Nullable;
import com.tcb.csv.CSV;
import com.tcb.csv.CSV_Reader;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class ImportCsvTableTask extends AbstractTask {
	
	private AppGlobals appGlobals;
	private Config config;
	
	@AutoValue
	public static abstract class Config implements ParameterReporter {
		
		public static Config create(String csvPath, String keyColumn, TableType tableType){
			return new AutoValue_ImportCsvTableTask_Config(
					csvPath,keyColumn,tableType);
		}
		
		public abstract String getCsvPath();
		public abstract String getKeyColumn();
		public abstract TableType getTableType();
						
	}
	
	public ImportCsvTableTask(AppGlobals appGlobals, Config config){
		this.appGlobals = appGlobals;
		this.config = config;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CSV csv = new CSV_Reader(config.getCsvPath(),",",true).getCSV();
		CsvTableImporter importer = new CsvTableImporter();
		importer.load(csv, metaNetwork, config.getKeyColumn(), config.getTableType());
	}
	
	
}
