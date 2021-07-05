package com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation;

public enum CorrelationFactorsAnalysisType {
	NEIGHBOUR;
	
	public String toString(){
		switch(this){
		case NEIGHBOUR: return "Neighbour";
		default: throw new UnsupportedOperationException();
		}
	}
}
