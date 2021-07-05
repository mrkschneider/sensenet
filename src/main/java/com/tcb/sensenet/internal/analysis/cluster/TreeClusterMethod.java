package com.tcb.sensenet.internal.analysis.cluster;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single.AbstractClustererSettingsPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single.AgglomerativeClustererPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree.AbstractTreeClustererSettingsPanel;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree.TreeAgglomerativeClustererPanel;
import com.tcb.sensenet.internal.properties.AppProperties;

public enum TreeClusterMethod {
	AGGLOMERATIVE;

	public AbstractTreeClustererSettingsPanel createPanel(AppProperties appProperties){
		switch(this){
		case AGGLOMERATIVE: return new TreeAgglomerativeClustererPanel(appProperties);
		default: throw new UnsupportedOperationException();
		}
	}
	
	public String toString(){
		switch(this){
		case AGGLOMERATIVE: return "Agglomerative";
		default:  throw new UnsupportedOperationException();
		}
	}
}
