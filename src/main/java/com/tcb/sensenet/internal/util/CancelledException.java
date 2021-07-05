package com.tcb.sensenet.internal.util;

public class CancelledException extends RuntimeException {
	public CancelledException(String message){
		super(message);
	}
	
	public CancelledException(Exception e){
		super(e);
	}
}
