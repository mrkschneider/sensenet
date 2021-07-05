package com.tcb.sensenet.internal.aggregation.methods.timeline;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.data.Columns;

public enum FrameWeightMethod {
	SUM,OCCURRENCE;
		
	public Columns getTargetColumn(){
		switch(this){
		case SUM: return AppColumns.AVERAGE_INTERACTIONS;
		case OCCURRENCE: return AppColumns.OCCURRENCE;
		default: throw new UnsupportedOperationException("Unknown AggregationMethod");
		}
	}
	
	public String toString(){
		switch(this){
		case SUM: return "Sum";
		case OCCURRENCE: return "Occurrence";
		default: throw new UnsupportedOperationException("Unknown AggregationMethod");
		}
	}
	
	public static FrameWeightMethod valueOfCLI(String method){
			try{
				return FrameWeightMethod.valueOf(method);
			} catch(IllegalArgumentException e){
				throw EnumUtil.usage(method, FrameWeightMethod.class);
			}
	}

	
}
