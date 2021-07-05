package com.tcb.sensenet.internal.log;

public enum LogType {
	GLOBAL,TASK;
	
	public String toString(){
		switch(this){
		case GLOBAL: return "Global";
		case TASK: return "Task";
		default: throw new IllegalArgumentException();
		}
	}
	
}
