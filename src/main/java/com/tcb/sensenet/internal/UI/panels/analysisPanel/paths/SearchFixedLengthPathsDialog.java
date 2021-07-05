package com.tcb.sensenet.internal.UI.panels.analysisPanel.paths;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextField;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.path.FixedLengthPathSearcher;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.selection.TwoNodeSelection;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTaskConfig;
import com.tcb.sensenet.internal.task.path.search.factories.ShowPathsAnalysisTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;

public class SearchFixedLengthPathsDialog extends DefaultDialog {
	private AppGlobals appGlobals;
		
	private JTextField minLengthField;
	private JTextField maxLengthField;
	
	private AppProperties appProperties;
	
	private static final AppProperty minLengthProperty = AppProperty.PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MINLENGTH;
	private static final AppProperty maxLengthProperty = AppProperty.PATHS_SEARCH_FIXED_LENGTH_DEFAULT_MAXLENGTH;
	
	
	public SearchFixedLengthPathsDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.appProperties = appGlobals.appProperties;
		
		this.setLayout(new GridBagLayout());
		
		this.setTitle("Set path search parameters");
		
		addGeneralPanel(this);
		
		this.add(
				DialogUtil.createActionPanel(this::confirm,this::dispose),
				getDefaultConstraints());

		this.pack();
	}
	
	private GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void addGeneralPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		minLengthField = p.addTextParameter("Min length", appProperties.getOrDefault(minLengthProperty));
		maxLengthField = p.addTextParameter("Max length", appProperties.getOrDefault(maxLengthProperty));
		target.add(p);
	}
	
	private void confirm() {
		Integer maxLength = Integer.valueOf(maxLengthField.getText());
		Integer minLength = Integer.valueOf(minLengthField.getText());
		
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		TwoNodeSelection selection = getSelection(network,rootNetwork);
						
		CyNode source = selection.getFirst();
		CyNode target = selection.getSecond();
		PathSearcher searcher = new FixedLengthPathSearcher(network,minLength,maxLength);
		SearchPathsTaskConfig config = SearchPathsTaskConfig.create(
				source,target,network,searcher);
		ShowPathsAnalysisTaskFactory fac = 
				new ShowPathsAnalysisTaskFactory(appGlobals);
				
		appProperties.set(minLengthProperty, minLength.toString());
		appProperties.set(maxLengthProperty, maxLength.toString());
		
		appGlobals.taskManager.execute(fac.createTaskIterator(config));
		this.dispose();
	}
	
	private TwoNodeSelection getSelection(CyNetworkAdapter network, CyRootNetworkAdapter rootNetwork){
		try{
			return TwoNodeSelection.create(network,rootNetwork);
		 } catch(Exception e){
			 e.printStackTrace();
			 throw new RuntimeException(e.getMessage());
		 }
	}
	
	
	
	
}
