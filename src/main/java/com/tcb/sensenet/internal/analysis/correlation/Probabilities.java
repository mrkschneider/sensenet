package com.tcb.sensenet.internal.analysis.correlation;

import java.util.Set;

public interface Probabilities<T> {
	public double getProbability(T x);
	public Set<T> getEvents();
}
