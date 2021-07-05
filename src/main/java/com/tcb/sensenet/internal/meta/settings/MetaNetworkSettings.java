package com.tcb.sensenet.internal.meta.settings;

import java.io.Serializable;

import com.tcb.sensenet.internal.labeling.LabelSettings;

public class MetaNetworkSettings implements Serializable {
	private static final long serialVersionUID = 2L;
	
	public LabelSettings labelSettings;
	public TimelineSettings timelineSettings;
	
	public MetaNetworkSettings(){
		labelSettings = new LabelSettings();
		timelineSettings = new TimelineSettings();
	}
}
