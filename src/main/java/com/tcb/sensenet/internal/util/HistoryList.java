package com.tcb.sensenet.internal.util;

import java.util.LinkedList;

public class HistoryList<T> {
	
	private LinkedList<T> hist;
	private int size;
	
	public HistoryList(int size) {
		this.hist = new LinkedList<>();
		this.size = size;
	}
	
	public void add(T x) {
		while(hist.size() >= size) poll();
		hist.addFirst(x);
	}
	
	public T poll() {
		return hist.pollLast();
	}
	
	public T get(int idx) {
		return hist.get(idx);
	}
	
	public int getCurrentSize() {
		return hist.size();
	}
	
	public T remove(int idx) {
		return hist.remove(idx);
	}

}
