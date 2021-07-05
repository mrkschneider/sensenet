package com.tcb.sensenet.internal.util.iterator;

import java.util.PrimitiveIterator;

public class DefaultIntIterator implements IntIterator {

	private OfInt it;

	public DefaultIntIterator(PrimitiveIterator.OfInt it) {
		this.it = it;
	}
	
	@Override
	public int nextInt() {
		return it.nextInt();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

}
