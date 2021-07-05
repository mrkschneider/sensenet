package com.tcb.sensenet.internal.util;

import java.util.Optional;
import java.util.function.Function;

public class NumberParser {
	
	public static Optional<Integer> parseInt(String s){
		return parse(s,Integer::valueOf);
	}
	
	public static Optional<Double> parseDouble(String s){
		return parse(s,Double::valueOf);
	}
	
	public static Optional<Long> parseLong(String s){
		return parse(s,Long::valueOf);
	}
	
	public static Optional<Float> parseFloat(String s){
		return parse(s,Float::valueOf);
	}
	
	private static <T extends Number> Optional<T> parse(String s, Function<String,T> f){
		try{
			return Optional.of(f.apply(s));
		} catch(NumberFormatException e){
			return Optional.empty();
		}
	}
	
}
