package com.tcb.sensenet.internal.analysis.diffusion;

public enum RandomWalkMode {
	DEFAULT, TARGETED, TARGETED_SYMMETRIC;
	
	public String toString(){
		switch(this){
		case DEFAULT: return "default";
		case TARGETED: return "targeted";
		case TARGETED_SYMMETRIC: return "targeted-symmetric";
		default: throw new IllegalArgumentException();
		}
		
	}
}
