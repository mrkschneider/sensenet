package com.tcb.sensenet.internal.labeling;

import java.io.Serializable;

public class LabelSettings implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public boolean useShortNames = false;
	public boolean removeDashes = false;
	public boolean removeChainIds = false;
	public boolean useNames = true;
}
