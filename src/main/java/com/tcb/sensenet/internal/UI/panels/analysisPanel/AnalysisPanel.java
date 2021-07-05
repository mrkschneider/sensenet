package com.tcb.sensenet.internal.UI.panels.analysisPanel;


import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.ClusterAnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.diffusion.DiffusionAnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.matrix.MatrixAnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.NetworkInteractionsAnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.paths.PathsAnalysisPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.selected.SelectedInteractionsAnalysisPanel;
import com.tcb.sensenet.internal.UI.util.Accordeon;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;


public class AnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	private SelectedInteractionsAnalysisPanel selectedEdgesPanel;
	private NetworkInteractionsAnalysisPanel networkAnalysisPanel;
	private PathsAnalysisPanel shortestPathsPanel;
	private ClusterAnalysisPanel clusterPanel;
	private MatrixAnalysisPanel matrixPanel;
	private DiffusionAnalysisPanel diffusionPanel;
		
	
	public AnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
									
		addNetworkAnalysisPanel();
		addSelectedEdgesPanel();
		addMatrixAnalysisPanel();
		addShortestPathsAnalysisPanel();
		addClusterAnalysisPanel();
		//addDiffusionAnalysisPanel();
		
		addDummyPanel();
		
		appGlobals.stateManagers.analysisPanelStateManager.register(this);
	}
	
	private void addSelectedEdgesPanel(){
		selectedEdgesPanel = 
				new SelectedInteractionsAnalysisPanel(appGlobals);
		this.add(new Accordeon("Selected interactions",selectedEdgesPanel));
	}
				
	private void addNetworkAnalysisPanel(){
		networkAnalysisPanel = 
				new NetworkInteractionsAnalysisPanel(appGlobals);
		this.add(new Accordeon("Network interactions",networkAnalysisPanel));
	}
	
	private void addShortestPathsAnalysisPanel() {
		shortestPathsPanel = new PathsAnalysisPanel(appGlobals);
		this.add(new Accordeon("Paths",shortestPathsPanel));
	}
	
	private void addClusterAnalysisPanel(){
		clusterPanel = new ClusterAnalysisPanel(appGlobals);
		this.add(new Accordeon("Clustering",clusterPanel));
	}
	
	private void addMatrixAnalysisPanel(){
		matrixPanel = new MatrixAnalysisPanel(appGlobals);
		this.add(new Accordeon("Network matrix",matrixPanel));
	}
	
	private void addDiffusionAnalysisPanel(){
		diffusionPanel = new DiffusionAnalysisPanel(appGlobals);
		this.add(new Accordeon("Diffusion",diffusionPanel));
	}
	
	
	public SelectedInteractionsAnalysisPanel getSelectedInteractionsPanel(){
		return selectedEdgesPanel;
	}
	
	public NetworkInteractionsAnalysisPanel getNetworkInteractionsPanel(){
		return networkAnalysisPanel;
	}
	
	public PathsAnalysisPanel getShortestPathsPanel(){
		return shortestPathsPanel;
	}
	
	public ClusterAnalysisPanel getClusterPanel(){
		return clusterPanel;
	}
	
	public MatrixAnalysisPanel getMatrixAnalysisPanel(){
		return matrixPanel;
	}
	
	public DiffusionAnalysisPanel getDiffusionPanel(){
		return diffusionPanel;
	}
		
}
