package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel.GroupDefinitionPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.DifferenceNetworkPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.ImportPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.ImportPanelMaster;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.networkOptionsPanel.NetworkOptionsPanel;
import com.tcb.sensenet.internal.UI.util.ComposingObservable;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.task.create.factories.ActionCreateMetaNetworkTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.differenceImporter.DifferenceImporter;





public class ImportNetworkDialog extends DefaultDialog {
		
	private AppGlobals appGlobals;
		
	private GroupDefinitionPanel groupDefinitionPanel;
	protected NetworkOptionsPanel networkOptionsPanel;
	
	private ComposingObservable dialogStateChanged;
	private ImportPanelMaster importMasterPanel;
			
	public ImportNetworkDialog(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.dialogStateChanged = new ComposingObservable();
			
		this.importMasterPanel = new ImportPanelMaster(this, appGlobals);
		this.groupDefinitionPanel = new GroupDefinitionPanel(appGlobals);
		this.networkOptionsPanel = new NetworkOptionsPanel(this, appGlobals);

		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.setTitle("Import network");
		
		this.add(importMasterPanel);
		this.add(groupDefinitionPanel);
		this.add(networkOptionsPanel);
		
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));

		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.pack();
	}
	
	private void confirm(){
		ImportConfig config = createImportConfig();
		
		TaskIterator tasks = new ActionCreateMetaNetworkTaskFactory(appGlobals)
				.createTaskIterator(config);
		appGlobals.taskManager.execute(tasks);
		
		dispose();
	}
	
	private ImportConfig createImportConfig() {
			ImportConfig c = ImportConfig.create(
					getInteractionImporter(),
					getNetworkName(),
					getCutoffValue(),
					getCutoffColumn(),
					getGroupDefinition(),
					getShouldCreateVisualStyle()
					);
		return c;
	}
	
	private Columns getCutoffColumn(){
		return getNetworkOptionsPanel().getCutoffColumn();
	}
	
	private String getNetworkName(){
		return getNetworkOptionsPanel().getNetworkName();
	}
	
	public InteractionImporter getInteractionImporter(){
		InteractionImporter importer = null;
		ImportPanel mainImportPanel = getImportPanel().getMainImportPanel();
		DifferenceNetworkPanel differenceNetworkPanel = getImportPanel()
				.getDifferenceNetworkPanel();
		try {
			importer = mainImportPanel.getCombinedInteractionImporter();	
						
			if(differenceNetworkPanel.isChecked()){
				InteractionImporter referenceImporter = 
						differenceNetworkPanel.getCombinedInteractionImporter();
				importer = new DifferenceImporter(referenceImporter,importer);
			}
			 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return importer;
	}
	
	private NodeGroupDefinition getGroupDefinition(){
		return getGroupDefinitionPanel().getGroupDefinition();
	}
		
	private Double getCutoffValue(){
		return getNetworkOptionsPanel().getTimeFractionCutoff();
	}
		
	private Boolean getShouldCreateVisualStyle(){
		return getNetworkOptionsPanel().getShouldCreateVisualStyle();
	}
		
	public void signalChanges(){
		dialogStateChanged.setChanged();
		dialogStateChanged.notifyObservers();
	}
	
	public ComposingObservable getState(){
		return dialogStateChanged;
	}
							
	public GroupDefinitionPanel getGroupDefinitionPanel(){
		return groupDefinitionPanel;
	}
	
	public NetworkOptionsPanel getNetworkOptionsPanel(){
		return networkOptionsPanel;
	}
	
	public ImportPanelMaster getImportPanel(){
		return importMasterPanel;
	}
		
}
