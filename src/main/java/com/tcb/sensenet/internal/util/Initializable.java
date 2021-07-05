package com.tcb.sensenet.internal.util;

import java.io.Serializable;

public class Initializable<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private T obj;

	private Initializable(T obj){
		this.obj = obj;
	}
	
	public static <T> Initializable<T> of(T obj){
		return new Initializable<T>(obj);
	}
	
	public static <T> Initializable<T> empty(){
		return new Initializable<T>(null);
	}
	
	public T get(){
		if(isPresent()) return obj;
		else throw new RuntimeException(
				String.format("Field was not initialized"));
	}
	
	public Boolean isPresent(){
		return obj!=null;
	}
	
	public void clear(){
		obj=null;
	}
}
