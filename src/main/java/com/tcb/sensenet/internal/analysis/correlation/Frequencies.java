package com.tcb.sensenet.internal.analysis.correlation;

import java.util.Set;

public interface Frequencies<T> extends Probabilities<T> {

	public double getFrequency(T x);
	public int getLength();
	public void add(T x);

}