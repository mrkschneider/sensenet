package com.tcb.sensenet.internal.util.iterator;

import java.util.PrimitiveIterator;

public class DefaultDoubleIterator implements DoubleIterator {

	private OfDouble it;

	public DefaultDoubleIterator(PrimitiveIterator.OfDouble it) {
		this.it = it;
	}
	
	@Override
	public double nextDouble() {
		return it.nextDouble();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

}
