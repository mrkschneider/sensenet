package com.tcb.sensenet.internal.map.edge;

public enum EdgeMappingMethod {
	NAME, LOCATION;
	
	public String toString(){
		switch(this){
		case NAME: return "Shared name";
		case LOCATION: return "Match location";
		default: throw new IllegalArgumentException();
		}
	}
}
