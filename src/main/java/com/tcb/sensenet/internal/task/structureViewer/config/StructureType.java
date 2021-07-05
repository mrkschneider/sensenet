package com.tcb.sensenet.internal.task.structureViewer.config;

public enum StructureType {
	SINGLE_STRUCTURE, TRAJECTORY, PRELOADED;
	
	public String toString(){
		switch(this){
		case SINGLE_STRUCTURE: return "Single structure";
		case TRAJECTORY: return "Trajectory";
		case PRELOADED: return "Preloaded"; 
		default: throw new IllegalArgumentException();
		}
	}
}
